package pers.zjc.sams.data.datawrapper;

import java.util.List;

import pers.zjc.sams.data.entity.Admin;
import pers.zjc.sams.data.entity.AttenceRecord;
import pers.zjc.sams.data.entity.Student;
import pers.zjc.sams.data.entity.Teacher;

/**
 * {
 *         "role": "1",
 *         "userId": "10010001",
 *         "token": "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ4cyIsInVzZXJOYW1lIjoieHMiLCJwd2QiOiIxMjMiLCJleHAiOjE1NTEwODU2MzIsImlhdCI6MTU1MTA4NTYyNX0.hgUPdjD8NlZNAeHs4GHIDnqnI-ebE7tnET6gGEtEzaA",
 *         "student": {
 *             "stuId": 10010001,
 *             "classId": 1501,
 *             "birthday": "1997-06-25 00:20:07",
 *             "idCard": "51112619970625701x",
 *             "name": "周俊材",
 *             "email": "1475485144@qq.com",
 *             "tel": "17723372606",
 *             "sex": 1,
 *             "status": 1,
 *             "major": "计算机科学与技术"
 *         },
 *         "records": [
 *             {
 *                 "attenceId": 4,
 *                 "status": 0,
 *                 "stuId": 10010001,
 *                 "courseId": 2,
 *                 "operator": "张三",
 *                 "createTime": "2019-02-25 17:04:50",
 *                 "updateTime": ""
 *             }
 *         ]
 *     }
 */
public class UserWrapper {

    private Admin admin;
    private Student student;
    private Teacher teacher;
    private String userId;
    private String userName;
    private String role;
    private String token;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
