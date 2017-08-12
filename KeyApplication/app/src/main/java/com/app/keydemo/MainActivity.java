package com.app.keydemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {


    private EditText inputbox1, inputbox2,
            inputbox3, inputbox4,
            inputbox5, inputbox6, inputbox7;
    private LinearLayout boxesContainer;
    private LicenseKeyboardUtil keyboardUtil;
    private EditText carid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        carid= (EditText) findViewById(R.id.carID);
        inputbox1 = (EditText) this.findViewById(R.id.et_car_license_inputbox1);
        inputbox2 = (EditText) this.findViewById(R.id.et_car_license_inputbox2);
        inputbox3 = (EditText) this.findViewById(R.id.et_car_license_inputbox3);
        inputbox4 = (EditText) this.findViewById(R.id.et_car_license_inputbox4);
        inputbox5 = (EditText) this.findViewById(R.id.et_car_license_inputbox5);
        inputbox6 = (EditText) this.findViewById(R.id.et_car_license_inputbox6);
        inputbox7 = (EditText) this.findViewById(R.id.et_car_license_inputbox7);
        Button button = (Button) this.findViewById(R.id.btn_add_car);
        button.setOnClickListener(this);

        ImageButton button2 =(ImageButton) findViewById(R.id.cancel);
                button2.setOnClickListener(this);
        boxesContainer = (LinearLayout) this.findViewById(R.id.ll_car_license_inputbox_content);

        //输入车牌完成后的intent过滤器
        IntentFilter finishFilter = new IntentFilter(LicenseKeyboardUtil.INPUT_LICENSE_COMPLETE);

        final BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String license = intent.getStringExtra(LicenseKeyboardUtil.INPUT_LICENSE_KEY);
              //  Toast.makeText(MainActivity.this,license,Toast.LENGTH_LONG).show();
                if (license != null && license.length() ==7) {
                    boxesContainer.setVisibility(View.GONE);
                    if (keyboardUtil != null) {
                        keyboardUtil.hideKeyboard();
                    }
                    Toast.makeText(MainActivity.this,license,Toast.LENGTH_LONG).show();
//                    AlertDialog alertDialog;
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                    builder.setMessage("车牌号为:" + license);
//                    alertDialog = builder.create();
//                    alertDialog.setCancelable(true);
//                    alertDialog.show();
                }else {
                    Toast.makeText(MainActivity.this,"数据不全",Toast.LENGTH_LONG).show();
                }
               // MainActivity.this.unregisterReceiver(this);
            }
        };
        this.registerReceiver(receiver, finishFilter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_car:
                boxesContainer.setVisibility(View.VISIBLE);

                keyboardUtil = new LicenseKeyboardUtil(this, new EditText[]{inputbox1, inputbox2, inputbox3,
                        inputbox4, inputbox5, inputbox6, inputbox7});
                keyboardUtil.clear();
                keyboardUtil.showKeyboard();
                break;
            case R.id.cancel:

                boxesContainer.setVisibility(View.GONE);
                if (keyboardUtil != null) {
                    keyboardUtil.hideKeyboard();
                    keyboardUtil.clear();
                }
                break;
        }
    }
}
