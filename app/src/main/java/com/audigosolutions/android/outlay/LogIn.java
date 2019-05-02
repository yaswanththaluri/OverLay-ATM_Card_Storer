package com.audigosolutions.android.outlay;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Handler;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import io.realm.Realm;
import io.realm.RealmResults;

public class LogIn extends AppCompatActivity {

    private KeyStore keyStore;
    // Variable used for storing the key in the Android Keystore container
    private static final String KEY_NAME = "androidHive";
    private Cipher cipher;
    private EditText pinInp1;
    private EditText pinInp2;
    private EditText pinInp3;
    private EditText pinInp4;
    private Realm realm;
    private String name = "";
    private String pin = "";
    private TextView nameOfUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);



        pinInp1 = findViewById(R.id.inp1);
        pinInp2 = findViewById(R.id.inp2);
        pinInp3 = findViewById(R.id.inp3);
        pinInp4 = findViewById(R.id.inp4);

        nameOfUser = findViewById(R.id.nameUser);


        realm = Realm.getDefaultInstance();


        RealmResults<User> list = realm.where(User.class).findAll();

        for (User i : list)
        {
            name = i.getName();
            pin = i.getPin();
        }

        nameOfUser.setText(name);


        pinInp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(pinInp1.getText().toString().trim().length() >=1){
                    pinInp1.clearFocus();
                    pinInp2.requestFocus();
                }
            }
        });

        pinInp2.addTextChangedListener(new TextWatcher() {
            private int previousLength;
            private boolean backSpace;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                previousLength = charSequence.length();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                backSpace = previousLength > editable.length();
                if(pinInp2.getText().toString().trim().length() >=1){
                    pinInp2.clearFocus();
                    pinInp3.requestFocus();
                }
                if (backSpace)
                {
                    pinInp2.clearFocus();
                    pinInp1.requestFocus();
                }
            }
        });

        pinInp3.addTextChangedListener(new TextWatcher() {
            private int previousLength;
            private boolean backSpace;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                previousLength = charSequence.length();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                backSpace = previousLength > editable.length();
                if(pinInp3.getText().toString().trim().length() >=1){
                    pinInp3.clearFocus();
                    pinInp4.requestFocus();
                }
                if (backSpace)
                {
                    pinInp3.clearFocus();
                    pinInp2.requestFocus();
                }
            }
        });

        pinInp4.addTextChangedListener(new TextWatcher() {
            private int previousLength;
            private boolean backSpace;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                previousLength = charSequence.length();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                backSpace = previousLength > editable.length();
                if(pinInp4.getText().toString().trim().length() >=1){
                    pinInp4.clearFocus();
                    Toast.makeText(LogIn.this, "PIN Entered", Toast.LENGTH_SHORT).show();

                    String entered = pinInp1.getText().toString()
                            + pinInp2.getText().toString()
                            + pinInp3.getText().toString()
                            +pinInp4.getText().toString();

                    if (pin.equals(entered))
                    {
                        Intent i = new Intent(LogIn.this, LandingPage.class);
                        startActivity(i);
                    }
                    else
                    {
                        Toast.makeText(LogIn.this, "PIN Entered is Wrong", Toast.LENGTH_LONG).show();
                    }
                }
                if (backSpace)
                {
                    pinInp4.clearFocus();
                    pinInp3.requestFocus();
                }
            }
        });




        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);


        if (!fingerprintManager.isHardwareDetected())
        {
            Toast.makeText(LogIn.this, "Finger Print Hardware not detected....Continue with PIN", Toast.LENGTH_SHORT).show();

        }
        else {
            // Checks whether fingerprint permission is set on manifest
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(LogIn.this,"Fingerprint authentication permission not enabled", Toast.LENGTH_SHORT).show();
            }
            else{
                // Check whether at least one fingerprint is registered
                if (!fingerprintManager.hasEnrolledFingerprints()) {
                    Toast.makeText(LogIn.this,"Register at least one finger print", Toast.LENGTH_SHORT).show();
                }
                else{
                    // Checks whether lock screen security is enabled or not
                    if (!keyguardManager.isKeyguardSecure()) {
                        Toast.makeText(LogIn.this,"Lock screen security not enabled", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        generateKey();


                        if (cipherInit()) {
                            FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
                            FingerPrintHandler helper = new FingerPrintHandler(this);
                            helper.startAuth(fingerprintManager, cryptoObject);
                        }
                    }
                }
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    protected void generateKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (Exception e) {
            e.printStackTrace();
        }


        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException("Failed to get KeyGenerator instance", e);
        }


        try {
            keyStore.load(null);
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException |
                InvalidAlgorithmParameterException
                | CertificateException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    public boolean cipherInit() {
        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }


        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {


        if (doubleBackToExitPressedOnce) {
            finishAffinity();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);

    }
}
