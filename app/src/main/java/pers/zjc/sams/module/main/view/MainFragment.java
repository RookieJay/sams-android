package pers.zjc.sams.module.main.view;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.zp.android.zlib.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pers.zjc.sams.R;
import pers.zjc.sams.common.Const;
import pers.zjc.sams.data.TabEntity;
import pers.zjc.sams.data.entity.AttenceRecord;
import pers.zjc.sams.module.main.contract.MainContract;
import pers.zjc.sams.module.myattence.view.MyAttenceFragment;
import pers.zjc.sams.module.personcenter.view.PersonCenterFragment;

@SuppressWarnings({ "ConstantConditions", "FieldCanBeLocal" })
public class MainFragment extends BaseFragment implements MainContract.View {

    @Inject
    MainContract.Presenter presenter;
    private CommonTabLayout mTabLayout;
    private String[] mTitles = { "我的考勤", "应用中心", "统计分析", "个人中心" };
    private int[] mIconUnselectIds = { R.mipmap.tab_mw_normal, R.mipmap.tab_ws_normal, R.mipmap.tab_stat_normal, R.mipmap.tab_pc_normal };
    private int[] mIconSelectIds = { R.mipmap.tab_mw_pressed, R.mipmap.tab_ws_pressed, R.mipmap.tab_stat_pressed, R.mipmap.tab_pc_pressed };
    //tab的标题、选中图标、未选中图标
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTabLayout = (CommonTabLayout)findViewById( R.id.ctl);
        Bundle args = new Bundle();
        List<AttenceRecord> records = getArguments().getParcelableArrayList(Const.Keys.KEY_ATTENCE_RECORDS);
        args.putParcelableArrayList(Const.Keys.KEY_ATTENCE_RECORDS, (ArrayList<? extends Parcelable>) records);
        mFragments.add(Fragment.instantiate(getActivity(), MyAttenceFragment.class.getName(), args));
        args.putString("type", "1");
        mFragments.add(Fragment.instantiate(getActivity(), ContainerFragment.class.getName(), args));
        args = new Bundle();
        args.putString("type", "2");
        mFragments.add(Fragment.instantiate(getActivity(), ContainerFragment.class.getName(), args));
        mFragments.add(new PersonCenterFragment());
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        mTabLayout.setTabData(mTabEntities, getActivity(),  R.id.fl_container, mFragments);
    }

    @Override
    protected int getLayoutId() {
        return  R.layout.fragment_main;
    }
}
