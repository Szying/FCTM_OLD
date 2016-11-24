package com.aaronlife.thecarebank.nurse;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.aaronlife.actionbartabdrawer.R;

/**
 * Created by aaron on 3/18/15.
 */
public class DialogLogin extends DialogFragment implements IHttpCompleted
{
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        // 定義對話盒物件建構器
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // 取得介面實體化物件
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_login, null);

        final TextView txtUsername = (TextView)view.findViewById(R.id.editUsername);
        final TextView txtPassword = (TextView)view.findViewById(R.id.editPassword);

        builder.setPositiveButton(R.string.longIn, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                HttpAction.login(txtUsername.getText().toString(), txtPassword.getText().toString(), DialogLogin.this);
            }
        });

        builder.setNeutralButton(R.string.new_id, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });

        builder.setNegativeButton(R.string.close, null);

        // 設定對話盒介面
        builder.setView(view);
        builder.setCancelable(false);

        return builder.create(); // 產生對話盒實體並回傳
    }

    @Override
    public void doNotify(String result)
    {
        Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void doError(int resID)
    {
        Toast.makeText(getActivity(), getText(resID), Toast.LENGTH_SHORT).show();
    }
}
