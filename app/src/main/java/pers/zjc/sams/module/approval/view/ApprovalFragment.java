package pers.zjc.sams.module.approval.view;

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
import pers.zjc.sams.app.AppConfig;
import pers.zjc.sams.app.SamsApplication;
import pers.zjc.sams.data.entity.SignRecord;
import pers.zjc.sams.module.approval.ApprovalModule;
import pers.zjc.sams.module.approval.DaggerApprovalComponent;
import pers.zjc.sams.module.approval.contract.ApprovalContract;
import pers.zjc.sams.module.approval.presenter.ApprovalPresenter;
import pers.zjc.sams.module.sign.view.SignLitAdapter;
import pers.zjc.sams.widget.swipyrefreshlayout.SwipyRefreshLayout;
import pers.zjc.sams.widget.swipyrefreshlayout.SwipyRefreshLayoutDirection;

public class ApprovalFragment extends BaseFragment implements View.OnClickListener, ApprovalContract.View, SwipyRefreshLayout.OnRefreshListener, SignLitAdapter.OnChangeSignStatusListener {

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
    private SignLitAdapter adapter;

    @Inject
    ApprovalPresenter presenter;
    @Inject
    AppConfig appConfig;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_approval;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerApprovalComponent.builder().appComponent(SamsApplication.getComponent())
                .approvalModule(new ApprovalModule(this))
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
        mRefeshLayout.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        adapter = new SignLitAdapter(getContext());
        adapter.setOnChangeSignStatusListener(this);
        adapter.setRole(appConfig.getRole());
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
    public void showMessage(String message) {
        showShortToast(message);
    }

    @Override
    public void showEmpty() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvEmpty.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void hideEmpty() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvEmpty.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void setData(List<SignRecord> records) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.replaceAll(records);
                adapter.notifyDataSetChanged();
            }
        });

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
    public void startRefresh() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mRefeshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                presenter.load();
            }
        });
    }

    @Override
    public void onChange(int attenceStatus) {
        presenter.attend(attenceStatus);
    }
}
