package com.dev_bourheem.hadi.ArchivePayment;

import android.os.Parcel;
import android.os.Parcelable;

public class ArPaymentExampleItem implements Parcelable {
    private String id;
    private String ShopName;
    private String paidAmount;
    private String datePaid;


    public ArPaymentExampleItem(String idd,String shopName,String paidAmounts,String datePaid1) {
        id=idd;
        ShopName = shopName;
        paidAmount = paidAmounts;
        datePaid = datePaid1;

    }


    protected ArPaymentExampleItem(Parcel in) {
        id= in.readString();
        ShopName = in.readString();
        paidAmount = in.readString();
        datePaid = in.readString();
    }

    public static final Creator<ArPaymentExampleItem> CREATOR = new Creator<ArPaymentExampleItem>() {
        @Override
        public ArPaymentExampleItem createFromParcel(Parcel in) {
            return new ArPaymentExampleItem(in);
        }

        @Override
        public ArPaymentExampleItem[] newArray(int size) {
            return new ArPaymentExampleItem[size];
        }
    };

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

    public String getARIdno() {
        return id;
    }
}
