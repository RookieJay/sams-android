package com.zp.android.zlib.common;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;

public class Const {

    /**
     * 选择相册
     */
    public static final int IMG = 1001;

    /**
     * GSP间隔
     */
    public static final long GPS_INTERVAL = 120 * 1000;

    public static class FragType {

        public static final String LIST = "FRAG_TYPE_LIST";
        public static final String DETAIL = "FRAG_TYPE_DETAIL";
        public static final String BACK = "FRAG_TYPE_BACK";
    }

    public static class Argument {

        public static final String INSPECTION_DATA = "ARGUMENT_INSPECTION_DATA";
        public static final String INSPECTION_BEGINTIME = "ARGUMENT_BEGINTIME";
        public static final String LINE_IDS = "ARGUMENT_LINE_IDS";
        public static final String FRAG_TYPE = "ARGUMENT_FRAG_TYPE";
        public static final String INTERVAL_GPS = "INTERVAL_GPS";
        public static final String CAR_ID = "CAR_ID";
    }

    public static class Text {

        public static final String SUBMIT_DATA = "TEXT_SUBMIT_DATA";
        public static final String SUBMIT_DATA_SUCCESS = "TEXT_SUBMIT_DATA_SUCCESS";
        public static final String SUBMIT_DATA_FAILED = "TEXT_SUBMIT_DATA_FAILED";
        public static final String PROCESSING = "TEXT_PROCESSING";
        public static final String PROCESSING_FAILED = "TEXT_PROCESSING_FAILED";
        public static final String GETTING_DATA = "TEXT_GETTING_DATA";
        public static final String GETTING_DATA_FAILED = "TEXT_GETTING_FAILED";
        public static final String UPLOAD_FILE = "TEXT_UPLOAD_FILE";
        //
        public static final String NFC_ID_ERROR = "TEXT_NFC_ID_ERROR";
        public static final String NFC_ID_IS_EMPTY = "TEXT_NFC_ID_IS_EMPTY";
        public static final String ORDER_NOT_EXISTS = "TEXT_ORDER_NOT_EXISTS";
        public static final String ORDER_DELIVERED = "TEXT_ORDER_DELIVERED";
        public static final String DISPATCH_NOT_STARTED = "TEXT_DISPATCH_NOT_STARTED";
        public static final String DISPATCH_NOT_EXISTS = "TEXT_DISPATCH_NOT_EXISTS";
        public static final String DISPATCH_SUBMIT_CANT_REPORT_COST = "TEXT_DISPATCH_SUBMIT_CANT_REPORT_COST";
        public static final String DISPATCH_ENDED_CANT_ADJUST = "TEXT_DISPATCH_ENDED_CANT_ADJUST";
        public static final String DISPATCH_SUBMIT_CANT_ADJUST = "TEXT_DISPATCH_SUBMIT_CANT_ADJUST";
        public static final String DISPATCH_ENDED_CANT_DELIVERY = "TEXT_DISPATCH_ENDED_CANT_DELIVERY";
        public static final String DISPATCH_SUBMIT_CANT_DELIVERY = "TEXT_DISPATCH_SUBMIT_CANT_DELIVERY";
        public static final String NOT_DOWNLOAD_ORDERS_CANT_START = "TEXT_NOT_DOWNLOAD_ORDERS_CANT_START";
        public static final String FINISHDISTRIBUTION_MILEAGE_ERROR = "TEXT_FINISHDISTRIBUTION_MILEAGE_ERROR";
        public static final String GET_LOCATION = "TEXT_GET_LOCATION";
        public static final String INPUT_REMARK = "TEXT_INPUT_REMARK";
        public static final String LOAD_DATA = "TEXT_LOAD_DATA";
        public static final String SELECT_LOAD_LINE_DATA = "TEXT_LOAD_LINE_DATA";
    }

    public static class PhotoType {
        /**
         * 巡检
         */
        public static final String INSPECTION = "0";
        /**
         * 报修
         */
        public static final String DEVICEREPAIR = "1";
    }

    public static class ScanQueryType{

        public static final String DEVICEID="id";

        public static final String BARCODE="barcode";
    }

    public static class ScanFromWhere{

        public static final String DEVICEID_REPAIRE="device_repair";

        public static final String DEVICEID_OUT_STORE="device_out_store";

        public static final String DEVICEID_IN_STORE="device_in_store";
    }

    public static class InspectionState{
        /**
         * 未执行
         */
        public static final String UNWORK="0";//未执行
        /**
         * 已开始
         */
        public static final String BEGIN="1";

        /**
         * 已执行
         */
        public static final String WORKFINISH="2";
    }

    @SuppressLint("SimpleDateFormat")
    public static class DateFormat {

        public static final SimpleDateFormat WITH_HMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        public static final SimpleDateFormat WITHOUT_HMS = new SimpleDateFormat("yyyy-MM-dd");
    }
}


