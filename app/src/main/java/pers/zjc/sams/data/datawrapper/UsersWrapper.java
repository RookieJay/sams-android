package pers.zjc.sams.data.datawrapper;


import java.util.List;

import pers.zjc.sams.data.entity.Admin;
import pers.zjc.sams.data.entity.Student;
import pers.zjc.sams.data.entity.Teacher;
import pers.zjc.sams.data.entity.User;

public class UsersWrapper {

    private List<User> users;
    private List<Admin> admins;
    private List<Student> students;
    private List<Teacher> teachers;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Admin> getAdmins() {
        return admins;
    }

    public void setAdmins(List<Admin> admins) {
        this.admins = admins;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }
}
