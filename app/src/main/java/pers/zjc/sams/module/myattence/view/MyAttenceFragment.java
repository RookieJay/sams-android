package pers.zjc.sams.module.myattence.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zp.android.zlib.base.BaseFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pers.zjc.sams.R;
import pers.zjc.sams.common.Const;
import pers.zjc.sams.common.EventBusUtil;
import pers.zjc.sams.common.event.Event;
import pers.zjc.sams.data.entity.AttenceRecord;

public class MyAttenceFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    @BindView(R.id.bar_title)
    TextView barTitle;

    private Unbinder unbinder;

    private MyAttenceAdapter attenceAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_myattence;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        unbinder = ButterKnife.bind(this, getView());
        EventBusUtil.register(this);
        initView();
//        loadData();

    }

    private void initView() {
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
//        tvEmpty = (TextView) findViewById(R.id.tv_empty);
        toolbar.setTitle("我的考勤");
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        attenceAdapter = new MyAttenceAdapter(getContext(), new ArrayList<>());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(attenceAdapter);
    }

    private void loadData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            List<AttenceRecord> records = bundle.getParcelableArrayList(Const.Keys.KEY_ATTENCE_RECORDS);
            if (records != null && records.size() > 0) {
                attenceAdapter.addAll(records);
            } else {
                showEmpty();
            }
        }
    }

    private void showEmpty() {
        tvEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadData(List<AttenceRecord> records) {
        if (records != null && records.size() > 0) {
            Log.d("records", records.toString());
            attenceAdapter.addAll(records);
        } else {
            showEmpty();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBusUtil.unregister(this);
    }

}
