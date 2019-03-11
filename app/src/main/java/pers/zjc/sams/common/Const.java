package pers.zjc.sams.common;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;

public interface Const {

    String DATETIME_NULL = "1900-01-01 00:00:00";
    String HAS_INSP = "1";

    interface Keys {

        String KEY_SELECT_ORG_DATA = "key_select_org_data";
        String KEY_ORG_TYPE = "key_org_type";
        String KEY_TASK_ID = "key_task_id";
        String KEY_REGION = "key_region";
        String KEY_TASK = "key_task";
        String KEY_NOTICE = "key_notice";
        String KEY_INSPAREAID = "key_inspareaid";
        String KEY_SCAN_NFC = "key_scan_nfc";
        String KEY_NFC_ID = "key_nfc_id";
        String KEY_OPENED = "key_opened";
        String KEY_FRAGMENT_TYPE = "key_fragment_type";
        String KEY_MAINT_TASK = "key_maint_task";
        String KEY_OPSINSP_TASK = "key_opsinsp_task";

        /*attence*/
        String KEY_ATTENCE_RECORDS = "key_attence_records";
        String KEY_USER_NAME = "key_user_name";
        String KEY_LEAVE = "key_leave";
    }

    interface Offline {

        // 安全检查任务执行
        String MID_TASK_EXEC = "10000";
        // 问题整改任务执行
        String MID_REFORM_EXEC = "10001";
        // 文件上传
        String MID_FILE_DATA = "10002";
        // 任务完成
        String MID_TASK_FINISH = "10003";
        // 任务回执
        String MID_TASK_LOG = "10004";
        // 巡检区域排序
        String MID_ORDER_INSPAREA = "10005";
        // 采集点注册
        String MID_REG_INSPPOINT = "10006";
        // 采集点注册
        String OPS_INSP_EXEC = "10007";
    }

    interface Actions {

        String ACTION_PING = "com.zp.android.scmp.Action_Ping";
        String ACTION_OFFLINEDATA_COMMIT = "com.zp.android.scmp.Action_OfflineData_Commit";
        String ACTION_OFFLINEDATA_DEL = "com.zp.android.scmp.Action_OfflineData_Del";
        String ACTION_OFFLINEDATA_INIT = "com.zp.android.scmp.Action_OfflineData_Init";
        String ACTION_TASK_FINISHED = "com.zp.android.scmp.Action_Task_Finished";
        String ACTION_SECCHECK_FILTER = "com.zp.android.scmp.ACTION_SECCHECK_FILTER";
        String ACTION_SELECT_DATE_RESULT = "com.zp.android.scmp.ACTION_SELECT_DATE_RESULT";
        String ACTION_UPDATE_STATUS_EDUCATION_FINISH = "com.zp.android.scmp.ACTION_UPDATE_STATUS_EDUCATION_FINISH";
        String ACTION_REFORM_HISTORY_FILTER = "com.zp.android.scmp.reform_history_filter";
        String ACTION_AUTH_ERROR = "com.zp.android.scmp.ACTION_AUTH_ERROR";
        String ACTION_MAINT_FILTER = "com.zp.android.scmp.ACTION_MAINT_FILTER";
        String ACTION_LOGOUT = "pers.zjc.android.sams.logout";
        String ACTION_REFRESH_PERSON_INFO = "pers.zjc.android.sams.refresh_person_info";
    }

    interface UrlConst {

        String URL_GET_FACE_FILE = "/api/mobile/face/file";
        String URL_GET_FILE = "/api/mobile/system/file";
        String URL_DOWNLOAD_FILE = "/api/mobile/system/download";
        String URL_CHECK_UPDATE = "/api/mobile/system/version";
    }

    interface HttpStatusCode {

        /**
         * 成功
         */
        String HttpStatus_200 = "200";
        /**
         * 未通过身份认证
         */
        String HttpStatus_401 = "401";
        /**
         * 没有访问对应资源的权限
         */
        String HttpStatus_403 = "403";
        /**
         * 未找到资源
         */
        String HttpStatus_404 = "404";
        /**
         * 服务端错误
         */
        String HttpStatus_500 = "500";
    }

    interface  VersionType{
        /**
         * 人脸
         */
        String FACE = "Face";
        /**
         * 指纹
         */
        String FINGERPRINT = "Fingerprint";
    }

    @SuppressLint("SimpleDateFormat")
    interface DateFormat {

        SimpleDateFormat WITH_HMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat WITHOUT_HMS = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat WITHOUT_HMS_00 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        SimpleDateFormat HHMM = new SimpleDateFormat("HH:mm");
        SimpleDateFormat HMM = new SimpleDateFormat("H:mm");
        SimpleDateFormat MMDDHHmm = new SimpleDateFormat("MM-dd HH:mm");
        SimpleDateFormat CN_M_D = new SimpleDateFormat("M月d日");
        SimpleDateFormat CN_MM_DD = new SimpleDateFormat("MM月dd日");
        SimpleDateFormat CN_MD_H_m = new SimpleDateFormat("M月d日 H时m分");
        SimpleDateFormat CN_WITHOUT_HMS = new SimpleDateFormat("yyyy年MM月dd日");
    }
}


