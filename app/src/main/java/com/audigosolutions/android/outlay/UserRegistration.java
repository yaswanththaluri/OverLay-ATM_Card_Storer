package com.audigosolutions.android.outlay;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import io.realm.Realm;
import io.realm.RealmAsyncTask;

public class UserRegistration extends AppCompatActivity {


    private TextView head;
    private Realm realm;
    private EditText name;
    private EditText pinInp1, pinInp2, pinInp3, pinInp4;
    private EditText cnfPin1, cnfPin2, cnfPin3, cnfPin4;
    private Button register;
    private RealmAsyncTask realmAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        realm = Realm.getDefaultInstance();

        head = findViewById(R.id.headTitle);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/pacific-font.ttf");
        head.setTypeface(typeface);

        name = findViewById(R.id.userName);

        pinInp1 = findViewById(R.id.rinp1);
        pinInp2 = findViewById(R.id.rinp2);
        pinInp3 = findViewById(R.id.rinp3);
        pinInp4 = findViewById(R.id.rinp4);

        cnfPin1 = findViewById(R.id.cnfinp1);
        cnfPin2 = findViewById(R.id.cnfinp2);
        cnfPin3 = findViewById(R.id.cnfinp3);
        cnfPin4 = findViewById(R.id.cnfinp4);

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
                    Toast.makeText(UserRegistration.this, "PIN Entered", Toast.LENGTH_SHORT).show();
                }
                if (backSpace)
                {
                    pinInp4.clearFocus();
                    pinInp3.requestFocus();
                }
            }
        });



        cnfPin1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(cnfPin1.getText().toString().trim().length() >=1){
                    cnfPin1.clearFocus();
                    cnfPin2.requestFocus();
                }
            }
        });

        cnfPin2.addTextChangedListener(new TextWatcher() {
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
                if(cnfPin2.getText().toString().trim().length() >=1){
                    cnfPin2.clearFocus();
                    cnfPin3.requestFocus();
                }
                if (backSpace)
                {
                    cnfPin2.clearFocus();
                    cnfPin1.requestFocus();
                }
            }
        });

        cnfPin3.addTextChangedListener(new TextWatcher() {
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
                if(cnfPin3.getText().toString().trim().length() >=1){
                    cnfPin3.clearFocus();
                    cnfPin4.requestFocus();
                }
                if (backSpace)
                {
                    cnfPin3.clearFocus();
                    cnfPin2.requestFocus();
                }
            }
        });

        cnfPin4.addTextChangedListener(new TextWatcher() {
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
                if(cnfPin4.getText().toString().trim().length() >=1){
                    cnfPin4.clearFocus();
                    Toast.makeText(UserRegistration.this, "PIN Entered", Toast.LENGTH_SHORT).show();
                }
                if (backSpace)
                {
                    cnfPin4.clearFocus();
                    cnfPin3.requestFocus();
                }
            }
        });




        register = findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = name.getText().toString();
                String pin = pinInp1.getText().toString()
                        + pinInp2.getText().toString()
                        +pinInp3.getText().toString()
                        +pinInp4.getText().toString();

                String cnfPin = cnfPin1.getText().toString()
                        +cnfPin2.getText().toString()
                        +cnfPin3.getText().toString()
                        +cnfPin4.getText().toString();


                if(pin.equals(cnfPin) && !username.equals(""))
                {
                    dataHandler(username, pin);
                }

            }
        });


    }


    public void dataHandler(final String name, final String pin)
    {
        realmAsyncTask = realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                User user = realm.createObject(User.class);
                user.setName(name);
                user.setPin(pin);
            }
        }, new Realm.Transaction.OnSuccess()
        {
            @Override
            public void onSuccess() {
                Toast.makeText(UserRegistration.this, "Registration Successfull", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(UserRegistration.this, LandingPage.class);
                startActivity(i);
            }
        },
        new Realm.Transaction.OnError()
        {
            @Override
            public void onError(Throwable error) {
                Toast.makeText(UserRegistration.this, "Registration Failed", Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    protected void onStop() {
        super.onStop();

        if(realmAsyncTask!=null  && !realmAsyncTask.isCancelled())
        {
            realmAsyncTask.cancel();
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
