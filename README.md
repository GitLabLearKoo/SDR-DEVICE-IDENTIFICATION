# SDR-DEVICE-IDENFICATION
项目引用了 https://github.com/yipianfengye/android-zxingLibrary 库

集成zxing二维码扫描和生成的库，自定义了扫描二维码的Activity，集成NFC扫描标识Activity

![](/screenshoot/demo.gif)


[demo-apk下载地址](/app/debug/app-debug.apk)

## gradle
project
```
allprojects {
    repositories {
        ...
        ...
        ...
        maven {
            url "https://jitpack.io"
        }
    }
}
```
module
```
dependencies {
    implementation 'com.github.sz-sdr:SDR-DEVICE-IDENTIFICATION:1.3.0'
}
```

## 权限
库中已经自动申请了权限，无需再手动申请，直接使用即可
```
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.VIBRATE" />

<!-- nfc 权限 -->
<uses-permission android:name="android.permission.NFC" />
<uses-feature
    android:name="android.hardware.nfc"
    android:required="true" />
<!--蓝牙-->
<uses-permission android:name="android.permission.BLUETOOTH" />
<!--无线-->
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

## 使用

1. 扫描识别二维码
    ```
    RxSDRDeviceIdentification
            .scan(getActivity())  // activity 或者 fragment
            .subscribe(new Consumer<Result<FragmentActivity>>() {
                @Override
                public void accept(Result<FragmentActivity> fragmentActivityResult) throws Exception {
                    // 使用自带的解析方法
                    String code = RxSDRDeviceIdentification.Helper.getScanResult(fragmentActivityResult);
                    if (code != null) {
                        AlertUtil.showPositiveToast(code);
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    AlertUtil.showNegativeToastTop(throwable.getMessage());
                }
            });
    ```

2. NFC识别功能
    ```
    RxSDRDeviceIdentification
            .nfc(getActivity())   // activity 或者 fragment
            .subscribe(new Consumer<Result<FragmentActivity>>() {
                @Override
                public void accept(Result<FragmentActivity> fragmentActivityResult) throws Exception {
                    // 使用自带的Helper解析
                    String code = RxSDRDeviceIdentification.Helper.getNfcResult(fragmentActivityResult);
                    if (code != null) {
                        AlertUtil.showPositiveToast(code);
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    AlertUtil.showNegativeToastTop(throwable.getMessage());
                }
            });
    ```

3. 已连接蓝牙列表
    ```
    RxSDRDeviceIdentification
            .bluetooth()
            .subscribe(new Consumer<List<BluetoothDevice>>() {
                @Override
                public void accept(List<BluetoothDevice> bluetoothDevices) throws Exception {
                    StringBuilder sb = new StringBuilder();
                    for (BluetoothDevice device : bluetoothDevices) {
                        sb.append(device.getName() + ">>>" + device.getAddress() + ">>>" + device.getBondState() + "\n");
                    }
                    AlertUtil.showPositiveToast(sb.toString());
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    AlertUtil.showNegativeToastTop(throwable.getMessage());
                }
            });
    ```
4. 正在连接的WiFi MAC
    ```
    RxSDRDeviceIdentification
            .wifi(getContext())
            .subscribe(new Consumer<String>() {
                           @Override
                           public void accept(String s) throws Exception {
                               AlertUtil.showPositiveToast(s);
                           }
                       },
                    new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            AlertUtil.showNegativeToastTop(throwable.getMessage());
                        }
                    });
    ```

5. 生成二维码
    ```
    Bitmap bitmap = RxSDRDeviceIdentification.createQRImage(content, 600);
    ```

