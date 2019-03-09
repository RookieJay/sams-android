package pers.zjc.sams.module.face;


import com.zp.android.zlib.base.BaseActivity;
import com.zp.android.zlib.di.ActivityModule;
import com.zp.android.zlib.http.HttpClient;

import dagger.Module;
import dagger.Provides;
import pers.zjc.sams.module.face.contract.FaceUploadContract;
import pers.zjc.sams.service.ApiService;

@Module
public class FaceRegisterModule extends ActivityModule {

    private FaceUploadContract.View view;

    public FaceRegisterModule(BaseActivity activity, FaceUploadContract.View view) {
        super(activity);
        this.view = view;
    }

//    @Provides
//    FaceUploadContract.View provideView(FaceUploadContract.View view) {
//        return this.view;
//    }

    @Provides
    ApiService provideApiService(HttpClient client) {
        return client.create(ApiService.class);
    }

}
