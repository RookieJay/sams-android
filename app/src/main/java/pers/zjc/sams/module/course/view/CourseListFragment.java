package pers.zjc.sams.module.course.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
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
import pers.zjc.sams.data.entity.Course;
import pers.zjc.sams.module.course.CourseListModule;
import pers.zjc.sams.module.course.DaggerCourseListComponent;
import pers.zjc.sams.module.course.contract.CourseListContract;
import pers.zjc.sams.module.course.presenter.CourseListPresenter;
import pers.zjc.sams.widget.swipyrefreshlayout.SwipyRefreshLayout;
import pers.zjc.sams.widget.swipyrefreshlayout.SwipyRefreshLayoutDirection;

public class CourseListFragment extends BaseFragment implements CourseListContract.View, View.OnClickListener, SwipyRefreshLayout.OnRefreshListener {

    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.iv_empty)
    ImageView ivEmpty;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    @BindView(R.id.mRefeshLayout)
    SwipyRefreshLayout mRefeshLayout;

    Unbinder unbinder;

    private CourseListAdapter adapter;

    @Inject
    CourseListPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_course_list;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerCourseListComponent.builder().appComponent(SamsApplication.getComponent())
                                 .courseListModule(new CourseListModule(this))
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
        barTitle.setText("课程列表");
        mRefeshLayout.setOnRefreshListener(this);
        btnBack.setOnClickListener(this);
        adapter = new CourseListAdapter(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showEmpty() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (adapter.getData()!=null && adapter.getData().size() > 0) {
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
    public void loadData(List<Course> courses) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.replaceAll(courses);
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
            }
        });
        showShortToast(getResources().getString(R.string.toast_fail_to_connect_server));
    }

    @Override
    public void showMessage(String msg) {
        showShortToast(msg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                back();
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
}
