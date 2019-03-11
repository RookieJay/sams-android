package pers.zjc.sams.module.register.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zp.android.zlib.base.BaseFragment;
import com.zp.android.zlib.utils.StringUtils;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pers.zjc.sams.R;
import pers.zjc.sams.app.SamsApplication;
import pers.zjc.sams.common.ScmpUtils;
import pers.zjc.sams.data.entity.User;
import pers.zjc.sams.module.face.PermissionAcitivity;
import pers.zjc.sams.module.main.MainActivity;
import pers.zjc.sams.module.register.DaggerRegisterComponent;
import pers.zjc.sams.module.register.RegisterModule;
import pers.zjc.sams.module.register.contract.RegisterContract;
import pers.zjc.sams.module.register.presenter.RegisterPresenter;

public class RegisterFragment extends BaseFragment implements RegisterContract.View, View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.bar_right)
    TextView barRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lbl_acoount)
    TextView lblAcoount;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.lbl_pwd)
    TextView lblPwd;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.lbl_org)
    TextView lblOrg;
    @BindView(R.id.rb_student)
    RadioButton rbStudent;
    @BindView(R.id.rb_teacher)
    RadioButton rbTeacher;
    @BindView(R.id.lbl_device_no)
    TextView lblDeviceNo;
    @BindView(R.id.txt_device_no)
    TextView txtDeviceNo;
    @BindView(R.id.rl_device_no)
    RelativeLayout rlDeviceNo;
    @BindView(R.id.lbl_face)
    TextView lblFace;
    @BindView(R.id.txt_face)
    TextView txtFace;
    @BindView(R.id.rl_face)
    RelativeLayout rlFace;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.view_line_last)
    View viewLineLast;
    @BindView(R.id.view_line)
    View viewLine;
    Unbinder unbinder;

    @Inject
    RegisterPresenter presenter;

    private User user = new User();
    String deviceId;
    MainActivity currentActivity;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof MainActivity) {
            currentActivity = (MainActivity) activity;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerRegisterComponent.builder()
                               .appComponent(SamsApplication.getComponent())
                               .registerModule(new RegisterModule(this))
                               .build()
                               .inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        unbinder = ButterKnife.bind(this, getView());
        initView();
        initData();
    }

    private void initData() {
        user.setRole(1);
        if (user.getRole() == 1) {
            presenter.loadImei();
        }
    }

    private void initView() {
        barTitle.setText("用户注册");
        rbStudent.setChecked(true);
        rbStudent.setOnCheckedChangeListener(this);
        rbTeacher.setOnCheckedChangeListener(this);
        btnRegister.setOnClickListener(this);
        rlDeviceNo.setVisibility(View.VISIBLE);
        rlFace.setVisibility(View.GONE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                String account = etAccount.getText().toString();
                String password = etPassword.getText().toString();
                if (StringUtils.isEmpty(account)) {
                    showShortToast("账号不能为空");
                    return;
                }
                if (StringUtils.isEmpty(password)) {
                    showShortToast("账号不能为空");
                    return;
                }
                user.setAccount(account);
                user.setPassword(password);
                if (user.getRole() == 1) {
                    deviceId = txtDeviceNo.getText().toString();
                    if (StringUtils.isEmpty(deviceId)) {
                        showShortToast("设备编码获取失败，请检查是否开启读取本机识别码权限");
                        return;
                    }
                    Log.d("提交的deviceId", deviceId);
                    presenter.register(user, deviceId);
                } else {
                    presenter.register(user, "");
                }
                break;
            default:
                break;
        }
    }

    public void back() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (getActivity() != null) {
                    getActivity().finish();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed()) {
            if (isChecked) {
                switch (buttonView.getId()) {
                    case R.id.rb_student:
                        rlDeviceNo.setVisibility(View.VISIBLE);
//                        rlFace.setVisibility(View.VISIBLE);
                        viewLineLast.setVisibility(View.VISIBLE);
                        viewLine.setVisibility(View.VISIBLE);
                        user.setRole(1);
                        break;
                    case R.id.rb_teacher:
                        rlDeviceNo.setVisibility(View.GONE);
                        rlFace.setVisibility(View.GONE);
                        viewLineLast.setVisibility(View.GONE);
                        viewLine.setVisibility(View.GONE);
                        user.setRole(2);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public void fillImei(String imei) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (StringUtils.isEmpty(imei)) {
                    txtDeviceNo.setText("本机识别码获取失败");
                    return;
                }
                txtDeviceNo.setText(imei);
            }
        });
    }

    @Override
    public void showMessage(String message) {
        showShortToast(message);
    }

    @Override
    public void showNetWorkErro() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showMessage(getResources().getString(R.string.toast_fail_to_connect_server));
            }
        });
    }
}
