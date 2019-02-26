package pers.zjc.sams.module.login;


import com.zp.android.zlib.http.HttpClient;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import pers.zjc.sams.module.login.contract.LoginContract;
import pers.zjc.sams.service.ApiService;

@Module
public class LoginModule {

    private LoginContract.View mView;

    @Inject
    public LoginModule(LoginContract.View mView) {
        this.mView = mView;
    }

    @Provides
    LoginContract.View provideView() {
        return mView;
    }

    @Provides
    ApiService provideApiService(HttpClient client) {
        return client.create(ApiService.class);
    }

}
