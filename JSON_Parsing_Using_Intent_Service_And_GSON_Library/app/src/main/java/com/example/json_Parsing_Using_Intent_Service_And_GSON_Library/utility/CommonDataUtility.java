package com.example.json_Parsing_Using_Intent_Service_And_GSON_Library.utility;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.json_Parsing_Using_Intent_Service_And_GSON_Library.R;
import com.example.json_Parsing_Using_Intent_Service_And_GSON_Library.gson_modal.Parameters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by peacock on 11/7/16.
 */
public class CommonDataUtility {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;

    public static String response = "";

    public static Parameters parameters = null;

    //public static JSONArray jsonArray = null;

    public static Activity activity;

    public static ArrayList<HashMap<String, String>> dataList = new ArrayList<>();

    //InvalidUser
    public static boolean isInvalidUser = false;

    //End
    public static int x;
    public static int y;

    public static void getDisplay(Context con) {
        Display display = ((Activity) con).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        x = size.x;
        y = size.y;
    }

    public static void notConnectedError(Activity con) {

        showAlert("No Internet connection found", con.getString(R.string.app_name),
                "Ok", con, "SNJ", false);

    }

    public static void netConnectedErrorfinish(Activity con) {

        showAlert("No Internet connection found", con.getString(R.string.app_name), "Ok", con,
                "SNJ", true);

    }

    public static boolean isValidEmail(String target) {

        Matcher matcher = Pattern.compile(EMAIL_PATTERN).matcher(target);

        return matcher.matches();

    }

    public static void showAlert(final String errorString, String title, final String button,
                                 final Activity con, String isTitle, final Boolean close) {

        View inf = LayoutInflater.from(con).inflate(R.layout.dialog, null);

        Button tv_dialog_ok = (Button) inf.findViewById(R.id.tv_dialog_ok);
        tv_dialog_ok.setText(button);

        TextView tv_error_string = (TextView) inf.findViewById(R.id.tv_error_string);
        tv_error_string.setText(errorString);
        TextView tv_thank_you = (TextView) inf.findViewById(R.id.tv_thank_you);
        if (isTitle.equals("NO")) {
            tv_thank_you.setVisibility(View.GONE);
        } else if (isTitle.equals("SNJ")) {
            tv_thank_you.setVisibility(View.VISIBLE);
            tv_thank_you.setText(title);
        }

        final Dialog dialog = new Dialog(con);
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(inf);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.roundcorner);
        dialog.show();

