package pers.zjc.sams.module.user.view;

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
import android.widget.RadioButton;
import android.widget.TextView;

import com.zp.android.zlib.base.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pers.zjc.sams.R;
import pers.zjc.sams.data.entity.Leave;
import pers.zjc.sams.data.entity.Student;
import pers.zjc.sams.module.leave.view.LeaveListAdapter;
import pers.zjc.sams.module.user.contract.UserManageContract;
import pers.zjc.sams.module.user.presenter.UserManagePresenter;
import pers.zjc.sams.widget.swipyrefreshlayout.SwipyRefreshLayout;
import pers.zjc.sams.widget.swipyrefreshlayout.SwipyRefreshLayoutDirection;

public class UserManageFrament extends BaseFragment implements UserManageContract.View, View.OnClickListener, SwipyRefreshLayout.OnRefreshListener {

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
    @BindView(R.id.rb_student)
    RadioButton rbStudent;
    @BindView(R.id.rb_teacher)
    RadioButton rbTeacher;
    Unbinder unbinder;
    private StudentListAdapter studentListAdapter;
    private TeacherListAdapter teacherListAdapter;
    @Inject
    UserManagePresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_manage;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        unbinder = ButterKnife.bind(this, getView());
        initView();
        presenter.init();
    }

    private void initView() {
        rbStudent.setChecked(true);
        btnBack.setOnClickListener(this);
        mRefeshLayout.setOnRefreshListener(this);

    }


    @Override
    public void showEmpty() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                if (adapter.getData() != null && adapter.getData().size() > 0) {
//                    adapter.clear();
//                    adapter.notifyDataSetChanged();
//                }
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
    public void setData(List<Student> records) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {

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
    public void onClick(View v) {

    }
}
