package pers.zjc.sams.module.user.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
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
import pers.zjc.sams.app.SamsApplication;
import pers.zjc.sams.data.entity.Student;
import pers.zjc.sams.data.entity.Teacher;
import pers.zjc.sams.module.user.DaggerUserManageComponent;
import pers.zjc.sams.module.user.UserManageModule;
import pers.zjc.sams.module.user.contract.UserManageContract;
import pers.zjc.sams.module.user.presenter.UserManagePresenter;
import pers.zjc.sams.widget.swipyrefreshlayout.SwipyRefreshLayout;
import pers.zjc.sams.widget.swipyrefreshlayout.SwipyRefreshLayoutDirection;

public class UserManageFrament extends BaseFragment implements UserManageContract.View, View.OnClickListener, SwipyRefreshLayout.OnRefreshListener {

    @Inject
    UserManagePresenter presenter;

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


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_manage;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerUserManageComponent.builder()
                                 .appComponent(SamsApplication.getComponent())
                                 .userManageModule(new UserManageModule(this))
                                 .build()
                                 .inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        unbinder = ButterKnife.bind(this, getView());
        initView();
        setListener();
        presenter.init();
    }

    private void initView() {
        rbStudent.setChecked(true);
        btnBack.setOnClickListener(this);
        mRefeshLayout.setOnRefreshListener(this);
        studentListAdapter = new StudentListAdapter(getContext(), presenter);
        teacherListAdapter = new TeacherListAdapter(getContext(), presenter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    private void setListener() {
        rbStudent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed() && isChecked) {
                    presenter.load(true);
                }
            }
        });
        rbTeacher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed() && isChecked) {
                    presenter.load(false);
                }
            }
        });
    }


    @Override
    public void showEmpty(boolean isStu) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isStu && studentListAdapter.getAll().size() > 0) {
                    studentListAdapter.clear();
                    studentListAdapter.notifyDataSetChanged();
                    tvEmpty.setVisibility(View.VISIBLE);
                    ivEmpty.setVisibility(View.VISIBLE);
                    showShortToast(getResources().getString(R.string.txt_empty));
                } else if (teacherListAdapter.getAll().size() > 0) {
                    teacherListAdapter.clear();
                    teacherListAdapter.notifyDataSetChanged();
                    tvEmpty.setVisibility(View.VISIBLE);
                    ivEmpty.setVisibility(View.VISIBLE);
                    showShortToast(getResources().getString(R.string.txt_empty));
                }
                else {
                    showShortToast("发生未知错误，请重新打开页面");
                }

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
    public void setStuData(List<Student> records) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.setAdapter(studentListAdapter);
                studentListAdapter.replaceAll(records);
                studentListAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void setTeaData(List<Teacher> records) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.setAdapter(teacherListAdapter);
                teacherListAdapter.replaceAll(records);
                teacherListAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        if (rbStudent.isChecked()) {
            presenter.load(true);
        }
        if (rbTeacher.isChecked()) {
            presenter.load(false);
        }

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
        switch (v.getId()) {
            case R.id.rb_student:
                presenter.load(true);
                break;
            case R.id.rb_teacher:
                presenter.load(false);
                break;
            case R.id.btn_back:
                back();
            default:
                break;
        }
    }

    private void back() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                FragmentManager fm = getFragmentManager();
                if (null != fm) {
                    fm.popBackStackImmediate();
                }
            }
        });
    }

    @Override
    public void notifyStuDataChanged(int position, boolean isCancel) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (null != studentListAdapter) {
                    if (studentListAdapter.getData().size() == 0) {
                        return;
                    }
                    if (isCancel) {
                        studentListAdapter.getData().get(position).setStatus(1);
                    } else {
                        studentListAdapter.getData().get(position).setStatus(0);
                    }

                    studentListAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void notifyTeaDataChanged(int position) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (null != teacherListAdapter) {
                    if (teacherListAdapter.getData().size() == 0) {
                        return;
                    }
                    teacherListAdapter.getData().remove(teacherListAdapter.getData().get(position));
                    teacherListAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
