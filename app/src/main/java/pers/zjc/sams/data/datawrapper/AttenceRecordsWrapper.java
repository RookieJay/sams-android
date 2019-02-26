package pers.zjc.sams.data.datawrapper;

import java.util.List;

import pers.zjc.sams.data.entity.AttenceRecord;
/**
 *
 *         "records": [
 *             {
 *                 "attenceId": 1,
 *                 "status": 1,
 *                 "stuId": 10010001,
 *                 "courseId": 2,
 *                 "courseName": "计算机操作系统",
 *                 "operator": "张三",
 *                 "createTime": "2019-02-26 19:07:22",
 *                 "updateTime": "2019-02-26 14:13:13"
 *             },
 *             {
 *                 "attenceId": 2,
 *                 "status": 2,
 *                 "stuId": 10010001,
 *                 "courseId": 1,
 *                 "courseName": "数据结构",
 *                 "operator": "张三",
 *                 "createTime": "2019-02-26 19:08:01",
 *                 "updateTime": "2019-02-26 14:14:57"
 *             },
 *             {
 *                 "attenceId": 3,
 *                 "status": 0,
 *                 "stuId": 10010001,
 *                 "courseId": 3,
 *                 "courseName": "计算机网络",
 *                 "operator": "李四",
 *                 "createTime": "2019-02-26 15:06:20",
 *                 "updateTime": "2019-02-26 14:13:04"
 *             },
 *             {
 *                 "attenceId": 4,
 *                 "status": 4,
 *                 "stuId": 10010001,
 *                 "courseId": 2,
 *                 "courseName": "计算机操作系统",
 *                 "operator": "张三",
 *                 "createTime": "2019-02-26 17:04:50",
 *                 "updateTime": "2019-02-26 14:31:45"
 *             },
 *             {
 *                 "attenceId": 5,
 *                 "status": 3,
 *                 "stuId": 10010001,
 *                 "courseId": 3,
 *                 "courseName": "计算机网络",
 *                 "operator": "张A",
 *                 "createTime": "2019-02-26 11:04:50",
 *                 "updateTime": "2019-02-26 14:31:40"
 *             }
 *         ]
 * */
public class AttenceRecordsWrapper {

    private List<AttenceRecord> records;

    public List<AttenceRecord> getRecords() {
        return records;
    }

    public void setRecords(List<AttenceRecord> records) {
        this.records = records;
    }

}
