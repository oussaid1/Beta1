package com.dev_bourheem.hadi.DatabaseClass;

import android.provider.BaseColumns;

public final class DbContractor  {

    private DbContractor (){}

    public class TableColumns implements BaseColumns {


        public static final String TABLE_NAME = "Stuff";
        //public static final String ID = "ID";
        public static final String col1 = "Item_Name";
        public static final String col2 = "Item_Price";
        public static final String person = "MoolHanout";
        public static final String da = "history";
        public static final String Quantifier = "Quantifiers";
        public static final String Quantity = "quantity";

        /*************************************************************/

        public static final String TABLE_NAMEArch = "StuffArch";
      //  public static final String IDArch = "ID";
        public static final String col1Arch = "Item_Name";
        public static final String col2Arch = "Item_Price";
        public static final String personArch = "MoolHanout";
        public static final String daArch = "history";
        public static final String QuantifierArch = "Quantifiers";
        public static final String QuantityArch = "quantity";
    }
}
