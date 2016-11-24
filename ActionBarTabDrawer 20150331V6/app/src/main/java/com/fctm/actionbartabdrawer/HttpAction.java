package com.fctm.actionbartabdrawer;


/**
 * Created by aaron on 3/27/15.
 */
public class HttpAction
{
    public static void login(String type, String account, String password, IHttpCompleted callback)
    {
        try
        {
            new AsyncHttpConnection(callback, "login",type, account, password).execute().get();
        }
        catch (Exception e)
        {
            callback.doError(R.string.login_err);
        }
    }

    public static void login1(String account, String password, IHttpCompleted callback)
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

    public synchronized static void logout(String account, IHttpCompleted callback)
    {
        try
        {
            new AsyncHttpConnection(callback, "logout", "member", account, "").execute().get();
        }
        catch (Exception e)
        {
            callback.doError(R.string.logout_err);
        }
    }
    public synchronized static void insert(String type, String data, IHttpCompleted callback)
    {
        try
        {
            new AsyncHttpConnection(callback, "insert", type, "", data).execute().get();
        }
        catch (Exception e)
        {
            callback.doError(R.string.insert_err);
        }
    }

    public synchronized static void update(String type, String number, String data, IHttpCompleted callback)
    {
        try
        {
            new AsyncHttpConnection(callback, "update", type, number, data).execute().get();
        }
        catch (Exception e)
        {
            callback.doError(R.string.update_err);
        }
    }

    public synchronized static void query(String type, String number, String data, IHttpCompleted callback)
    {
        try
        {
            new AsyncHttpConnection(callback, "query", type, number, data).execute().get();
        }
        catch (Exception e)
        {
            callback.doError(R.string.query_err);
        }
    }

    public synchronized static void delete(String type, String number, IHttpCompleted callback)
    {
        try
        {
            new AsyncHttpConnection(callback, "delete", type, number, "").execute().get();
        }
        catch (Exception e)
        {
            callback.doError(R.string.delete_err);
        }
    }

}



