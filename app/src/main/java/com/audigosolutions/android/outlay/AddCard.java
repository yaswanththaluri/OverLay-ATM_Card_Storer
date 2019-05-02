package com.audigosolutions.android.outlay;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmAsyncTask;

public class AddCard extends Fragment {


    public View view;
    private EditText cardNumber;
    private EditText cnfCardNumber;
    private EditText cardName;
    private EditText cardUserName;
    private EditText expiry;
    private EditText cvv;
    private FloatingActionButton fab;
    private Button save;
    private Button reset;
    private boolean isOpened = false;
    private LinearLayout lay;
    private TextView hint;

    private Realm realm;
    private RealmAsyncTask realmAsyncTask;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.add_card, container, false);

        cardUserName = (view).findViewById(R.id.cardusername);
        cardNumber = (view).findViewById(R.id.cardNumber);
        cnfCardNumber = (view).findViewById(R.id.cnfcardNumber);
        expiry = (view).findViewById(R.id.expDate);
        cvv = (view).findViewById(R.id.cardcvv);
        cardName = (view).findViewById(R.id.cardName);
        hint = view.findViewById(R.id.textHint);

        lay = (view).findViewById(R.id.addCardLay);

        fab = (view).findViewById(R.id.addCardFab);

        save = (view).findViewById(R.id.save);
        reset = (view).findViewById(R.id.reset);

        realm = Realm.getDefaultInstance();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isOpened)
                {
                    fab.setImageResource(R.drawable.close);
                    lay.setVisibility(View.VISIBLE);
                    hint.setVisibility(View.INVISIBLE);
                    isOpened = true;
                }
                else
                {
                    lay.setVisibility(View.INVISIBLE);
                    fab.setImageResource(R.drawable.add);
                    hint.setVisibility(View.VISIBLE);
                    resetData();
                    isOpened = false;
                }
            }
        });


        expiry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable editable) {

                

            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = cardNumber.getText().toString();
                String cnfNum = cnfCardNumber.getText().toString();
                String name = cardName.getText().toString();
                String user = cardUserName.getText().toString();
                String exp = expiry.getText().toString();
                String cvvNo = cvv.getText().toString();

                if (!num.equals("") && !cnfNum.equals("") && !name.equals("") && !user.equals("") && !exp.equals("") && !cvvNo.equals(""))
                {
                    if (num.equals(cnfNum))
                    {
                        saveDetails(num, name, user, exp, cvvNo);
                    }
                    else
                    {
                        Toast.makeText(view.getContext(), "Mismatch in Card Number and Confirmation Card Number", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(view.getContext(), "All fields must be filled", Toast.LENGTH_SHORT).show();
                }
            }
        });


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetData();
            }
        });

        return view;
    }



    public void resetData()
    {
        cardName.setText("");
        cardNumber.setText("");
        cnfCardNumber.setText("");
        cvv.setText("");
        cardUserName.setText("");
        expiry.setText("");
    }


    public void saveDetails(final String cardNo, final String CardName, final String userName, final String expiry, final String cvv)
    {
        realmAsyncTask = realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                CardDetails details = realm.createObject(CardDetails.class);
                details.setCardNumber(cardNo);
                details.setCardNickName(CardName);
                details.setNameOnCard(userName);
                details.setExpiryDate(expiry);
                details.setCvv(cvv);
            }

        }, new Realm.Transaction.OnSuccess()
        {
            @Override
            public void onSuccess() {
                Toast.makeText(view.getContext(), "Card Saved Successfully", Toast.LENGTH_SHORT).show();
                lay.setVisibility(View.INVISIBLE);
                fab.setImageResource(R.drawable.add);
                hint.setVisibility(View.VISIBLE);
                resetData();
                isOpened = false;
            }
        }, new Realm.Transaction.OnError()
        {
            @Override
            public void onError(Throwable error) {
                Toast.makeText(view.getContext(), "Error in Saving Card", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();

        if(realmAsyncTask!=null  && !realmAsyncTask.isCancelled())
        {
            realmAsyncTask.cancel();
        }
    }
}
