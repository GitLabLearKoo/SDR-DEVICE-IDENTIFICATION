package com.sdr.qrcode;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.sdr.identification.RxSDRDeviceIdentification;
import com.sdr.lib.base.BaseActivity;
import com.sdr.lib.util.AlertUtil;

import io.reactivex.functions.Consumer;
import rx_activity_result2.Result;

public class MainActivity extends BaseActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.main_textview);
    }

    public void openScan(View view) {
        RxSDRDeviceIdentification.scan(getActivity())
                .subscribe(new Consumer<Result<FragmentActivity>>() {
                    @Override
                    public void accept(Result<FragmentActivity> fragmentActivityResult) throws Exception {
                        String code = RxSDRDeviceIdentification.Helper.getScanResult(fragmentActivityResult);
                        if (code != null) {
                            AlertUtil.showPositiveToast(code);
                            textView.setText(code);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        AlertUtil.showNegativeToastTop(throwable.getMessage());
                    }
                });
    }
}
