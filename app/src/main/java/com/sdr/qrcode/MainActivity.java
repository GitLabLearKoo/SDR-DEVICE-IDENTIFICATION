package com.sdr.qrcode;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.sdr.identification.RxSDRDeviceIdentification;
import com.sdr.lib.base.BaseActivity;
import com.sdr.lib.util.AlertUtil;

import java.util.List;

import io.reactivex.functions.Consumer;
import rx_activity_result2.Result;

public class MainActivity extends BaseActivity {


    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getClass().getSimpleName());
        textView = findViewById(R.id.text_view);
    }

    public void openScan(View view) {
        RxSDRDeviceIdentification
                .scan(getActivity())
                .subscribe(new Consumer<Result<FragmentActivity>>() {
                    @Override
                    public void accept(Result<FragmentActivity> fragmentActivityResult) throws Exception {
                        String code = RxSDRDeviceIdentification.Helper.getScanResult(fragmentActivityResult);
                        if (code != null) {
                            AlertUtil.showPositiveToastTop(code, "");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        AlertUtil.showNegativeToastTop(throwable.getMessage(), "");
                    }
                });
    }

    public void nfcScan(View view) {
        RxSDRDeviceIdentification
                .nfc(getActivity())
                .subscribe(new Consumer<Result<FragmentActivity>>() {
                    @Override
                    public void accept(Result<FragmentActivity> fragmentActivityResult) throws Exception {
                        int resultCode = fragmentActivityResult.resultCode();
                        if (resultCode == RESULT_OK) {
                            String code = RxSDRDeviceIdentification.Helper.getNfcResult(fragmentActivityResult);
                            if (code != null) {
                                AlertUtil.showPositiveToastTop(code, "");
                                textView.setText(code);
                            } else {
                                AlertUtil.showNegativeToastTop("code码为空", "");
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        AlertUtil.showNegativeToastTop(throwable.getMessage(), "");
                    }
                });
    }

    public void bluetooth(View view) {
        RxSDRDeviceIdentification
                .bluetooth()
                .subscribe(new Consumer<List<BluetoothDevice>>() {
                    @Override
                    public void accept(List<BluetoothDevice> bluetoothDevices) throws Exception {
                        StringBuilder sb = new StringBuilder();
                        for (BluetoothDevice device : bluetoothDevices) {
                            sb.append(device.getName() + ">>>" + device.getAddress() + ">>>" + device.getBondState() + "\n");
                        }
                        AlertUtil.showPositiveToastTop(sb.toString(), "");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        AlertUtil.showNegativeToastTop(throwable.getMessage(), "");
                    }
                });
    }

    public void wifiMac(View view) {
        RxSDRDeviceIdentification
                .wifi(getContext())
                .subscribe(new Consumer<String>() {
                               @Override
                               public void accept(String s) throws Exception {
                                   AlertUtil.showPositiveToastTop(s, "");
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                AlertUtil.showNegativeToastTop(throwable.getMessage(), "");
                            }
                        });
    }

    public void geneQRCode(View view) {
        startActivity(new Intent(getContext(), GenerateQRCodeActivity.class));
    }
}
