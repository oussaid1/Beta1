package com.dev_bourheem.hadi.Payment;

import android.os.Parcel;
import android.os.Parcelable;

public class PaymentItemsClass implements Parcelable {
    private String id;
    private String ShopName;
    private String paidAmount;
    private String datePaid;


    public PaymentItemsClass(String idd,String shopName,String paidAmounts,String datePaid1) {
        id=idd;
        ShopName = shopName;
        paidAmount = paidAmounts;
        datePaid = datePaid1;

    }


    protected PaymentItemsClass(Parcel in) {
        id= in.readString();
        ShopName = in.readString();
        paidAmount = in.readString();
        datePaid = in.readString();
    }

    public static final Parcelable.Creator<PaymentItemsClass> CREATOR = new Parcelable.Creator<PaymentItemsClass>() {
        @Override
        public PaymentItemsClass createFromParcel(Parcel in) {
            return new PaymentItemsClass(in);
        }

        @Override
        public PaymentItemsClass[] newArray(int size) {
            return new PaymentItemsClass[size];
        }
    };
    public String getIdno() {
        return id;
    }

    public String getShopName() {
        return ShopName;
    }

    public String getDateBought() {
        return paidAmount;
    }

    public String getItemPrice() {
        return datePaid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(id);
        dest.writeString(ShopName);
        dest.writeString(paidAmount);
        dest.writeString(datePaid);
    }


}
