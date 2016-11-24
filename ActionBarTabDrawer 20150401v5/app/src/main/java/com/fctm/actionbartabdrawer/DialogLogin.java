package com.fctm.actionbartabdrawer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



/**
 * Created by aaron on 3/18/15.
 */
public class DialogLogin extends DialogFragment implements IHttpCompleted {

    public static String muserType;
    public static TextView txtUsername;
    public static TextView txtPassword;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // 定義對話盒物件建構器
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // 取得介面實體化物件
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_login, null);

         txtUsername = (TextView) view.findViewById(R.id.editUsername);
        txtPassword = (TextView) view.findViewById(R.id.editPassword);

        Spinner mchooseUsertype = (Spinner) view.findViewById(R.id.chooseUsertype);
        ArrayAdapter<CharSequence> adapList =
                ArrayAdapter.createFromResource(getActivity(),
                        R.array.UserType_list,
                        android.R.layout.simple_spinner_item);
        adapList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mchooseUsertype.setAdapter(adapList);
        mchooseUsertype.setOnItemSelectedListener(spnListener);



//        final ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, strArray);
//        chooseUsertype.setAdapter(adapter);


        builder.setPositiveButton(R.string.longIn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(MainActivity.userType == 0){
                   // Toast.makeText(view.getContext(),"Login userType"+txtUsername.getText().toString(),Toast.LENGTH_LONG).show();

                    HttpAction.login("employer",txtUsername.getText().toString(), txtPassword.getText().toString(), DialogLogin.this,"Users");
                    ((MainActivity)getActivity()).sectionPagerAdapterMas();

                }
                else if(MainActivity.userType == 1){
                    HttpAction.login1(txtUsername.getText().toString(), txtPassword.getText().toString(), DialogLogin.this,"Users");
                    ((MainActivity)getActivity()).sectionPagerAdapterLab();
                }
                else{
                };
            }
        });

        builder.setNeutralButton(R.string.new_id, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DialogRegister dlg = new DialogRegister();
                dlg.show(getFragmentManager(), "DialogRegister");

            }
        });

        builder.setNegativeButton(R.string.close, null);

        // 設定對話盒介面
        builder.setView(view);
        builder.setCancelable(false);

        return builder.create(); // 產生對話盒實體並回傳


    }




    @Override
    public void doNotify(String result) {
     //   Toast.makeText(getActivity(), "doNotify result="+result, Toast.LENGTH_SHORT).show();

        if(result.indexOf("true") == 0)

            HttpAction.query("member", txtUsername.getText().toString(), "", new GetInfo(getActivity()), "Users");




    }

    @Override
    public void doError(int resID) {
        Toast.makeText(getActivity(), getText(resID), Toast.LENGTH_SHORT).show();
    }

    // 建立Spinner傾聽器
    Spinner.OnItemSelectedListener spnListener = new Spinner.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
            muserType = parent.getSelectedItem().toString();

            if (muserType.equals(getString(R.string.Master))) {
                MainActivity.userType = UserId.type0;
            }

            else if (muserType.equals("Master")){
                MainActivity.userType = UserId.type0;
            }

            else if (muserType.equals("外傭")){
                MainActivity.userType = UserId.type1;
            }

            else if(muserType.equals("Foreign Caretaker")){
                MainActivity.userType = UserId.type1;
            }

        }


        public void onNothingSelected(AdapterView<?> arg0) {
        }
    };
}
