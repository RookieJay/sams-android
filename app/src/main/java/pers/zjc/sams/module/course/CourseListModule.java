package pers.zjc.sams.module.course;


import com.zp.android.zlib.http.HttpClient;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import pers.zjc.sams.module.course.contract.CourseListContract;
import pers.zjc.sams.service.ApiService;

@Module
public class CourseListModule {

    private CourseListContract.View mView;

    @Inject
    public CourseListModule(CourseListContract.View mView) {
        this.mView = mView;
    }

    @Provides
    CourseListContract.View provideView() {
        return mView;
    }

    @Provides
    ApiService provideApiService(HttpClient client) {
        return client.create(ApiService.class);
    }

}
