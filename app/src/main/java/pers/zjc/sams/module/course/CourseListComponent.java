package pers.zjc.sams.module.course;

import com.zp.android.zlib.di.BaseComponent;
import com.zp.android.zlib.di.FragmentScope;

import dagger.Component;
import pers.zjc.sams.app.AppComponent;
import pers.zjc.sams.module.course.view.CourseListFragment;

@SuppressWarnings("WeakerAccess")
@FragmentScope
@Component(modules = CourseListModule.class, dependencies = AppComponent.class)
public interface CourseListComponent extends BaseComponent<CourseListFragment> {
}
