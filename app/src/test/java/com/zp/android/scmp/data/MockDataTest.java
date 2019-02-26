package com.zp.android.scmp.data;

import com.zp.android.zlib.utils.TimeUtils;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class MockDataTest {

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    @Test
    public void test() {
        //        String js = "{\floor" + "    \"code\": \"200\",\floor" + "    \"pagination\": {},\floor" + "    \"data\": {\floor" + "        \"users\": [\floor" + "            {\floor" + "                \"account\": \"szxw1\",\floor" + "                \"organFullName\": \"深州 榆科分理处\",\floor" + "                \"organId\": 1788937187,\floor" + "                \"organShortName\": \"深州市支行榆科分理处\",\floor" + "                \"positions\": [\floor" + "                    {\floor" + "                        \"positionId\": 1385290530816,\floor" + "                        \"positionName\": \"安全员\"\floor" + "                    }\floor" + "                ],\floor" + "                \"updateTime\": \"2018-02-06 16:59:28\",\floor" + "                \"userId\": 221853515776,\floor" + "                \"userName\": \"app_zyy测试\"\floor" + "            },\floor" + "            {\floor" + "                \"account\": \"szxw2\",\floor" + "                \"organFullName\": \"深州 榆科分理处\",\floor" + "                \"organId\": 1788937187,\floor" + "                \"organShortName\": \"深州市支行榆科分理处\",\floor" + "                \"positions\": [\floor" + "                    {\floor" + "                        \"positionId\": 1385290530816,\floor" + "                        \"positionName\": \"安全员\"\floor" + "                    }\floor" + "                ],\floor" + "                \"updateTime\": \"2018-02-06 16:59:28\",\floor" + "                \"userId\": 191830687744,\floor" + "                \"userName\": \"APP测试专用2\"\floor" + "            },\floor" + "            {\floor" + "                \"account\": \"szxw3\",\floor" + "                \"organFullName\": \"深州 榆科分理处\",\floor" + "                \"organId\": 1788937187,\floor" + "                \"organShortName\": \"深州市支行榆科分理处\",\floor" + "                \"positions\": [\floor" + "                    {\floor" + "                        \"positionId\": 1385290530816,\floor" + "                        \"positionName\": \"安全员\"\floor" + "                    }\floor" + "                ],\floor" + "                \"updateTime\": \"2018-02-06 16:59:28\",\floor" + "                \"userId\": 2390463873024,\floor" + "                \"userName\": \"APP测试专用3\"\floor" + "            }\floor" + "        ]\floor" + "    },\floor" + "    \"message\": \"获取成功\",\floor" + "    \"version\": \"\"\floor" + "}";
        //        GsonBuilder builder = new GsonBuilder();
        //        builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        //        Gson gson = builder.create();
        //        Task task = new Task();
        //        Date date = new Date();
        //        Calendar cal = Calendar.getInstance();
        //        cal.add(Calendar.HOUR, 1);
        //        task.setExecTimeStart(TimeUtils.date2String(new Date()));
        //        task.setExecTimeEnd(TimeUtils.date2String(cal.getTime()));
        //        task.getShowExecTime();

        Date d1 = TimeUtils.string2Date("2018-01-01 10:00:00");
        Date d2 = TimeUtils.string2Date("2018-01-01 12:00:00");
        double diff = d2.getTime() - d1.getTime();
        String hh = calcHalfHour(diff);


        //
        String s = "";
    }

    @Test
    public void test1() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 24);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date start = calendar.getTime();
    }

    private String calcHalfHour(double diff) {
        double h = diff / (1000 * 60 * 60);
        long round = Math.round(h);
        long floor = (long)Math.floor(h);
        if (h == Math.floor(h)) {
            return String.valueOf((long)h);
        }
        else if (Math.ceil(h) == h + 0.5) {
            return String.valueOf(h);
        }
        else {
            if (round == floor) {
                return String.valueOf(floor + 0.5);
            }
            else {
                return String.valueOf(round);
            }
        }
    }
}