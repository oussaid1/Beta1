package com.dev_bourheem.hadi.Archieve;

import android.os.Parcel;
import android.os.Parcelable;

import com.dev_bourheem.hadi.exampleitem;

public class ArchexampleItem implements Parcelable {
    private String idno;
    private String quantity;
    private String quantifier;
    private String ItemName;
    private String ItemPrice;
    private String ShopName;
    private String dateBought;


    public ArchexampleItem(String idd, String Qntt, String Quantif, String ITnm, String ITprix, String ShopName1, String dateBought1) {
        idno = idd;
        quantity = Qntt;
        quantifier = Quantif;
        ItemName = ITnm;
        ItemPrice = ITprix;
        ShopName = ShopName1;
        dateBought = dateBought1;

    }


    protected ArchexampleItem(Parcel in) {
        idno = in.readString();
        quantity = in.readString();
        quantifier = in.readString();
        ItemName = in.readString();
        ItemPrice = in.readString();
        ShopName = in.readString();
        dateBought = in.readString();
    }

    public static final Parcelable.Creator<ArchexampleItem> CREATOR = new Parcelable.Creator<ArchexampleItem>() {
        @Override
        public ArchexampleItem createFromParcel(Parcel in) {
            return new ArchexampleItem( in );
        }

        @Override
        public ArchexampleItem[] newArray(int size) {
            return new ArchexampleItem[size];
        }
    };

    public String getShopName() {
        return ShopName;
    }

    public String getDateBought() {
        return dateBought;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getQuantifier() {
        return quantifier;
    }

    public String getItemName() {
        return ItemName;
    }

    public String getItemPrice() {
        return ItemPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString( idno );
        dest.writeString( quantity );
        dest.writeString( quantifier );
        dest.writeString( ItemName );
        dest.writeString( ItemPrice );
        dest.writeString( ShopName );
        dest.writeString( dateBought );
    }

    public String getIdno() {
        return idno;
    }
}
