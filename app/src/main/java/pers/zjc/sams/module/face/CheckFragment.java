package pers.zjc.sams.module.face;

import android.app.Activity;
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
import android.widget.TextView;

import com.zp.android.zlib.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pers.zjc.sams.R;
import pers.zjc.sams.module.main.MainActivity;
import pers.zjc.sams.module.myattence.view.MyAttenceAdapter;
import pers.zjc.sams.widget.swipyrefreshlayout.SwipyRefreshLayout;

public class CheckFragment extends BaseFragment implements View.OnClickListener {

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
    Unbinder unbinder;
    private MainActivity curActivity;

    private MyAttenceAdapter adapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof MainActivity) {
            this.curActivity = (MainActivity) activity;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_check;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        unbinder = ButterKnife.bind(this, getView());
        //暂时不用人脸
//        Intent intent = new Intent(getActivity(), PermissionAcitivity.class);
//        curActivity.startActivity(intent);
        initView();

    }

    private void initView() {
        btnBack.setOnClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        adapter = new MyAttenceAdapter(getContext());
        mRecyclerView.setAdapter(adapter);
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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStackImmediate();
                }
            }
        });
    }
}
