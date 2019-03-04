package pers.zjc.sams.module.personcenter.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zp.android.zlib.base.BaseFragment;
import com.zp.android.zlib.utils.StringUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pers.zjc.sams.R;
import pers.zjc.sams.app.SamsApplication;
import pers.zjc.sams.module.personcenter.DaggerModifyPwdComponent;
import pers.zjc.sams.module.personcenter.ModifyPwdModule;
import pers.zjc.sams.module.personcenter.contract.ModifyPwdContract;
import pers.zjc.sams.module.personcenter.presenter.ModifyPwdPresenter;

public class ModifyPwdFragment extends BaseFragment implements ModifyPwdContract.View, View.OnClickListener {


    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.bar_right)
    TextView barRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.etOldPwd)
    EditText etOldPwd;
    @BindView(R.id.etNewPwd)
    EditText etNewPwd;
    @BindView(R.id.etConfirmNewPwd)
    EditText etConfirmNewPwd;
    Unbinder unbinder;

    @Inject
    ModifyPwdPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_modify_pwd;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerModifyPwdComponent.builder().appComponent(SamsApplication.getComponent())
                                .modifyPwdModule(new ModifyPwdModule(this))
                                .build()
                                .inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        initView();
    }

    private void initView() {
        barTitle.setText("修改密码");
        barRight.setText("提交");
        barRight.setOnClickListener(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (unbinder != null) {
                    Log.d("onDestroyView: ", "binder is not null");
                    unbinder.unbind();
                }
                if (getActivity() != null) {
                    getActivity().finish();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            Log.d("onDestroyView: ", "binder is not null");
            unbinder.unbind();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bar_right:
                String oldPwd = etOldPwd.getText().toString();
                String newPwd = etNewPwd.getText().toString();
                String confirmNewPwd = etConfirmNewPwd.getText().toString();
                if (StringUtils.isEmpty(oldPwd)) {
                    showShortToast("请输入旧密码");
                    return;
                }
                if (StringUtils.isEmpty(newPwd)) {
                    showShortToast("请输入新密码");
                    return;
                }
                if (StringUtils.isEmpty(confirmNewPwd)) {
                    showShortToast("请再次输入新密码");
                }
                if (!StringUtils.equals(newPwd, confirmNewPwd)) {
                    showShortToast("两次输入新密码不一致");
                    return;
                }
                presenter.commit(oldPwd, newPwd);
                break;
            case R.id.btn_back:
                back();
                break;
            default:
                break;
        }
    }

    @Override
    public void showMessage(String message) {
        showShortToast(message);
    }

    @Override
    public void showNetWorkErro() {
        showShortToast(getResources().getString(R.string.toast_fail_to_connect_server));
    }

    @Override
    public void back() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }
}
