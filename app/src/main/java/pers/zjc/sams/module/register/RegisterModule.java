package pers.zjc.sams.module.register;


import com.zp.android.zlib.http.HttpClient;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import pers.zjc.sams.module.register.contract.RegisterContract;
import pers.zjc.sams.service.ApiService;

@Module
public class RegisterModule {

    private RegisterContract.View mView;

    @Inject
    public RegisterModule(RegisterContract.View mView) {
        this.mView = mView;
    }

    @Provides
    RegisterContract.View provideView() {
        return mView;
    }

    @Provides
    ApiService provideApiService(HttpClient client) {
        return client.create(ApiService.class);
    }

}
