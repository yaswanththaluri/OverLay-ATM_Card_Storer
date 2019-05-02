package com.audigosolutions.android.outlay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class CardAdapter extends ArrayAdapter<CardDetails> {



    public CardAdapter(Context context, int resource, List<CardDetails> objects) {
        super(context, resource, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.list_item, parent, false);
        }

        TextView name = (convertView).findViewById(R.id.displayCardName);
        TextView number = convertView.findViewById(R.id.displayCrdNumber);

        final CardDetails details = getItem(position);

        final String card = details.getCardNumber();
        String lastFour = card.substring(card.length()-4);
        name.setText(details.getCardNickName());
        number.setText(lastFour);


        TextView t = convertView.findViewById(R.id.tap);
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), CardCredentials.class);
                Bundle extras = new Bundle();
                extras.putString("cardNumber", details.getCardNumber());
                extras.putString("nameOnCard", details.getNameOnCard());
                extras.putString("expiry", details.getExpiryDate());
                extras.putString("cvv", details.getCvv());
                i.putExtras(extras);
                view.getContext().startActivity(i);
            }
        });

        return convertView;
    }
}
