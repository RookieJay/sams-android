package pers.zjc.sams.module.devicemanage.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zp.android.zlib.base.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pers.zjc.sams.R;
import pers.zjc.sams.app.SamsApplication;
import pers.zjc.sams.module.devicemanage.DaggerDeviceManageComponent;
import pers.zjc.sams.module.devicemanage.DeviceManageModule;
import pers.zjc.sams.module.devicemanage.contract.DeviceManageContract;
import pers.zjc.sams.module.devicemanage.presenter.DeviceManagePresenter;
import pers.zjc.sams.widget.swipyrefreshlayout.SwipyRefreshLayout;

public class DeviceManageFragment extends BaseFragment implements DeviceManageContract.View, View.OnClickListener {

    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.bar_right)
    TextView barRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mRefeshLayout)
    SwipyRefreshLayout mRefeshLayout;
    @BindView(R.id.iv_empty)
    ImageView ivEmpty;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    Unbinder unbinder;
    private DeviceManageAdapter adapter;
    @Inject
    DeviceManagePresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_device_manage;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerDeviceManageComponent.builder()
                                   .appComponent(SamsApplication.getComponent())
                                   .deviceManageModule(new DeviceManageModule(this))
                                   .build()
                                   .inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        unbinder = ButterKnife.bind(this, getView());
        initView();
        presenter.load();
    }

    private void initView() {
        btnBack.setOnClickListener(this);
        adapter = new DeviceManageAdapter(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {

    }
}
