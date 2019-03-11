package pers.zjc.sams.module.login.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.zp.android.zlib.base.BaseFragment;
import com.zp.android.zlib.utils.KeyboardUtils;
import com.zp.android.zlib.utils.StringUtils;
import com.zp.android.zlib.utils.ToastUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pers.zjc.sams.R;
import pers.zjc.sams.app.AppConfig;
import pers.zjc.sams.app.SamsApplication;
import pers.zjc.sams.common.ScmpUtils;
import pers.zjc.sams.data.entity.AttenceRecord;
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

    @Inject
    LoginPresenter mPresenter;
    @Inject
    AppConfig appConfig;

    private Unbinder unbinder;

    private String account;
    private String pwd;
    private boolean isRemember;

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
                KeyboardUtils.hideSoftInput(getView());
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
            default:
                break;
        }
    }

    @Override
    public void showMessage(String msg) {
        showShortToast(msg);
    }

    @Override
    public void swithToMainFragment(List<AttenceRecord> records, String role) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isAdded()) {
                    MainActivity activity;
                    if (null != (activity = (MainActivity)getActivity())) {
                        activity.switchToMainFragment(records, role);
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
}
