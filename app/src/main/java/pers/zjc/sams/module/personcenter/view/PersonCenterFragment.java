package pers.zjc.sams.module.personcenter.view;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.zp.android.zlib.base.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pers.zjc.sams.R;
import pers.zjc.sams.app.AppConfig;
import pers.zjc.sams.app.SamsApplication;
import pers.zjc.sams.common.ScmpUtils;
import pers.zjc.sams.module.main.MainActivity;
import pers.zjc.sams.module.personcenter.DaggerPersonCenterComponent;
import pers.zjc.sams.module.personcenter.PersonCenterModule;
import pers.zjc.sams.module.personcenter.contract.PersonCenterContract;
import pers.zjc.sams.module.personcenter.presenter.PersonCenterPresenter;

public class PersonCenterFragment extends BaseFragment implements PersonCenterContract.View, View.OnClickListener {

    @Inject
    AppConfig appConfig;
    @Inject
    PersonCenterPresenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.txt_user)
    TextView tvUser;
    @BindView(R.id.txt_role)
    TextView tvRole;
    @BindView(R.id.txt_major)
    TextView txt_major;
    @BindView(R.id.tvCacheSize)
    TextView tvCacheSize;
    @BindView(R.id.rlModifyPwd)
    RelativeLayout rlModifyPwd;
    @BindView(R.id.btn_exit)
    TextView btExit;

    private Unbinder unbinder;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personcenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerPersonCenterComponent.builder().appComponent(SamsApplication.getComponent())
                                             .personCenterModule(new PersonCenterModule(this))
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

    private void initView() {
        barTitle.setText("个人中心");
        tvCacheSize.setOnClickListener(this);
        tvUser.setText(appConfig.getUserName());
        btExit.setOnClickListener(this);
        rlModifyPwd.setOnClickListener(this);
    }

    private void initData() {

        switch (appConfig.getRole()) {
            case "0":
                tvRole.setText("管理员");
                break;
            case "1":
                tvRole.setText("学生");
                txt_major.setText(appConfig.getMajor());
                break;
            case "2":
                tvRole.setText("教师");
                txt_major.setText(appConfig.getMajor());
                break;
            default:
                break;
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlModifyPwd:
                switchToMidifyFragment();
                break;
            case R.id.btn_exit:
                presenter.exit();
                break;
            default:
                break;
        }
    }

    private void switchToMidifyFragment() {
        ScmpUtils.startWindow(getContext(), ModifyPwdFragment.class.getName());
    }

    @Override
    public void exit() {
        MaterialDialog.Builder builder = ScmpUtils.createDialog(getActivity(),
                getString(R.string.dialog_title),
                getString(R.string.dialog_exit_login), ContextCompat.getColor(getContext(),R.color.c32d6af),
                (dialog, which) -> {
                    if (isAdded()) {
                        MainActivity activity;
                        if (null != (activity = (MainActivity)getActivity())) {
                            activity.switchToLoginFragment();
                        }
                    }
                }, null);
        builder.show();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
