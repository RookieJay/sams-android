package pers.zjc.sams.module.center;

import com.zp.android.zlib.http.HttpClient;

import dagger.Module;
import dagger.Provides;
import pers.zjc.sams.module.center.contract.CenterContract;
import pers.zjc.sams.module.myattence.contract.MyAttenceContract;
import pers.zjc.sams.service.ApiService;

@Module
public class CenterModule {

    private CenterContract.View view;

    public CenterModule(CenterContract.View view) {
        this.view = view;
    }

    @Provides
    CenterContract.View provideView() {
        return this.view;
    }

    @Provides
    ApiService provideApiService(HttpClient client) {
        return client.create(ApiService.class);
    }
}
