package pers.zjc.sams.module.devicemanage.view;

import com.zp.android.zlib.base.BaseFragment;

import pers.zjc.sams.R;
import pers.zjc.sams.module.devicemanage.contract.DeviceManageContract;

public class DeviceManageFragment extends BaseFragment implements DeviceManageContract.View {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_leave;
    }
}
