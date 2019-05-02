package com.audigosolutions.android.outlay;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class CardCredentials extends AppCompatActivity {


    private TextView number, userName, exp, cvv;
    private RelativeLayout card;
    private ImageView share;
    private ImageView delete;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_credentials);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        delete = findViewById(R.id.cardDelete);

        realm = Realm.getDefaultInstance();



        final String cardNumber = extras.getString("cardNumber");
        String name = extras.getString("nameOnCard");
        String expiry = extras.getString("expiry");
        String cv = extras.getString("cvv");

        number = findViewById(R.id.credentialCardNumber);
        userName = findViewById(R.id.credentialNameOnCard);
        exp = findViewById(R.id.credentialExpiry);
        cvv = findViewById(R.id.credentialCvv);


        String spacedCardNumber = "";
        int j =0;
        for(int i=0; i<cardNumber.length(); i++)
        {
            if(i!=0 && j == 3)
            {
                spacedCardNumber = spacedCardNumber + cardNumber.charAt(i);
                spacedCardNumber = spacedCardNumber + "\t\t\t";
                j = 0;
            }
            else
            {
                spacedCardNumber = spacedCardNumber + cardNumber.charAt(i);
                j++;
            }
        }

        String spacedExp = "";
        for(int i=0; i<expiry.length(); i++)
        {
            if(i==1)
            {
                spacedExp = spacedExp +  expiry.charAt(i);
                spacedExp = spacedExp + "  /  ";
            }
            else
            {
                spacedExp = spacedExp +  expiry.charAt(i);
            }
        }


        number.setText(spacedCardNumber);
        userName.setText(name);
        exp.setText(spacedExp);
        cvv.setText(cv);

        card = findViewById(R.id.credentialCard);
        card.setDrawingCacheEnabled(true);

//        share = findViewById(R.id.share);


//        share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                createAndShare(card);
//            }
//        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(CardCredentials.this);
                builder.setMessage("Are You sure You want to Delete It?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                deleteCard(cardNumber);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                builder.show();

            }
        });




    }


    public void createAndShare(RelativeLayout card)
    {
        Bitmap bitmap = Bitmap.createBitmap(card.getDrawingCache());

        Uri uri = null;
        try {
            File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "to-share.png");
            FileOutputStream stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
            stream.close();
            uri = Uri.fromFile(file);
        } catch (IOException e) {
            Log.d("tag", "IOException while trying to write file for sharing: " + e.getMessage());
        }

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("image/png");
        startActivity(Intent.createChooser(shareIntent, "share via"));
    }



    public void deleteCard(String cardNumber)
    {
        final RealmResults<CardDetails> details = realm.where(CardDetails.class).findAll();
        int i = 0;
        for (CardDetails j: details)
        {
            if(j.getCardNumber().equals(cardNumber))
            {
                break;
            }
            else
            {
                i++;
            }
        }

        final int index = i;
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                details.deleteFromRealm(index);
                Toast.makeText(CardCredentials.this, "Card Deleted Successfully", Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }






}
