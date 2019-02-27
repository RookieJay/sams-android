package pers.zjc.sams.module.sign.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.zp.android.zlib.base.BaseFragment;
import com.zp.android.zlib.utils.StringUtils;
import com.zp.android.zlib.utils.TimeUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pers.zjc.sams.R;
import pers.zjc.sams.app.AppConfig;
import pers.zjc.sams.app.SamsApplication;
import pers.zjc.sams.common.IPUtils;
import pers.zjc.sams.data.entity.Course;
import pers.zjc.sams.data.entity.SignRecord;
import pers.zjc.sams.module.leave.view.CoursePopup;
import pers.zjc.sams.module.sign.DaggerSignComponent;
import pers.zjc.sams.module.sign.SignModule;
import pers.zjc.sams.module.sign.contract.SignContract;
import pers.zjc.sams.module.sign.presenter.SignPresenter;
import pers.zjc.sams.widget.roundedimageview.RoundedImageView;

public class SignFragment extends BaseFragment implements SignContract.View, View.OnClickListener, CoursePopup.PopupItemClick {

    private static final int MSG_ONE = 1;
    @Inject
    SignPresenter presenter;
    @Inject
    AppConfig appConfig;

    Unbinder unbinder;
    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.txt_char)
    TextView txtChar;
    @BindView(R.id.tv_course)
    TextView tvCourse;
    @BindView(R.id.iv_course_icon)
    ImageView ivCourseIcon;
    @BindView(R.id.rl_choose_course)
    RelativeLayout rlChooseCourse;
    @BindView(R.id.txt_current_location)
    TextView txtCurrentLocation;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.rl_current_location)
    RelativeLayout rlCurrentLocation;
    @BindView(R.id.txt_sign)
    TextView txtSign;
    @BindView(R.id.tv_sign_time)
    TextView tvSignTime;
    @BindView(R.id.iv_sign)
    RoundedImageView ivSign;
    @BindView(R.id.iv_locate)
    RoundedImageView ivLocate;
    @BindView(R.id.fl_sign)
    FrameLayout flSign;
    //BaiduMap
    private BaiduMap baiduMap;
    public LocationClient mLocationClient;
    private boolean isFirstLocate = true;

    private Activity curActivity;

    private int courseId;
    private String location;
    private double mCurrentLat;
    private double mCurrentLon;
    private boolean isSigned;
    private String curTime;
    TimeHandler handler = new TimeHandler(this);

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sign;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        curActivity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerSignComponent.builder().appComponent(SamsApplication.getComponent())
                .signModule(new SignModule(this))
                .build()
                .inject(this);
        SDKInitializer.initialize(curActivity.getApplicationContext());
        mLocationClient = new LocationClient(getContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        unbinder = ButterKnife.bind(this, getView());
        initMapView();
        initPermission();
        initView();
    }

    private void initView() {
        initTimer();
        btnBack.setOnClickListener(this);
        rlChooseCourse.setOnClickListener(this);
        ivLocate.setOnClickListener(this);
        flSign.setOnClickListener(this);
    }

    @SuppressLint("HandlerLeak")
    private void initTimer() {
        new TimeThread().start();
    }

    private void updateTime() {
        curTime = TimeUtils.date2String(new Date());
        if (tvSignTime != null) {
            tvSignTime.setText(curTime);
        }
    }


    private void initMapView() {
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        // 删除百度地图LoGo
        mapView.removeViewAt(1);
        // 对定位的图标进行配置，需要MyLocationConfiguration实例，这个类是用设置定位图标的显示方式的
        MyLocationConfiguration.LocationMode locationMode = MyLocationConfiguration.LocationMode.FOLLOWING;
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.me);
        baiduMap.setMyLocationConfiguration(new MyLocationConfiguration(locationMode, true, bitmapDescriptor));
    }

    private void initPermission() {
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(curActivity.getApplicationContext(), Manifest.
                permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(curActivity.getApplicationContext(), Manifest.
                permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(curActivity.getApplicationContext(), Manifest.
                permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (!permissionList.isEmpty()) {
            String []permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(curActivity, permissions, 1);
        } else {
            requestLocation();

        }
    }

    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        //每x秒更新位置
//        option.setScanSpan(3000);
        option.setIsNeedAddress(true);
        // 打开gps
        option.setOpenGps(true);
        //Device_Sensors：传感器模式
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);

        // 可选，默认gcj02，设置返回的定位结果坐标系
        option.setCoorType("bd09ll");
        mLocationClient.setLocOption(option);

    }

    private void navigateTo(BDLocation location) {
        navigateTo(location, false);
    }

    private void navigateTo(BDLocation location, boolean isForce) {
        if (isFirstLocate) {
            Log.d("isFirstLocate", "自动定位");
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            //在地图中央的地方画圆：颜色0x2201A4F1
            OverlayOptions ooCircle = new CircleOptions().fillColor(0x2201A4F1)
                    .center(ll).stroke(new Stroke(5, 0x2201A4F1))
                    .radius(200);
            baiduMap.addOverlay(ooCircle);
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);
            //缩放级别 视野最大 值越大 视野最小 4-21
            update = MapStatusUpdateFactory.zoomTo(17f);
            baiduMap.animateMapStatus(update);
            isFirstLocate = false;
        }
        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        locationBuilder.latitude(location.getLatitude());
        locationBuilder.longitude(location.getLongitude());
        MyLocationData locationData = locationBuilder.build();
        baiduMap.setMyLocationData(locationData);
    }

    public void locate() {
        Log.d("手动", "定位");
        LatLng ll = new LatLng(mCurrentLat, mCurrentLon);
        OverlayOptions ooCircle = new CircleOptions()
                .fillColor(0x2201A4F1)
                .center(ll).stroke(new Stroke(5, 0x2201A4F1))
                .radius(200);
        baiduMap.addOverlay(ooCircle);
        MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
        baiduMap.animateMapStatus(update);
        //缩放级别 4视野最大 值越大 21视野最小
        update = MapStatusUpdateFactory.zoomTo(17f);
        baiduMap.animateMapStatus(update);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            showShortToast("必须同意所有权限才能使用本程序");
                            back();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    showShortToast("发生未知错误");
                    back();
                }
                break;
            default:
        }
    }

    private void back() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                FragmentManager fm = getFragmentManager();
                if (fm != null) {
                    fm.popBackStackImmediate();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(null != handler) {
            handler.removeMessages(MSG_ONE);
            handler = null;
            Log.d("SignFragment", "release Handler success");
        }
        unbinder.unbind();
        mLocationClient.stop();
        if (mapView != null) {
            mapView.onDestroy();
        }
        baiduMap.setMyLocationEnabled(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                back();
                break;
            case R.id.rl_choose_course:
                presenter.loadCourse();
                break;
            case R.id.fl_sign:
                if (isSigned) {
                    showShortToast("您已经签到");
                    return;
                }
                if (courseId == 0) {
                    showShortToast("请选择课程");
                    return;
                }
                if (StringUtils.isEmpty(location)) {
                    showShortToast("未获取到当前位置，请重新定位");
                    return;
                }
                String ip = IPUtils.getIP(getContext());
                if (StringUtils.isEmpty(ip)) {
                    showShortToast("未获取到当前网络信息，请重新打开网络");
                    return;
                }
                if (StringUtils.isEmpty(appConfig.getUserId())) {
                    showShortToast("未获取到当前用户信息，请重新登录");
                    return;
                }
                SignRecord record = new SignRecord();
                record.setCourseId(courseId);
                record.setLocation(location);
                record.setSignIp(ip);
                record.setStuId(Integer.valueOf(appConfig.getUserId()));
                presenter.sign(record);
                break;
            case R.id.iv_locate:
                showShortToast("回到当前位置");
                locate();
                break;
            default:
                break;
        }
    }

    @Override
    public void showCoursePopupWindow(List<Course> data) {
        SignFragment context = this;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CoursePopup coursePopup = new CoursePopup(getContext(), context);
                coursePopup.setData(data);
                coursePopup.setPopupGravity(Gravity.CENTER).showPopupWindow();
            }
        });

    }

    @Override
    public void showMessage(String msg) {
        showShortToast(msg);
    }

    @Override
    public void showNetWorkErro() {
        showShortToast(getResources().getString(R.string.toast_fail_to_connect_server));
    }

    @Override
    public void onPopupItemclick(Course data) {
        tvCourse.setText(data.getName());
        courseId = data.getId();
        //验证本课程是否已签到，待添加
//        presenter.validate();
        showUnSignedView();
    }

    private void showUnSignedView() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ivSign.setBackground(ContextCompat.getDrawable(curActivity.getApplicationContext(), R.drawable.shape_unsigned_btn));
                txtSign.setText(getResources().getString(R.string.txt_unsigned));
                tvSignTime.setVisibility(View.VISIBLE);
                isSigned = false;
            }
        });
    }

    @Override
    public void showSignedView() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ivSign.setBackground(ContextCompat.getDrawable(curActivity.getApplicationContext(), R.drawable.shape_signed_btn));
                txtSign.setText(getResources().getString(R.string.txt_signed));
                tvSignTime.setVisibility(View.GONE);
                isSigned = true;
            }
        });

    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(final BDLocation bdLocation) {
            Log.d("onReceiveLocation: ", "onReceiveLocation: ");
            if (bdLocation == null || mapView == null) {
                return;
            }
            mCurrentLat = bdLocation.getLatitude();
            mCurrentLon = bdLocation.getLongitude();
            baiduMap.clear();
            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                bdLocation.getSpeed();// 速度 单位：km/h
                bdLocation.getSatelliteNumber();// 卫星数目
                bdLocation.getAltitude();// 海拔高度 单位：米
                bdLocation.getGpsAccuracyStatus();// *****gps质量判断*****
                showShortToast("gps定位成功");
                navigateTo(bdLocation);

            } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                // 运营商信息
                if (bdLocation.hasAltitude()) {// *****如果有海拔高度*****
                    bdLocation.getAltitude();// 单位：米
                }
                bdLocation.getOperators();   // 运营商信息
