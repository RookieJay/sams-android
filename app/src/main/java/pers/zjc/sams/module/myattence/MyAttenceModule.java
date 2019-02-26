package pers.zjc.sams.module.myattence;

import com.zp.android.zlib.http.HttpClient;

import dagger.Module;
import dagger.Provides;
import pers.zjc.sams.module.myattence.contract.MyAttenceContract;
import pers.zjc.sams.service.ApiService;

@Module
public class MyAttenceModule {

    private MyAttenceContract.View view;

    public MyAttenceModule(MyAttenceContract.View view) {
        this.view = view;
    }

    @Provides
    MyAttenceContract.View provideView() {
        return this.view;
    }

    @Provides
    ApiService provideApiService(HttpClient client) {
        return client.create(ApiService.class);
    }
}
