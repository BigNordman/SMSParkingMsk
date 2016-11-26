package com.nordman.big.smsparkingmsk;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.nordman.big.smsparkinglib.BaseSmsManager;

import java.util.Date;

/**
 * Created by s_vershinin on 15.01.2016.
 * Класс для формирования целевого СМС-сообщения
 */
class SmsManager extends BaseSmsManager {

    String parkNum = "";

    SmsManager(Context context) {
        super(context);
    }

    @Override
    public void updateSms() {
        sms = "";

        if (parkNum.equals("")) {
            sms += "*";
        } else {
            sms += parkNum + "*";
        }
        sms += regNum.toUpperCase() + "*" + hours ;
    }

    @Override
    public void showParkingScreen() {
        Intent intent = new Intent(context, ParkingActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void showMainScreen() {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void saveState() {
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor ed = prefs.edit();
        ed.putString("regNum", regNum);
        ed.putString("parkNum", parkNum);
        ed.putInt("status", appStatus);
        ed.putLong("sendDate", sendDate != null ? sendDate.getTime() : 0);
        ed.putLong("startParkingDate", startParkingDate != null ? startParkingDate.getTime() : 0);
        ed.putString("hours", hours);
        ed.apply();
    }

    @Override
    public void restoreState() {
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(context);
        regNum = prefs.getString("regNum", "");
        parkNum = prefs.getString("parkNum", "");
        appStatus = prefs.getInt("status", STATUS_INITIAL);
        sendDate = new Date(prefs.getLong("sendDate",0));
        startParkingDate = new Date(prefs.getLong("startParkingDate",0));
        if (!parkNum.equals("")) currentZone = geoMgr.getParkZone(Integer.parseInt(parkNum));
        hours = prefs.getString("hours", "1");
    }
}
