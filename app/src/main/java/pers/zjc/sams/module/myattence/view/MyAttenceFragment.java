package pers.zjc.sams.module.myattence.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zp.android.zlib.base.BaseFragment;
import com.zp.android.zlib.http.Param;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pers.zjc.sams.R;
import pers.zjc.sams.app.AppConfig;
import pers.zjc.sams.app.SamsApplication;
import pers.zjc.sams.common.Const;
import pers.zjc.sams.common.EventBusUtil;
import pers.zjc.sams.data.entity.AttenceRecord;
import pers.zjc.sams.module.leave.view.LeaveListAdapter;
import pers.zjc.sams.module.myattence.DaggerMyAttenceComponent;
import pers.zjc.sams.module.myattence.MyAttenceModule;
import pers.zjc.sams.module.myattence.contract.MyAttenceContract;
import pers.zjc.sams.module.myattence.presenter.MyAttencePresenter;
import pers.zjc.sams.widget.swipyrefreshlayout.SwipyRefreshLayout;
import pers.zjc.sams.widget.swipyrefreshlayout.SwipyRefreshLayoutDirection;

public class MyAttenceFragment extends BaseFragment implements MyAttenceContract.View, View.OnClickListener, SwipyRefreshLayout.OnRefreshListener {

    @Inject
    MyAttencePresenter presenter;
    @Inject
    AppConfig appConfig;

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.mRefeshLayout)
    SwipyRefreshLayout mRefeshLayout;
    @BindView(R.id.iv_empty)
    ImageView ivEmpty;

    private Unbinder unbinder;

    private MyAttenceAdapter attenceAdapter;
    private LeaveListAdapter leaveListAdapter;
    List<AttenceRecord> records = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_myattence;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerMyAttenceComponent.builder().appComponent(SamsApplication.getComponent())
                                          .myAttenceModule(new MyAttenceModule(this))
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
        attenceAdapter = new MyAttenceAdapter(getContext(), new ArrayList<>());
        leaveListAdapter = new LeaveListAdapter(getContext(), new ArrayList<>());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        switch (appConfig.getRole()) {
            case "0":
                barTitle.setText("我的职责");
                break;
            case "1":
                barTitle.setText("我的考勤");
                mRecyclerView.setAdapter(attenceAdapter);
                loadData();
                break;
            case "2":
                barTitle.setText("我的审批");
                mRecyclerView.setAdapter(leaveListAdapter);
                loadData();
                break;
            default:
                break;
        }
        mRefeshLayout.setOnRefreshListener(this);
    }

    private void loadData() {
//        Log.d("loadData", "loadData");
//        if (getArguments() != null) {
//            Log.d("getArguments", "getArguments");
//            records = getArguments().getParcelableArrayList(Const.Keys.KEY_ATTENCE_RECORDS);
//        }
//        attenceAdapter.addAll(records);
//        if (records == null || records.size() == 0) {
//            presenter.load();
//        }
        presenter.load();
    }

    @Override
    public void showEmpty() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (attenceAdapter.getData()!=null && attenceAdapter.getData().size() > 0) {
                    attenceAdapter.clear();
                    attenceAdapter.notifyDataSetChanged();
                }
                tvEmpty.setVisibility(View.VISIBLE);
                ivEmpty.setVisibility(View.VISIBLE);
//                showShortToast(getResources().getString(R.string.txt_empty));
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
    public void onClick(View view) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
//        EventBusUtil.unregister(this);
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        presenter.load();
    }

    @Override
    public void resetData(List<AttenceRecord> records) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                attenceAdapter.replaceAll(records);
                attenceAdapter.notifyDataSetChanged();

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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mRefeshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void showMessage(String msg) {
        showShortToast(msg);
    }
}
