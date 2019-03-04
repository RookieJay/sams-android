package pers.zjc.sams.module.personcenter;

import com.zp.android.zlib.http.HttpClient;

import dagger.Module;
import dagger.Provides;
import pers.zjc.sams.module.personcenter.contract.ModifyPwdContract;
import pers.zjc.sams.service.ApiService;

@Module
public class ModifyPwdModule {

    private ModifyPwdContract.View view;

    public ModifyPwdModule(ModifyPwdContract.View view) {
        this.view = view;
    }

    @Provides
    ModifyPwdContract.View provideView() {
        return this.view;
    }

    @Provides
    ApiService provideApiService(HttpClient client) {
        return client.create(ApiService.class);
    }
}
