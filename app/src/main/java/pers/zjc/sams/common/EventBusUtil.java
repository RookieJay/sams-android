package pers.zjc.sams.common;

import com.zp.android.zlib.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;

import pers.zjc.sams.common.event.Event;

/**
 * @author jayqiu.
 * @description
 * @Created time 2017-09-26 22 ：13
 */
public class EventBusUtil {
    private static final String TAG = "EventBusUtils";
    public static void register(Object subscriber) {
        if (!EventBus.getDefault().isRegistered(subscriber)) {
            LogUtils.e(TAG, "register: 注册成功");
            EventBus.getDefault().register(subscriber);
        } else {
            LogUtils.e(TAG, "register: 注册失败");
        }
    }

    public static void unregister(Object subscriber) {
        if (EventBus.getDefault().isRegistered(subscriber)) {
            LogUtils.e(TAG, "unregister: 成功");
            EventBus.getDefault().unregister(subscriber);
        } else {
            LogUtils.e(TAG, "unregister:失败");
        }

    }

    public static void sendEvent(Event event) {
        EventBus.getDefault().post(event);
    }

    public static void sendStickyEvent(Event event) {
        EventBus.getDefault().postSticky(event);
    }


    public static <T> void removeStickyEvent(Class<T> eventType) {
        T stickyEvent = EventBus.getDefault().getStickyEvent(eventType);
        if (stickyEvent != null) {
            EventBus.getDefault().removeStickyEvent((T) stickyEvent);
        }
    }

    /**
     * 移除所有的粘性订阅事件
     */
    public static void removeAllStickyEvents() {
        EventBus.getDefault().removeAllStickyEvents();
    }

    /**
     * 取消事件传送
     *
     * @param event 事件对象
     */
    public static void cancelEventDelivery(Object event) {
        EventBus.getDefault().cancelEventDelivery(event);
    }
}
