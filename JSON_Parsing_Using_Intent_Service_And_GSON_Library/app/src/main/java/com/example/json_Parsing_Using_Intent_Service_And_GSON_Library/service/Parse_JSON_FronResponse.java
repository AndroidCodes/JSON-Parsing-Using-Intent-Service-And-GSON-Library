package com.example.json_Parsing_Using_Intent_Service_And_GSON_Library.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.example.json_Parsing_Using_Intent_Service_And_GSON_Library.gson_modal.Parameters;
import com.example.json_Parsing_Using_Intent_Service_And_GSON_Library.utility.CommonDataUtility;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by peacock on 5/9/16.
 */
public class Parse_JSON_FronResponse extends IntentService {

    public Parse_JSON_FronResponse() {
        super("");

        setIntentRedelivery(true);

    }

    public Parse_JSON_FronResponse(String name) {
        super(name);

        setIntentRedelivery(true);

    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Gson gson = new Gson();

        Calendar start = Calendar.getInstance();
        start.setTimeInMillis(System.currentTimeMillis());
        SimpleDateFormat startTime = new SimpleDateFormat("HH:mm:ss");
        System.out.println("    **startTime     --> " +
                startTime.format(start.getTime()));

        CommonDataUtility.parameters = gson.fromJson(CommonDataUtility.response, Parameters.class);

        Calendar end = Calendar.getInstance();
        end.setTimeInMillis(System.currentTimeMillis());
        SimpleDateFormat endTime = new SimpleDateFormat("HH:mm:ss");
        System.out.println("    **EndTime       --> " +
                endTime.format(end.getTime()));

        System.out.println("    **Size          --> " +
                CommonDataUtility.parameters.getResult().size());

        Intent updateUI = new Intent("updateUI");
        updateUI.putExtra("opCode", "SearchDiamond");
        LocalBroadcastManager.getInstance(this).sendBroadcast(updateUI);

    }
}
