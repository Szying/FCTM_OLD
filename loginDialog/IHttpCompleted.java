package com.aaronlife.thecarebank.nurse;

/**
 * Created by aaron on 3/26/15.
 */
public interface IHttpCompleted
{
    public void doNotify(String result);
    public void doError(int resID);
}