//                showShortToast("网络定位成功");
                navigateTo(bdLocation);

            } else if (bdLocation.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
//                showShortToast("离线定位成功");
                navigateTo(bdLocation);

            } else if (bdLocation.getLocType() == BDLocation.TypeServerError) {
                showShortToast("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");

            } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkException) {
                showShortToast("网络不通导致定位失败，请检查网络是否通畅");

            } else if (bdLocation.getLocType() == BDLocation.TypeCriteriaException) {
                showShortToast("\"法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    StringBuilder currentPosition = new StringBuilder();
                    //注意：百度坐标是先纬度（Lat）再经度（Lng/Lon），其他坐标是先经度再纬度，
//                    Point point = new Point(location.getLatitude(), location.getLongitude());
                    currentPosition.append("纬度：").append(mCurrentLat)
                            .append("\n");
                    currentPosition.append("经度：").append(mCurrentLon)
                            .append("\n");
                    currentPosition.append(bdLocation.getProvince());
                    currentPosition.append(bdLocation.getCity());
                    currentPosition.append(bdLocation.getDistrict());
                    currentPosition.append(bdLocation.getStreet()).append("\n");
                    currentPosition.append("定位方式：");
                    location = bdLocation.getProvince()+bdLocation.getCity()+bdLocation.getDistrict()+bdLocation.getStreet();
                    Log.d("location", location);
                    if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {
                        currentPosition.append("GPS");
                    } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                        currentPosition.append("网络");
                    }
                    tvLocation.setText(currentPosition);
                }
            });
        }
    }


    static class TimeHandler extends Handler {

        private WeakReference<SignFragment> weakReference;

        public TimeHandler(SignFragment fragment) {
            weakReference = new WeakReference<>(fragment);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {
            final SignFragment fragment = weakReference.get();
            super.handleMessage(msg);
            if (null != fragment && fragment.isVisible()) {
                switch (msg.what) {
                    case MSG_ONE:
                        fragment.updateTime();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    //开一个线程继承Thread
    public class TimeThread extends Thread {
        //重写run方法
        @Override
        public void run() {
            super.run();
            // do-while  一 什么什么 就
            do {
                try {
                    //每隔一秒 发送一次消息
                    Thread.sleep(1000);
                    Message msg = new Message();
                    //消息内容 为MSG_ONE
                    msg.what = MSG_ONE;
                    //发送
                    if (handler != null) {
                        handler.sendMessage(msg);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (true);
        }
    }



}
