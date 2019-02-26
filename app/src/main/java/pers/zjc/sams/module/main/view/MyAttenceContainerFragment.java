package pers.zjc.sams.module.main.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.zp.android.zlib.base.BaseFragment;

import pers.zjc.sams.R;
import pers.zjc.sams.module.myattence.view.MyAttenceFragment;

public class MyAttenceContainerFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mywork_container;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentManager childFragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = childFragmentManager.beginTransaction();
        transaction.add(R.id.fl_container, new MyAttenceFragment(), MyAttenceFragment.class.getSimpleName());
        transaction.commitAllowingStateLoss();
    }
}
