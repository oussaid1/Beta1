package com.dev_bourheem.hadi;

public class exampleitem {
    private int mImageResource;
    private String mText1;
    private String mText2;
    private String ShopName;
    private String dateBought;

    public exampleitem(int imageResource, String text1, String text2,String ShopName1,String dateBought1) {
        mImageResource = imageResource;
        mText1 = text1;
        mText2 = text2;
        ShopName = ShopName1;
        dateBought = dateBought1;

    }

    public int getImageResource() {
        return mImageResource;
    }

    public String getText1() {
        return mText1;
    }

    public String getText2() {
        return mText2;
    }

    public String getShopName() {
        return ShopName;
    }

    public String getDateBought() {
        return dateBought;
    }
}
