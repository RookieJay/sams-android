package pers.zjc.sams.module.course.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import pers.zjc.sams.common.ScmpUtils;
import pers.zjc.sams.data.entity.Course;
import pers.zjc.sams.module.course.CourseListModule;
import pers.zjc.sams.module.course.DaggerCourseListComponent;
import pers.zjc.sams.module.course.contract.CourseListContract;
import pers.zjc.sams.module.course.presenter.CourseListPresenter;
import pers.zjc.sams.widget.swipyrefreshlayout.SwipyRefreshLayout;
import pers.zjc.sams.widget.swipyrefreshlayout.SwipyRefreshLayoutDirection;

public class CourseListFragment extends BaseFragment implements CourseListContract.View, View.OnClickListener, SwipyRefreshLayout.OnRefreshListener, CourseListAdapter.OnItemLongCLickListener, PopupMenu.OnMenuItemClickListener {

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
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    Unbinder unbinder;

    private CourseListAdapter adapter;

    @Inject
    CourseListPresenter presenter;
    private Course mCourse;

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
        ivAdd.setOnClickListener(this);
        adapter = new CourseListAdapter(getContext(), this);
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
            case R.id.iv_add:

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
    public void onItemLongClick(View view, Course data, int position) {
        mCourse = data;
        //创建弹出式菜单对象（最低版本11）
        PopupMenu popup = new PopupMenu(getContext(), view);//第二个参数是绑定的那个view
        //获取菜单填充器
        MenuInflater inflater = popup.getMenuInflater();
        //填充菜单
        inflater.inflate(R.menu.menu_course_manage, popup.getMenu());
        //绑定菜单项的点击事件
        popup.setOnMenuItemClickListener(this);
        //显示(这一行代码不要忘记了)
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_edit:
                switchToCourseEditFrag(mCourse);
                break;
            case R.id.menu_delete:

                presenter.deleteCourse(mCourse);

            default:
                break;
        }
        return false;
    }

    private void switchToCourseEditFrag(Course course) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("course", course);
        ScmpUtils.startWindow(getContext(), CourseEditFragment.class.getName(), bundle);
    }

    @Override
    public void update(Course course) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showShortToast("删除成功");
                adapter.remove(course);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
