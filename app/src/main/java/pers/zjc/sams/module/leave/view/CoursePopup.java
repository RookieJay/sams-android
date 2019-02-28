package pers.zjc.sams.module.leave.view;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;

import java.util.ArrayList;
import java.util.List;

import pers.zjc.sams.R;
import pers.zjc.sams.data.entity.Course;
import razerdp.basepopup.BasePopupWindow;

public class CoursePopup extends BasePopupWindow  implements CourseAdapter.ItemClickListener{

    private RecyclerView mRecyclerView;
    private CourseAdapter adapter;
    private PopupItemClick popupItemClick;
    private List<Course> courseList = new ArrayList<>();

    public CoursePopup(Context context, PopupItemClick popupItemClick) {
        super(context);
        this.popupItemClick = popupItemClick;
    }

    public CoursePopup(Context context, boolean delayInit) {
        super(context, delayInit);
    }

    public CoursePopup(Context context, int width, int height) {
        super(context, width, height);
    }

    public CoursePopup(Context context, int width, int height, boolean delayInit) {
        super(context, width, height, delayInit);
    }

    @Override
    public View onCreateContentView() {
        View view = createPopupById(R.layout.popup_choose_course);
        mRecyclerView = view.findViewById(R.id.mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        adapter = new CourseAdapter(getContext(), this);
        mRecyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    protected Animation onCreateShowAnimation() {
        return getDefaultAlphaAnimation(true);
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        return getDefaultAlphaAnimation(false);
    }

    @Override
    public void onPopupItemClick(Course data) {
        popupItemClick.onPopupItemclick(data);
        dismiss();
    }

    public void setData(List<Course> data) {
        if (courseList != null) {
            courseList.clear();
            courseList.addAll(data);
        }
        courseList = data;
        adapter.addAll(data);
    }

    public interface PopupItemClick {
        void onPopupItemclick(Course data);
    }
}
