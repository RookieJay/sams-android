package pers.zjc.sams.module.main.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.zp.android.zlib.base.BaseFragment;

import pers.zjc.sams.R;
import pers.zjc.sams.module.center.view.CenterFragment;

public class ContainerFragment extends BaseFragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentManager cfm = getChildFragmentManager();
        FragmentTransaction trans = cfm.beginTransaction();
        Fragment fm = Fragment.instantiate(getActivity(), CenterFragment.class.getName(), getArguments());
        trans.add(R.id.fl_container, fm);
        trans.addToBackStack(CenterFragment.class.getSimpleName());
        trans.commitAllowingStateLoss();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_container;
    }
}
