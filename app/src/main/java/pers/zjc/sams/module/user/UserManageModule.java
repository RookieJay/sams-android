package pers.zjc.sams.module.user;

import com.zp.android.zlib.di.FragmentScope;
import com.zp.android.zlib.http.HttpClient;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import pers.zjc.sams.module.user.contract.UserManageContract;
import pers.zjc.sams.service.ApiService;

@Module
public class UserManageModule {

    private UserManageContract.View view;

    @Inject
    UserManageModule(UserManageContract.View view) {
        this.view = view;
    }

    @Provides
    UserManageContract.View provideView() {
        return view;
    }

    @Provides
    ApiService provideApiservice(HttpClient client) {
        return client.create(ApiService.class);
    }
}

