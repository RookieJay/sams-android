package pers.zjc.sams.module.sign.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import pers.zjc.sams.app.AppConfig;
import pers.zjc.sams.app.SamsApplication;
import pers.zjc.sams.data.entity.SignRecord;
import pers.zjc.sams.module.sign.DaggerSignListComponent;
import pers.zjc.sams.module.sign.DaggerSignStatomponent;
import pers.zjc.sams.module.sign.SignListModule;
import pers.zjc.sams.module.sign.SignStatModule;
import pers.zjc.sams.module.sign.contract.SignStatContract;
import pers.zjc.sams.module.sign.presenter.SignStatPresenter;
import pers.zjc.sams.widget.swipyrefreshlayout.SwipyRefreshLayout;
import pers.zjc.sams.widget.swipyrefreshlayout.SwipyRefreshLayoutDirection;

public class SignStatFragment extends BaseFragment
        implements SignStatContract.View, View.OnClickListener, SwipyRefreshLayout.OnRefreshListener {

    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.bar_right)
    TextView barRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_signed)
    TextView tvSigned;
    @BindView(R.id.txt_signed)
    TextView txtSigned;
    @BindView(R.id.tv_unsigned)
    TextView tvUnsigned;
    @BindView(R.id.txt_unsigned)
    TextView txtUnsigned;
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
    private SignLitAdapter adapter;
    @Inject
    SignStatPresenter presenter;
    @Inject
    AppConfig appConfig;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sign_stat;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerSignStatomponent.builder()
                              .appComponent(SamsApplication.getComponent())
                              .signStatModule(new SignStatModule(this))
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
        adapter = new SignLitAdapter(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(adapter);
        mRefeshLayout.setOnRefreshListener(this);
        btnBack.setOnClickListener(this);
        tvSigned.setOnClickListener(this);
        tvUnsigned.setOnClickListener(this);

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
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        presenter.load();
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
                if (mRefeshLayout.isRefreshing()) {
                    finishRefresh();
                }
                showShortToast(getResources().getString(R.string.toast_fail_to_connect_server));
            }
        });

    }

    @Override
    public void showMessage(String msg) {
        showShortToast(msg);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                back();
                break;
            case R.id.tv_signed:
                adapter.filter(1);
                break;
            case R.id.tv_unsigned:
                adapter.filter(0);
                break;
            default:
                break;
        }
    }

    private void back() {
        if (getFragmentManager() != null) {
            getFragmentManager().popBackStackImmediate();
        }
    }

    @Override
    public void showStats(int numSigned, int numUnsigned) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvSigned.setText(String.valueOf(numSigned));
                tvUnsigned.setText(String.valueOf(numUnsigned));
            }
        });
    }


}
