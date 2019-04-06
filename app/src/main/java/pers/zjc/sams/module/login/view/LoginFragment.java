package pers.zjc.sams.module.login.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zp.android.zlib.base.BaseFragment;
import com.zp.android.zlib.utils.KeyboardUtils;
import com.zp.android.zlib.utils.StringUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pers.zjc.sams.R;
import pers.zjc.sams.app.AppConfig;
import pers.zjc.sams.app.SamsApplication;
import pers.zjc.sams.common.Const;
import pers.zjc.sams.common.ScmpUtils;
import pers.zjc.sams.module.login.DaggerLoginComponent;
import pers.zjc.sams.module.login.LoginModule;
import pers.zjc.sams.module.login.contract.LoginContract;
import pers.zjc.sams.module.login.presenter.LoginPresenter;
import pers.zjc.sams.module.main.MainActivity;
import pers.zjc.sams.module.register.view.RegisterFragment;

public class LoginFragment extends BaseFragment implements LoginContract.View, View.OnClickListener {


    @BindView(R.id.et_account)
    EditText mEtAccount;
    @BindView(R.id.et_userpwd)
    EditText mEtUserPwd;
    @BindView(R.id.remember_pwd)
    CheckBox mCbxRMBPwd;
    @BindView(R.id.tv_forget_pwd)
    TextView mTvForgetPwd;
    @BindView(R.id.bt_login)
    Button mBtLogin;
//    @BindView(R.id.offline_login)
//    Button mBtOfflineLogin;
    @BindView(R.id.tv_to_register)
    TextView tvToRegister;
    @BindView(R.id.img_clear)
    ImageView ivClear;
    @BindView(R.id.img_switch_mode)
    ImageView ivSwithMode;

    @Inject
    LoginPresenter mPresenter;
    @Inject
    AppConfig appConfig;

    private Unbinder unbinder;

    private String account;
    private String pwd;
    private boolean isRemember;
    private boolean isLogout;
    private boolean showPwd;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerLoginComponent.builder().appComponent(SamsApplication.getComponent())
                                      .loginModule(new LoginModule(this))
                                      .build()
                                      .inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        unbinder = ButterKnife.bind(this, getView());
        initView();
        mPresenter.init(isLogout);
    }

    private void initView() {
        isRemember = appConfig.isRemember();
        mEtAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                account = mEtAccount.getText().toString();
                if (!StringUtils.isEmpty(account)) {
                    ivClear.setVisibility(View.VISIBLE);
                } else {
                    ivClear.setVisibility(View.GONE);
                }
            }
        });
        mEtUserPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                pwd = mEtUserPwd.getText().toString();
            }
        });
        mBtLogin.setOnClickListener(this);
        mCbxRMBPwd.setChecked(isRemember);
        if (mCbxRMBPwd.isChecked()) {
            mEtAccount.setText(appConfig.getAccount());
            mEtUserPwd.setText(appConfig.getPassWord());
        }
        mCbxRMBPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (compoundButton.isPressed()) {
                    Log.d("onCheckedChanged", String.valueOf(isChecked));
                    isRemember = isChecked;
                }
            }
        });
        mTvForgetPwd.setOnClickListener(this);
        tvToRegister.setOnClickListener(this);
        ivClear.setOnClickListener(this);
        ivSwithMode.setOnClickListener(this);
        hideKeyboardWhenLostFocus(mEtAccount);
        hideKeyboardWhenLostFocus(mEtUserPwd);
    }

    @Override
    public void showProgress() {
        showProgressDialogInternal(getResources().getString(R.string.txt_logging_in));
    }

    @Override
    public void closeProgress() {
        closeProgressDialogInternal();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                if (StringUtils.isEmpty(account)) {
                    showShortToast(getResources().getString(R.string.toast_username_can_not_be_empty));
                    return;
                }
                if (StringUtils.isEmpty(pwd)) {
                    showShortToast(getResources().getString(R.string.toast_password_can_not_be_empty));
                    return;
                }
                mPresenter.login(account, pwd, isRemember);
                break;
            case R.id.tv_forget_pwd:
                showShortToast("请联系管理员重置密码");
                break;
            case R.id.tv_to_register:
                ScmpUtils.startWindow(getContext(), RegisterFragment.class.getName());
                break;
            case R.id.img_clear:
                ivClear.setVisibility(View.GONE);
                mEtAccount.setText("");
                break;
            case R.id.img_switch_mode:
                if (!showPwd) {
                    showPwd = true;
                    mEtUserPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ivSwithMode.setImageDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.icon_public_pwd_show));
                } else {
                    showPwd = false;
                    mEtUserPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ivSwithMode.setImageDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.icon_public_pwd_hide));
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void showMessage(String msg) {
        showShortToast(msg);
    }

    @Override
    public void swithToMainFragment(String role) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isAdded()) {
                    MainActivity activity;
                    if (null != (activity = (MainActivity)getActivity())) {
                        activity.switchToMainFragment(role);
                    }
                }
            }
        });

    }

    @Override
    public void showNetWorkErroMessage() {
        showShortToast(getResources().getString(R.string.toast_fail_to_connect_server));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        if (mContext != null && logoutReceiver != null) {
//            mContext.unregisterReceiver(logoutReceiver);
//        }
//    }

}
