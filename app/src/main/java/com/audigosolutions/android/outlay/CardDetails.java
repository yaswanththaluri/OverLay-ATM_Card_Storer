package com.audigosolutions.android.outlay;

import io.realm.RealmObject;

public class CardDetails extends RealmObject {

    private String cardNumber;
    private String cardNickName;
    private String nameOnCard;
    private String expiryDate;
    private String cvv;


    public CardDetails()
    {}

    public CardDetails(String cardNumber, String cardNickName, String nameOnCard, String expiryDate, String cvv)
    {
        this.cardNickName = cardNickName;
        this.cardNumber = cardNumber;
        this.nameOnCard = nameOnCard;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }


    public String getCardNickName() {
        return cardNickName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setCardNickName(String cardNickName) {
        this.cardNickName = cardNickName;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }
}
