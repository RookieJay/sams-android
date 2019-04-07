package pers.zjc.sams.module.devicemanage.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zp.android.zlib.base.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pers.zjc.sams.R;
import pers.zjc.sams.app.SamsApplication;
import pers.zjc.sams.data.entity.Device;
import pers.zjc.sams.module.devicemanage.DaggerDeviceManageComponent;
import pers.zjc.sams.module.devicemanage.DeviceManageModule;
import pers.zjc.sams.module.devicemanage.contract.DeviceManageContract;
import pers.zjc.sams.module.devicemanage.presenter.DeviceManagePresenter;
import pers.zjc.sams.widget.swipyrefreshlayout.SwipyRefreshLayout;
import pers.zjc.sams.widget.swipyrefreshlayout.SwipyRefreshLayoutDirection;

public class DeviceManageFragment extends BaseFragment implements DeviceManageContract.View, View.OnClickListener, SwipyRefreshLayout.OnRefreshListener {

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
        mRefeshLayout.setOnRefreshListener(this);
        btnBack.setOnClickListener(this);
        adapter = new DeviceManageAdapter(getContext(), presenter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                back();
                break;
            default:
                break;
        }
    }

    private void back() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStackImmediate();
                }
            }
        });
    }


    @Override
    public void setData(List<Device> data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.replaceAll(data);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void startRefresh() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mRefeshLayout.setRefreshing(true);
            }
        });

    }

    @Override
    public void showEmpty() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvEmpty.setVisibility(View.VISIBLE);
                ivEmpty.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void hideEmpty() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvEmpty.setVisibility(View.GONE);
                ivEmpty.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void showMessage(String msg) {
        showShortToast(msg);
    }

    @Override
    public void finishRefresh() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mRefeshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void showNetworkErro() {
        showShortToast(getResources().getString(R.string.toast_fail_to_connect_server));
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        presenter.load();
    }

    @Override
    public void notifyDataChanged(int vPosition, boolean isCancel) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (adapter.getData() != null && adapter.getData().size() > 0) {
                    adapter.getData().get(vPosition).setDeviceStatus(isCancel ? 1 : 0);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
