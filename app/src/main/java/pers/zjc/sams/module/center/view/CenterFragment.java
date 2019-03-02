package pers.zjc.sams.module.center.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zp.android.zlib.base.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pers.zjc.sams.R;
import pers.zjc.sams.app.SamsApplication;
import pers.zjc.sams.data.entity.FunctionInfo;
import pers.zjc.sams.module.center.CenterModule;
import pers.zjc.sams.module.center.DaggerCenterComponent;
import pers.zjc.sams.module.center.contract.CenterContract;
import pers.zjc.sams.module.center.presenter.CenterPresenter;

public class CenterFragment extends BaseFragment implements CenterContract.View, CenterAdapter.OnItemClickListener {

    @Inject
    CenterPresenter presenter;

    @BindView(R.id.mRecyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private CenterAdapter adapter;
    private Unbinder unbinder;


    private static final String TAG = CenterFragment.class.getSimpleName();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_center;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerCenterComponent.builder().appComponent(SamsApplication.getComponent())
                .centerModule(new CenterModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        unbinder = ButterKnife.bind(this, getView());
        initView();
        Bundle args = getArguments();
        String type = args.getString("type", "0");
        switch (type) {
            case "1":
                tvTitle.setText("应用中心");
                presenter.loadCenterData();
                break;
            case "2":
                tvTitle.setText("统计分析");
                presenter.loadSatisticsData();
                break;
            default:
                break;
        }
    }


    private void initView() {
        LinearLayoutManager manager = new GridLayoutManager(this.getActivity(), 3);
        adapter = new CenterAdapter(this.getContext(), this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showData(List<FunctionInfo> functions) {
        adapter.addAll(functions);
    }

    @Override
    public void onItemClick(View view, FunctionInfo data) {
        FragmentManager fm = getFragmentManager();
        if (fm != null) {
            FragmentTransaction trans = fm.beginTransaction();
            trans.add(R.id.fl_container,
                    Fragment.instantiate(getContext(), data.getClazz()));
            trans.addToBackStack(data.getClazz());
            trans.commitAllowingStateLoss();
        }
    }
}
