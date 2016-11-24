package com.aaronlife.thecarebank.nurse;

import com.aaronlife.actionbartabdrawer.R;

/**
 * Created by aaron on 3/27/15.
 */
public class HttpAction
{
    public static void login(String account, String password, IHttpCompleted callback)
    {
        try 
        {
            new AsyncHttpConnection(callback, "login", "member", account, password).execute().get();
        } 
        catch (Exception e) 
        {
            callback.doError(R.string.login_err);
        }
    }

    public static void logout(String account)
    {

    }

    public static void insert(String type, String data)
    {

    }

    public static void update(String type, String number, String data)
    {

    }

    public static void query(String type, String number, String data)
    {

    }

    public static void delete(String type, String number)
    {

    }

    public static void link(String emp_id, String nurse_id)
    {

    }
}
