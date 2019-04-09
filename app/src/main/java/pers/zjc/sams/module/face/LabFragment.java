package pers.zjc.sams.module.face;

import android.app.Activity;
import android.content.Intent;

import com.zp.android.zlib.base.BaseFragment;

import pers.zjc.sams.R;
import pers.zjc.sams.module.main.MainActivity;

public class LabFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_camera;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof MainActivity) {
            MainActivity curActivity = (MainActivity)activity;
            curActivity.startActivity(new Intent(curActivity.getApplicationContext(), PermissionAcitivity.class));
        }
    }

}
