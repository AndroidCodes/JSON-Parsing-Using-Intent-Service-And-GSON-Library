package com.example.json_Parsing_Using_Intent_Service_And_GSON_Library;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.json_Parsing_Using_Intent_Service_And_GSON_Library.adapter.RecyclerViewAdapter;
import com.example.json_Parsing_Using_Intent_Service_And_GSON_Library.service.Parse_JSON_FronResponse;
import com.example.json_Parsing_Using_Intent_Service_And_GSON_Library.utility.CommonDataUtility;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //https://www.youtube.com/watch?v=oZpv6W3Lflo  Youtube Video Link Which I Prefered

    private boolean isCompleted = false;

    private String url = "http://webservice.snjdiam.com/SNJV1.asmx";

    private String SEARCHDIAMOND = "SearchDiamond";

    private Activity activity;

    private ProgressDialog pDialog;

    private BroadcastReceiver updateUI = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getStringExtra("opCode").equals(SEARCHDIAMOND)) {

                //freeMemory();

                if (pDialog != null) {

                    pDialog.dismiss();

                }

                RecyclerView rv_listData = (RecyclerView) findViewById(R.id.rv_listData);

                RecyclerViewAdapter adapter = new RecyclerViewAdapter(activity,
                        CommonDataUtility.parameters.getResult());

                LinearLayoutManager ll_manager = new LinearLayoutManager(activity,
                        LinearLayoutManager.VERTICAL, false);
                rv_listData.setLayoutManager(ll_manager);
                rv_listData.setAdapter(adapter);

                Snackbar.make(rv_listData, "" + CommonDataUtility.parameters.getResult().size() +
                        " Stones Found", Snackbar.LENGTH_SHORT).show();

                isCompleted = true;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        activity = MainActivity.this;

        findViewById(R.id.btn_startParsing).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (CommonDataUtility.getConnectivityStatusString(activity)) {

                    isCompleted = false;

                    getData();

                } else {

                    CommonDataUtility.notConnectedError(activity);

                    isCompleted = true;

                }
            }
        });
    }

    private void getData() {

        pDialog = new ProgressDialog(activity);
        pDialog.setMessage(getString(R.string.pDialogMsg));
        pDialog.setTitle(getString(R.string.app_name));
        pDialog.setCancelable(false);
        pDialog.show();

        String str = url + "/" + SEARCHDIAMOND;

        StringRequest getData = new StringRequest(Request.Method.POST, str,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        Calendar startResponse = Calendar.getInstance();
                        startResponse.setTimeInMillis(System.currentTimeMillis());
                        SimpleDateFormat sResponse = new SimpleDateFormat("HH:mm:ss");
                        System.out.println("    **startResponse --> " +
                                sResponse.format(startResponse.getTime()));

                        System.out.println("Response --> " + response);

                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            String success = jsonObject.getString("Success");

                            if (success.equals("1")) {

                                CommonDataUtility.response = response;

                                Intent startIntentService = new Intent(Intent.ACTION_SYNC, null,
                                        activity, Parse_JSON_FronResponse.class);
                                startService(startIntentService);

                            }
                        } catch (Exception e) {

                            if (pDialog != null) {

                                pDialog.dismiss();

                            }

                            System.out.println("APPException --> " + e.getMessage());

                            isCompleted = true;

                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                pDialog.dismiss();

                System.out.println("VoleyError --> " + error.getMessage());

                isCompleted = true;

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();

                param.put("LoginName", "admin");
                param.put("PassWord", "jaysnj");
                param.put("ShapeList", "R,PE,M,O,H,PR,CU,EM,SE,LR,SR,A,C,CB,CMB,CO,RB,RT,SB,ST,T,TR,OTH");
                param.put("FromCarat", "0.180");
                param.put("ToCarat", "99.990");
                param.put("FromClarity", "");//1
                param.put("ToClarity", "");//11
                param.put("FromColor", "");//1
                param.put("ToColor", "");//23
                param.put("FancyColorList", "");
                param.put("FancyIntensityList", "");
                param.put("FancyOvertoneList", "");
                param.put("CutList", "");
                param.put("PolishList", "");
                param.put("SymmetryList", "");
                param.put("FlourList", "");
                param.put("CertiList", "");
                param.put("ShadeList", "");
                param.put("LusterList", "");
                param.put("HAList", "");
                param.put("FromDisc", "");
                param.put("ToDisc", "");
                param.put("FromPrice", "");
                param.put("ToPrice", "");
                param.put("FromDiameter", "");
                param.put("ToDiameter", "");
                param.put("FromRatio", "");
                param.put("ToRatio", "");
                param.put("FromTotDepth", "");
                param.put("ToTotDepth", "");
                param.put("FromTable", "");
                param.put("ToTable", "");
                param.put("FromLength", "");
                param.put("ToLength", "");
                param.put("FromWidth", "");
                param.put("ToWidth", "");
                param.put("FromGirdlePer", "");
                param.put("ToGirdlePer", "");
                param.put("FromPAngle", "");
                param.put("ToPAngle", "");
                param.put("FromPHeight", "");
                param.put("ToPHeight", "");
                param.put("FromCAngle", "");
                param.put("ToCAngle", "");
                param.put("FromCHeight", "");
                param.put("ToCHeight", "");
                param.put("FromStarLength", "");
                param.put("ToStarLength", "");
                param.put("FromLowerHalf", "");
                param.put("ToLowerHalf", "");
                param.put("Culet", "");
                param.put("Girdle", "");
                param.put("KeyToSymbolLike", "");
                param.put("KeyToSymbolUnlink", "");

                param.put("TableInc", "");
                param.put("SideInc", "");
                param.put("TableNatts", "");
                param.put("SideNatts", "");
                param.put("TableOpen", "");
                param.put("SideOpen", "");

                param.put("CrownExtraFacet", "");
                param.put("PavalianExtraFacet", "");
                param.put("NOBGM", "");
                param.put("IS3EXACTIVE", "");
                param.put("IS3VGACTIVE", "");
                param.put("IS2VGACTIVE", "");

                param.put("StoneIdList", "");
                param.put("CertificateNoList", "");
                param.put("IsFancy", "0");
                param.put("SaveSearchName", "");
                param.put("SaveSearchID", "");
                param.put("IsTrading", "");
                param.put("IsSNJStock", "");

                System.out.println("Parameters --> " + param.toString());

                for (int i = 0; i < 35; i++) {

                    System.out.print("*");

                }

                Calendar start = Calendar.getInstance();
                start.setTimeInMillis(System.currentTimeMillis());
                SimpleDateFormat startTime = new SimpleDateFormat("HH:mm:ss");
                System.out.println("\n\n\n\n    **startRequest  --> " +
                        startTime.format(start.getTime()));

                return param;
            }
        };

        getData.setRetryPolicy(new DefaultRetryPolicy(600000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Adding request to request queue
        Volley.newRequestQueue(activity).add(getData);

    }

    @Override
    public void onBackPressed() {

        if (isCompleted) {

            super.onBackPressed();

            finish();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(activity).registerReceiver(updateUI,
                new IntentFilter("updateUI"));

    }


    public void freeMemory() { //This method will free unused space from memory/ram

        System.runFinalization();

        Runtime.getRuntime().gc();

        System.gc();

    }
}
