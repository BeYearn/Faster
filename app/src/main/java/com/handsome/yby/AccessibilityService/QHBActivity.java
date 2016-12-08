package com.handsome.yby.AccessibilityService;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.handsome.yby.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QHBActivity extends AppCompatActivity {

    @BindView(R.id.btn_startService)
    Button btnStartService;
    @BindView(R.id.common_toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_times)
    TextView tvTimes;
    @BindView(R.id.btn_close)
    Button btnClose;

    private boolean isCleaned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        checkUsedCount();
    }

    private void checkUsedCount() {
        SharedPreferences sp = getSharedPreferences("common_sp", Context.MODE_PRIVATE);
        int used_count = sp.getInt("used_count", 0);
        String str = getResources().getString(R.string.hav_used_times);
        String times = String.format(str, used_count);
        tvTimes.setText(times);

        if(used_count>=3){
            isCleaned=true;
            Snackbar.make(tvContent, R.string.used_clean,Snackbar.LENGTH_LONG).show();

        }else {
            SharedPreferences.Editor edit = sp.edit();
            used_count++;
            edit.putInt("used_count", used_count);
            edit.commit();
        }

    }

    private void initView() {
        setContentView(R.layout.activity_qhb);

        ButterKnife.bind(this);
        toolbar.setTitle(R.string.title);
        setSupportActionBar(toolbar);
    }


    @OnClick(R.id.btn_startService)
    public void click(View v){
        if(isCleaned){
            Toast.makeText(this, R.string.cleaned_alert,Toast.LENGTH_LONG).show();
        }else {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
        }
    }

    @OnClick(R.id.btn_close)
    public void clickClose(View v){
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        startActivity(intent);
        Toast.makeText(this, R.string.close_service_alert,Toast.LENGTH_LONG).show();
        this.finish();
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this,"点击home键吧",Toast.LENGTH_LONG).show();
    }
}
