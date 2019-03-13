package pers.zjc.sams.module.devicemanage;

import com.zp.android.zlib.http.HttpClient;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import pers.zjc.sams.module.devicemanage.contract.DeviceManageContract;
import pers.zjc.sams.service.ApiService;

@Module
public class DeviceManageModule {

    private DeviceManageContract.View view;

    @Inject
    public DeviceManageModule(DeviceManageContract.View view) {
        this.view = view;
    }

    @Provides
    DeviceManageContract.View provideView() {
        return view;
    }

    @Provides
    ApiService provideApiService(HttpClient client) {
        return client.create(ApiService.class);
    }

}
