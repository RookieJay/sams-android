//
// Created by Michael on 2017/12/20.
//

#include <cstdlib>
#include <memory.h>
#include "CUtil.h"
#include "AndroidLog.h"

/**
 * 将GBK编码的jstring转化为char数组
 * @param [in]env
 * @param [in]jstr
 * @param [out]returnChar 用户自己管理内存，自己开辟自己销毁
 * @param [in]returnCharLen 用户开辟的returnChar的长度
 * @return 字符串长度,为-1则表示超过指定长度，不为0则表示字符串的长度，0表示空字符串
 */

int jstringToPCharGBK(JNIEnv *env, jstring jstr, char *returnChar, int returnCharLen) {
    if (returnChar == NULL) {
        return 0;
    }

    jclass tmpClass = env->FindClass("java/lang/String");
    jstring strencode = env->NewStringUTF("gb2312");
    jmethodID mid = env->GetMethodID(tmpClass, "getBytes", "(Ljava/lang/String;)[B");
    jbyteArray barr = (jbyteArray) env->CallObjectMethod(jstr, mid, strencode);

    jsize alen = env->GetArrayLength(barr);
    jbyte *ba = env->GetByteArrayElements(barr, JNI_FALSE);
    //如果长度为0，则直接返回
    if (alen <= 0) {
        return 0;
    }

    //为了保证，returnChar[alen] = 0;（字符串截断）所以实际的字符串jstr的长度需要小于开辟字符数组长度returnCharLen
    if (returnCharLen <= alen) {
        return -1;
    }

    strcpy(returnChar, reinterpret_cast<const char *>(ba));
    returnChar[alen] = 0;
    env->ReleaseByteArrayElements(barr, ba, 0);
    return alen;

}

/**
 * char数组转成jstring  utf编码格式
 * @param env
 * @param pat  char数组
 */
jstring charToJstringUTF8(JNIEnv *env, const char *pat) {
    // 定义java String类 strClass 
    jclass strClass = env->FindClass("java/lang/String");
    // 获取java String类方法String(byte[],String)的构造器,用于将本地byte[]数组转换为一个新String  
    jmethodID ctorID = env->GetMethodID(strClass, "<init>", "([BLjava/lang/String;)V");
    // 建立byte数组  
    jbyteArray bytes = env->NewByteArray(strlen(pat));
    // 将char* 转换为byte数组  
    env->SetByteArrayRegion(bytes, 0, strlen(pat), (jbyte *) pat);
    //设置String, 保存语言类型,用于byte数组转换至String时的参数  
    jstring encoding = env->NewStringUTF(
            "utf-8");   //原本：gb-2312 chang:直接用utf-8编码格式，解决Java层调用中文乱码问题；
    //将byte数组转换为java String,并输出  
    jstring result = (jstring) env->NewObject(strClass, ctorID, bytes, encoding);
    (env)->DeleteLocalRef(bytes);
    env->DeleteLocalRef(encoding);
    return result;
}

/**
 * char数组转成jstring  GBK 编码格式
 *   @param env
 * @param pat  char数组
 */
jstring charToJstringGBK(JNIEnv *env, const char *pat) {
    // 定义java String类 strClass 
    jclass strClass = env->FindClass("java/lang/String");
    // 获取java String类方法String(byte[],String)的构造器,用于将本地byte[]数组转换为一个新String  
    jmethodID ctorID = env->GetMethodID(strClass, "<init>", "([BLjava/lang/String;)V");
    // 建立byte数组  
    jbyteArray bytes = env->NewByteArray(strlen(pat));
    // 将char* 转换为byte数组  
    env->SetByteArrayRegion(bytes, 0, strlen(pat), (jbyte *) pat);
    //设置String, 保存语言类型,用于byte数组转换至String时的参数  
    jstring encoding = env->NewStringUTF(
            "gb-2312");
    //将byte数组转换为java String,并输出  
    jstring result = (jstring) env->NewObject(strClass, ctorID, bytes, encoding);
    env->DeleteLocalRef(bytes);
    env->DeleteLocalRef(encoding);
    return result;
}


/**
*  绑定线程，jni回调到java层需要用到
* @param env   注意这里用取地址符
* @return  true--已经绑定成功，false--已经绑定，无需再绑定
*/
bool attachThread(JavaVM *mJavaVM, JNIEnv *&env) {
    bool attached = false;
    if (mJavaVM == NULL) {
        LOGE("JavaVM == NULL!!!，please check");
        return attached;
    }
    switch (mJavaVM->GetEnv((void **) &env, JNI_VERSION_1_6)) {
        case JNI_OK://已绑定
            LOGI("JNI_OK");
            break;
        case JNI_EDETACHED://已解绑
            LOGI("JNI_EDETACHED");
            if (mJavaVM->AttachCurrentThread(&env, NULL) != JNI_OK) {
                LOGE("Could not attach current thread. ");
            } else {
                attached = true;
            }

            break;
        case JNI_EVERSION:
            LOGE("Invalid java version. ");
    }
    return attached;
}

////////// 旧的写法 获取JNI Env 偶现DetachThread Error 
/*
 * 
 * static pthread_key_t mThreadKey;
 * static JNIEnv *Android_JNI_GetEnv() {

    JNIEnv *env;
    if (mJavaVM == NULL) {
        LOGE("mJavaM== NULL !!!");
        return NULL;

    }
    int status = mJavaVM->AttachCurrentThread(&env, NULL);
    if (status != JNI_OK) {
        LOGE("failed to attach current thread");
        return NULL;
    }
    return env;
}

static void Android_JNI_ThreadDestroyed(void *value) {
    JNIEnv *env = (JNIEnv *) value;
    if (env != NULL && mJavaVM != NULL) {
        mJavaVM->DetachCurrentThread();
        LOGE("detach called");
        pthread_setspecific(mThreadKey, NULL);
    }
}

JNIEnv *Android_JNI_SetupThread(void) {

    JNIEnv *env = Android_JNI_GetEnv();
    if (env == NULL)
        return NULL;
    int status = pthread_setspecific(mThreadKey, (void *) env);
    if (status != JNI_OK) {
        LOGE("pthread_setspecific failed");
    }
    return env;
}

JNIEnv *initJNIEnv() {
    if (pthread_key_create(&mThreadKey, Android_JNI_ThreadDestroyed)) {
        LOGE("Error initializing pthread key");
    }
    return Android_JNI_SetupThread();
}*/


