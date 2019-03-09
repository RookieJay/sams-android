package pers.zjc.sams.module.personinfo.contract;

import pers.zjc.sams.data.datawrapper.UserWrapper;
import pers.zjc.sams.data.entity.Result;
import pers.zjc.sams.data.entity.Student;
import pers.zjc.sams.data.entity.Teacher;

public interface PersonInfoContract {
    interface Model {
        Result<Student> getStuInfo(String userId);

        Result<Teacher> getTeacherInfo(String userId);

        Result updateStu(Student student);

        Result updateTeacher(Teacher teacher);
    }

    interface View {
        void showNetWorkErro();

        void fillStuData(Student data);

        void showMessage(String message);

        void fillTeacherData(Teacher data);

        void back();
    }

    interface Presenter {
        void loadData();

        void commit(Student student);

        void commit(Teacher teacher);
    }
}
