package com.audigosolutions.android.outlay;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;
import android.widget.ImageView;

public class FingerPrintHandler extends FingerprintManager.AuthenticationCallback {

    private Context context;

    public FingerPrintHandler(Context mContext)
    {
        context = mContext;
    }

    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {
        CancellationSignal cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {

    }

    @Override
    public void onAuthenticationFailed() {
        this.updateUI("Authentication Failed", 0);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        this.updateUI("Authentication Succeeded", 1);
    }

    private void updateUI(String message, int result)
    {
        ImageView image = ((Activity)context).findViewById(R.id.fingerImage);
        Intent i = new Intent(context.getApplicationContext(), LandingPage.class);

        if(result==0)
        {
            image.setImageResource(R.drawable.fingererror);
        }
        else if (result==1)
        {
            image.setImageResource(R.drawable.fingersuceed);
            context.startActivity(i);
        }
    }
}
