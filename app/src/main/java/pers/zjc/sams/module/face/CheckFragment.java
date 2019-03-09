package pers.zjc.sams.module.face;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zp.android.zlib.base.BaseFragment;

import pers.zjc.sams.R;
import pers.zjc.sams.app.SamsApplication;
import pers.zjc.sams.module.main.MainActivity;

public class CheckFragment extends BaseFragment {

    private MainActivity curActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof MainActivity) {
        this.curActivity = (MainActivity) activity;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_maintain;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Intent intent = new Intent(getActivity(), PermissionAcitivity.class);
        curActivity.startActivity(intent);
    }
}