        tv_dialog_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (close) {
                    con.finish();
                    dialog.dismiss();

                } else {
                    dialog.dismiss();
                }

            }
        });
    }

    /*public static void inValidUserDialog(final Activity activity) {

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.roundcorner);
        dialog.setContentView(R.layout.dialog);

        TextView tv_thank_you = (TextView) dialog.
                findViewById(R.id.tv_thank_you);
        tv_thank_you.setText(activity.getString(R.string.app_name));
        tv_thank_you.setVisibility(View.VISIBLE);

        TextView tv_error_string = (TextView) dialog.
                findViewById(R.id.tv_error_string);
        tv_error_string.setText(activity.getString(R.string.invalidUser));

        Button tv_dialog_ok = (Button) dialog.
                findViewById(R.id.tv_dialog_ok);
        tv_dialog_ok.setText(activity.getString(R.string.ok));

        dialog.show();

        tv_dialog_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                isInvalidUser = true;

                ApplicationLoader.getInstance().getPrefManager().
                        setLoginName("");

                ApplicationLoader.getInstance().getPrefManager().
                        setPassword("");

                dialog.dismiss();

                Intent i = new Intent(activity, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(i);

                activity.finish();

                return;

            }
        });
    }*/

    public static void parseResult(JSONArray array, ArrayList<HashMap<String, String>> result,
                                   ArrayList<String> paralist) throws JSONException {

        result.clear();

        for (int i = 0; i < array.length(); i++) {

            JSONObject temp = array.getJSONObject(i);

            HashMap<String, String> map = new HashMap<String, String>();

            for (int j = 0; j < paralist.size(); j++) {

                if (temp.has(paralist.get(j)))
                    map.put(paralist.get(j), temp.getString(paralist.get(j)));

            }

            result.add(map);

            System.out.println("parseResult --> " + result.toString());

        }
    }

    //Check for Internet connection
    public static int getConnectivityStatus(Activity activity) {

        ConnectivityManager cm = (ConnectivityManager) activity.
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork && activeNetwork.isConnectedOrConnecting()) {

            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;

        }

        return TYPE_NOT_CONNECTED;

    }

    public static void hideKeyboard(Activity activity) {

        if (activity.getCurrentFocus() != null) {

            InputMethodManager inputMethodManager = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);

            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().
                    getWindowToken(), 0);

        }
    }

    public static boolean getConnectivityStatusString(Activity activity) {
        int connection = getConnectivityStatus(activity);
        boolean status = false;

        if (connection == TYPE_WIFI || connection == TYPE_MOBILE
                || connection != TYPE_NOT_CONNECTED) {
            status = true;
        } else {
            status = false;
        }

        return status;
    }

    public static String toTwoPrecision(String str) {
        Double value;
        String result;
        value = Double.parseDouble(str);
        result = String.format(Locale.getDefault(), "%.2f", value);

        return result;
    }

    /*public static void addToArrayList(JSONObject Result, String Name, ArrayList<FillMaster> list)
            throws JSONException {

        JSONObject temp;
        FillMaster fillMaster;
        JSONArray sizemast = Result.getJSONArray(Name);

        for (int i = 0; i < sizemast.length(); i++) {

            fillMaster = new FillMaster();

            temp = sizemast.getJSONObject(i);
            if (temp.has(StaticDataUtility.IDNNO)) {

                fillMaster.setIDNNO(temp.getString(StaticDataUtility.IDNNO));
//                temp1.put(StaticDataUtility.IDNNO, temp.getString(StaticDataUtility.IDNNO));
            }

            if (temp.has(StaticDataUtility.CODE)) {

                fillMaster.setCODE(temp.getString(StaticDataUtility.CODE));
//                temp1.put(StaticDataUtility.CODE, temp.getString(StaticDataUtility.CODE));
            }

            if (temp.has(StaticDataUtility.NAME)) {

                fillMaster.setNAME(temp.getString(StaticDataUtility.NAME));
//                temp1.put(StaticDataUtility.NAME, temp.getString(StaticDataUtility.NAME));
            }

//            if (temp.has(StaticDataUtility.SORTNAME)) {
//                temp1.put(StaticDataUtility.SORTNAME, temp.getString(StaticDataUtility.SORTNAME));
//            }
//            if (temp.has(StaticDataUtility.ORD)) {
//                temp1.put(StaticDataUtility.ORD, temp.getString(StaticDataUtility.ORD));
//            }
//            if (temp.has(StaticDataUtility.ACTION_ID)) {
//                temp1.put(StaticDataUtility.ACTION_ID, temp.getString(StaticDataUtility.ACTION_ID));
//            }
//            if (temp.has(StaticDataUtility.ACTION_NAME)) {
//                temp1.put(StaticDataUtility.ACTION_NAME, temp.getString(StaticDataUtility.ACTION_NAME));
//            }
//            if (temp.has(StaticDataUtility.ACTIVITY_ID)) {
//                temp1.put(StaticDataUtility.ACTIVITY_ID, temp.getString(StaticDataUtility.ACTIVITY_ID));
//            }
//            if (temp.has(StaticDataUtility.ACTIVITY_NAME)) {
//                temp1.put(StaticDataUtility.ACTIVITY_NAME, temp.getString(StaticDataUtility.ACTIVITY_NAME));
//            }
//            if (temp.has("SIZE")) {
//                temp1.put("SIZE", temp.getString("SIZE"));
//            }
//            if (temp.has("ID")) {
//                temp1.put("ID", temp.getString("ID"));
//            }
//
//            if (temp.has("AVAIL")) {
//                temp1.put("AVAIL", temp.getString("AVAIL"));
//            }
//            if (temp.has("Title")) {
//                temp1.put("Title", temp.getString("Title"));
//            }
            list.add(fillMaster);

        }
    }

    public static void showAlertForPDF(String errorString, String ttitle,
                                       final Activity con, final String name) {

        View inf = LayoutInflater.from(con).inflate(R.layout.dialog_logout, null);


        TextView tv_message = (TextView) inf.findViewById(R.id.tv_message);
        tv_message.setText(errorString);

        TextView tv_dialog_ok = (TextView) inf.findViewById(R.id.tv_ok);
        tv_dialog_ok.setText(Html.fromHtml("<B><font color='#4791FF'>"
                + con.getApplicationContext().getString(R.string.ok)
                + "</font><B>"));

        TextView tv_cancel = (TextView) inf.findViewById(R.id.tv_cancel);
        tv_cancel.setText(Html.fromHtml("<B><font color='#4791FF'>"
                + con.getApplicationContext().getString(
                R.string.Open_Pdf) + "</font><B>"));

        final Dialog dialog = new Dialog(con);
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(inf);
        WindowManager wm = (WindowManager) con.getSystemService(Context.WINDOW_SERVICE);
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        dialog.getWindow().setLayout(width - 100,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.getWindow().setBackgroundDrawableResource(R.drawable.roundcorner);
        dialog.show();

        tv_dialog_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dialog.dismiss();
                return;
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dialog.dismiss();

                File file = new File(name);
                Intent target = new Intent(Intent.ACTION_VIEW);
                target.setDataAndType(Uri.fromFile(file),
                        "application/pdf");
                target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                Intent intent = Intent.createChooser(
                        target,
                        con.getApplicationContext().getString(
                                R.string.Open_File));
                try {
                    con.startActivity(intent);
                } catch (ActivityNotFoundException e) {


                    CommonDataUtility.showAlert(con.getString(R.string.No_PDF_Reader_Found),
                            con.getString(R.string.app_name), con.getString(R.string.ok),
                            con, "SNJ", false);


                }
                return;


            }
        });


    }*/
}
