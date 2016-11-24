package com.fctm.actionbartabdrawer;

import android.app.Activity;

/**
 * Created by Red on 2015/4/1.
 */
public class GetInfo implements IHttpCompleted
{
    Activity activity;
    public static String result = null;
    public GetInfo(Activity activity)
    {
        this.activity = activity;
    }
    @Override
    public void doNotify(String result) {
       // Toast.makeText(activity, "Get Info" + result, Toast.LENGTH_LONG).show();
        this.result = result;
    }

    @Override
    public void doError(int resID) {

    }
}
