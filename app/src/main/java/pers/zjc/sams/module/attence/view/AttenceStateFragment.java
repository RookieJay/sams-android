package pers.zjc.sams.module.attence.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import pers.zjc.sams.app.SamsApplication;
import pers.zjc.sams.data.entity.AttenceRecord;
import pers.zjc.sams.module.attence.AttenceStatModule;
import pers.zjc.sams.module.attence.DaggerAttenceStatComponent;
import pers.zjc.sams.module.attence.contract.AttenceStatContract;
import pers.zjc.sams.module.attence.presenter.AttenceStatPresenter;
import pers.zjc.sams.module.myattence.view.MyAttenceAdapter;
import pers.zjc.sams.widget.swipyrefreshlayout.SwipyRefreshLayout;
import pers.zjc.sams.widget.swipyrefreshlayout.SwipyRefreshLayoutDirection;

public class AttenceStateFragment extends BaseFragment implements AttenceStatContract.View, View.OnClickListener, SwipyRefreshLayout.OnRefreshListener {

    @Inject
    AttenceStatPresenter presenter;
    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.bar_right)
    TextView barRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_late)
    TextView tvLate;
    @BindView(R.id.txt_late)
    TextView txtLate;
    @BindView(R.id.tv_normal)
    TextView tvNormal;
    @BindView(R.id.txt_normal)
    TextView txtNormal;
    @BindView(R.id.tv_leaving)
    TextView tvLeaving;
    @BindView(R.id.txt_leaving)
    TextView txtLeaving;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.tv_absent)
    TextView tvAbsent;
    @BindView(R.id.txt_absent)
    TextView txtAbsent;
    @BindView(R.id.tv_earlier_leave)
    TextView tvEarlierLeave;
    @BindView(R.id.txt_earlier_leave)
    TextView txtEarlierLeave;
    @BindView(R.id.rl_bottom)
    RelativeLayout rlBottom;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mRefeshLayout)
    SwipyRefreshLayout mRefeshLayout;
    @BindView(R.id.iv_empty)
    ImageView ivEmpty;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    Unbinder unbinder;

    private MyAttenceAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerAttenceStatComponent.builder()
                                  .appComponent(SamsApplication.getComponent())
                                  .attenceStatModule(new AttenceStatModule(this))
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
        tvLate.setOnClickListener(this);
        tvAbsent.setOnClickListener(this);
        tvEarlierLeave.setOnClickListener(this);
        tvLeaving.setOnClickListener(this);
        tvNormal.setOnClickListener(this);
        adapter = new MyAttenceAdapter(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_attence_stat;
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
            case R.id.tv_normal:
                adapter.filter(0);
                break;
            case R.id.tv_late:
                adapter.filter(1);
                break;
            case R.id.tv_leaving:
                adapter.filter(2);
                break;
            case R.id.tv_absent:
                adapter.filter(3);
                break;
            case R.id.tv_earlier_leave:
                adapter.filter(4);
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
    public void setData(List<AttenceRecord> records) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.replaceAll(records);
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

    @Override
    public void showEmpty() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (adapter.getData() != null && adapter.getData().size() > 0) {
                    adapter.clear();
                    adapter.notifyDataSetChanged();
                }
                tvEmpty.setVisibility(View.VISIBLE);
                ivEmpty.setVisibility(View.VISIBLE);
                showShortToast(getResources().getString(R.string.txt_empty));
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
    public void setStats(int normal, int late, int leaving, int absent, int earlierLeave) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvNormal.setText(String.valueOf(normal));
//                tvNormal.setTextColor(ContextCompat.getColor(getContext(), R.color.normal_attence));
                tvLate.setText(String.valueOf(late));
//                tvLate.setTextColor(ContextCompat.getColor(getContext(), R.color.late));
                tvLeaving.setText(String.valueOf(leaving));
//                tvLeaving.setTextColor(ContextCompat.getColor(getContext(), R.color.leaving));
                tvAbsent.setText(String.valueOf(absent));
//                tvAbsent.setTextColor(ContextCompat.getColor(getContext(), R.color.absent));
                tvEarlierLeave.setText(String.valueOf(earlierLeave));
//                tvEarlierLeave.setTextColor(ContextCompat.getColor(getContext(), R.color.earlier_leave));

            }
        });
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        presenter.load();
    }
}
