package pers.zjc.sams.module.leave.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import pers.zjc.sams.data.entity.Leave;
import pers.zjc.sams.module.leave.DaggerLeaveStatComponent;
import pers.zjc.sams.module.leave.LeaveStatModule;
import pers.zjc.sams.module.leave.contract.LeaveStatContract;
import pers.zjc.sams.module.leave.presenter.LeaveStatPresenter;
import pers.zjc.sams.widget.swipyrefreshlayout.SwipyRefreshLayout;
import pers.zjc.sams.widget.swipyrefreshlayout.SwipyRefreshLayoutDirection;

public class LeaveStatFragment extends BaseFragment implements LeaveStatContract.View, View.OnClickListener, SwipyRefreshLayout.OnRefreshListener {

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
    @BindView(R.id.tv_checking)
    TextView tvChecking;
    @BindView(R.id.txt_checking)
    TextView txtChecking;
    @BindView(R.id.tv_revoke)
    TextView tvRevoke;
    @BindView(R.id.txt_revoke)
    TextView txtRevoke;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.tv_pass)
    TextView tvPass;
    @BindView(R.id.txt_pass)
    TextView txtPass;
    @BindView(R.id.tv_refuse)
    TextView tvRefuse;
    @BindView(R.id.txt_refuse)
    TextView txtRefuse;
    @BindView(R.id.rl_bottom)
    RelativeLayout rlBottom;
    Unbinder unbinder;
    private LeaveStatAdapter adapter;
    @Inject
    LeaveStatPresenter presenter;
    @Inject
    AppConfig appConfig;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_leave_stat;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerLeaveStatComponent.builder().appComponent(SamsApplication.getComponent())
                .leaveStatModule(new LeaveStatModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        unbinder = ButterKnife.bind(this, getView());
        initView();
        presenter.loadData();
    }

    private void initView() {
        setListener();
        adapter = new LeaveStatAdapter(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(adapter);
    }

    private void setListener() {
        btnBack.setOnClickListener(this);
        mRefeshLayout.setOnRefreshListener(this);
        tvChecking.setOnClickListener(this);
        tvRevoke.setOnClickListener(this);
        tvPass.setOnClickListener(this);
        tvRefuse.setOnClickListener(this);

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
            case R.id.tv_checking:
                adapter.filter(0);
                break;
            case R.id.tv_revoke:
                adapter.filter(1);
                break;
            case R.id.tv_pass:
                adapter.filter(2);
                break;
            case R.id.tv_refuse:
                adapter.filter(3);
                break;
            default:
                break;
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
    public void showMessage(String message) {
        showShortToast(message);
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
    public void setData(List<Leave> records) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.setAllData(records);
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
                presenter.loadData();
            }
        });
    }

    @Override
    public void showStats(int checkingNum, int revokeNum, int passNumb, int refusedNumb) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvChecking.setText(String.valueOf(checkingNum));
                tvRevoke.setText(String.valueOf(revokeNum));
                tvPass.setText(String.valueOf(passNumb));
                tvRefuse.setText(String.valueOf(refusedNumb));
            }
        });
    }



}
