package pers.zjc.sams.module.course;

import com.zp.android.zlib.di.BaseComponent;
import com.zp.android.zlib.di.FragmentScope;

import dagger.Component;
import pers.zjc.sams.app.AppComponent;
import pers.zjc.sams.module.course.view.CourseEditFragment;
import pers.zjc.sams.module.course.view.CourseListFragment;

@SuppressWarnings("WeakerAccess")
@FragmentScope
@Component(modules = CourseEditModule.class, dependencies = AppComponent.class)
public interface CourseEditComponent extends BaseComponent<CourseEditFragment> {
}
