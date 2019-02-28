package pers.zjc.sams.module.sign.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import pers.zjc.sams.data.entity.SignRecord;
import pers.zjc.sams.module.sign.DaggerSignListComponent;
import pers.zjc.sams.module.sign.SignListModule;
import pers.zjc.sams.module.sign.contract.SignListContract;
import pers.zjc.sams.module.sign.presenter.SignListPresenter;
import pers.zjc.sams.widget.swipyrefreshlayout.SwipyRefreshLayout;
import pers.zjc.sams.widget.swipyrefreshlayout.SwipyRefreshLayoutDirection;

public class SignListFragment extends BaseFragment implements SignListContract.View, View.OnClickListener, SwipyRefreshLayout.OnRefreshListener {

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
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    Unbinder unbinder;

    @Inject
    SignListPresenter presenter;
    @BindView(R.id.iv_empty)
    ImageView ivEmpty;

    private SignLitAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sign_list;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerSignListComponent.builder().appComponent(SamsApplication.getComponent())
                .signListModule(new SignListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        unbinder = ButterKnife.bind(this, getView());
        initView();
        presenter.loadHistory(0);
    }

    private void initView() {
        adapter = new SignLitAdapter(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(adapter);
        btnBack.setOnClickListener(this);
        mRefeshLayout.setOnRefreshListener(this);
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
        presenter.loadHistory(0);
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
            default:
                break;
        }
    }

    private void back() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }
}
