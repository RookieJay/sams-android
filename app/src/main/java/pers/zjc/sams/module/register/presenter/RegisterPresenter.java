package pers.zjc.sams.module.register.presenter;

import com.google.gson.Gson;

import android.text.TextUtils;
import android.util.Log;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zp.android.zlib.utils.PhoneUtils;
import com.zp.android.zlib.utils.SystemUtil;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import okhttp3.Call;
import pers.zjc.sams.BuildConfig;
import pers.zjc.sams.app.AppConfig;
import pers.zjc.sams.common.Const;
import pers.zjc.sams.data.entity.Device;
import pers.zjc.sams.data.entity.Result;
import pers.zjc.sams.data.entity.User;
import pers.zjc.sams.module.register.contract.RegisterContract;
import pers.zjc.sams.module.register.model.RegisterModel;

public class RegisterPresenter implements RegisterContract.Presenter {

    private RegisterContract.View view;
    @Inject
    RegisterModel model;
    @Inject
    AppConfig appConfig;
    @Inject
    Executor executor;

    @Inject
    RegisterPresenter(RegisterContract.View view) {
        this.view = view;
    }

    @Override
    public void loadImei() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                String imei = null;
                try {
                    imei = PhoneUtils.getIMEI();
                }
                catch (SecurityException e) {
                    e.printStackTrace();
                }
                view.fillImei(TextUtils.isEmpty(imei) ? "" : imei);
            }
        });
    }

    @Override
    public void register(User user, String deviceId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Device device = new Device();
                    device.setDeviceId(deviceId);
                    device.setDeviceModel(SystemUtil.getDeviceBrand()+" "+SystemUtil.getSystemModel());
                    device.setDeviceVersion("Android "+SystemUtil.getSystemVersion());
                    Wrapper wrapper = new Wrapper();
                    wrapper.setUser(user);
                    wrapper.setDevice(device);
                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("wrapper", wrapper);
                    Gson gson = new Gson();
                    String json = gson.toJson(wrapper);
                    Log.d("json", json);
                    Map<String, Object> userMap = new LinkedHashMap<>();
                    userMap.put("user", user);
                    Map<String, Object> deviceMap = new LinkedHashMap<>();
                    deviceMap.put("device", device);
                    String userJson = gson.toJson(user);
                    String deviceJson = gson.toJson(device);
                    Log.d("userJson", userJson);
                    Log.d("deviceJson", deviceJson);
                    Map test = new HashMap();
                    test.put(null, "14148625610379871511");
                    test.put(null, "OnePlus ONEPLUS A3010");
                    Log.d("test", gson.toJson(test));


                    Result result = model.register(userJson, deviceJson, json);
                    if (result != null) {
                        if (result.getCode().equals(Const.HttpStatusCode.HttpStatus_200)) {
                            view.showMessage(result.getMessage());
                            view.back();
                        } else {
                            view.showMessage(result.getMessage());
                        }
                    }
                    else {
                        view.showNetWorkErro();
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                    Log.d("捕获异常", e.getMessage());
                    view.showMessage(e.getMessage());
                }
            }
        });


    }

    public class Wrapper {
        private User user;
        private Device device;

        public Wrapper() {
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public Device getDevice() {
            return device;
        }

        public void setDevice(Device device) {
            this.device = device;
        }
    }


}
