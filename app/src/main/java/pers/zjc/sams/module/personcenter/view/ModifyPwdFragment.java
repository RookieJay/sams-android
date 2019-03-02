package pers.zjc.sams.module.personcenter.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zp.android.zlib.base.BaseFragment;
import com.zp.android.zlib.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pers.zjc.sams.R;

public class ModifyPwdFragment extends BaseFragment implements View.OnClickListener {


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

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_modify_pwd;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        initView();
    }

    private void initView() {
        barTitle.setText("修改密码");
        barRight.setOnClickListener(this);
        toolbar.setNavigationOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
                if (StringUtils.isEmpty(oldPwd)) {
                    showShortToast("请输入新密码");
                    return;
                }
                if (StringUtils.isEmpty(confirmNewPwd)) {
                    showShortToast("请再次输入新密码");
                }
                if (!StringUtils.equals(oldPwd, newPwd)) {
                    showShortToast("两次输入新密码不一致");
                    return;
                }
                break;
            case R.id.toolbar:
                showShortToast("点击返回");
                if (getActivity() != null) {
                    getActivity().finish();
                }
                break;
            default:
                break;
        }
    }
}
