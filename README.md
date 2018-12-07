# SDRQRcodeLibrary
项目引用了 https://github.com/yipianfengye/android-zxingLibrary 库

集成zxing二维码扫描和生成的库，自定义了扫描二维码的Activity

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
    implementation 'com.github.HyfSunshine:SDRQRcodeLibrary:1.0'
}
```

## 权限
```
<uses-permission android:name="android.permission.CAMERA"/>
<uses-permission android:name="android.permission.VIBRATE"/>
```
CAMERA权限在Android6.0+是运行是权限，需要手动申请

## 使用

1. 扫描二维码
    ```
    // 开启二维码扫描
    QRScanActivity.start(this, 100);
    ```

2. 扫描成功结果
    ```
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String string = bundle.getString(CodeUtils.RESULT_STRING);
            textView.setText(string);
        }
    }
    ```


