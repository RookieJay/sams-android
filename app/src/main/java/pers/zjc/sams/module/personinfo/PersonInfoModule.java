package pers.zjc.sams.module.personinfo;

import com.zp.android.zlib.http.HttpClient;

import dagger.Module;
import dagger.Provides;
import pers.zjc.sams.module.personcenter.contract.PersonCenterContract;
import pers.zjc.sams.module.personinfo.contract.PersonInfoContract;
import pers.zjc.sams.service.ApiService;

@Module
public class PersonInfoModule {

    private PersonInfoContract.View view;

    public PersonInfoModule(PersonInfoContract.View view) {
        this.view = view;
    }

    @Provides
    PersonInfoContract.View provideView() {
        return this.view;
    }

    @Provides
    ApiService provideApiService(HttpClient client) {
        return client.create(ApiService.class);
    }
}
