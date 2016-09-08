package com.example.json_Parsing_Using_Intent_Service_And_GSON_Library.utility;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class Font {
    public static String circula = "circula-medium_0.otf";
    public static String ShapeIcons = "fonts/Diamond-Shapes.ttf";
    public static String SRK = "SRK.ttf";
    public static String SRKshape = "SRKshape.ttf";
    public static String SRKTwinShape = "SRKTwinShape.ttf";
    public static String SRKBtb = "SRKIcons-B2B.ttf";

    public static String SNJ_app = "fonts/SNJ-App.ttf";


    public static void setIconFonts(Context con, TextView tv) {
        Typeface font = Typeface.createFromAsset(con.getAssets(), "fonts/WalaIphone.ttf");
        tv.setTypeface(font);
    }

    public static void setAppIconFonts(TextView tv, Context con) {
        Typeface font = Typeface.createFromAsset(con.getAssets(), SNJ_app);
        tv.setTypeface(font);
    }

    public static void setShapeFonts(TextView tv, Context con) {
        Typeface font = Typeface.createFromAsset(con.getAssets(), ShapeIcons);
        tv.setTypeface(font);
    }

    public static void setTitleFont(TextView tv, Context con) {
        Typeface font = Typeface.createFromAsset(con.getAssets(), "fonts/" + circula);
        tv.setTypeface(font);
    }

    public static void setBtb(TextView tv, Context con) {
        Typeface font = Typeface.createFromAsset(con.getAssets(), "fonts/" + SRKBtb);
        tv.setTypeface(font);
    }

    public static void setSRK(TextView tv, Context con) {
        Typeface font = Typeface.createFromAsset(con.getAssets(), "fonts/" + SRK);
        tv.setTypeface(font);
    }

    public static void setSRKShape(TextView tv, Context con) {
        Typeface font = Typeface.createFromAsset(con.getAssets(), "fonts/" + SRKshape);
        tv.setTypeface(font);
    }

    public static void setTwinSRKShape(TextView tv, Context con) {
        Typeface font = Typeface.createFromAsset(con.getAssets(), "fonts/" + SRKTwinShape);
        tv.setTypeface(font);
    }
}
