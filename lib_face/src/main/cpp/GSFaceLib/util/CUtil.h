//
// Created by Michael on 2017/12/21.
//

#ifndef CUNION_VMS_CUTIL_H
#define CUNION_VMS_CUTIL_H

#include <jni.h>

#ifdef __cplusplus
extern "C" {
#endif

//存在内存泄漏，不再使用
//char *jstringToCharGBK(JNIEnv *env, jstring jstr, jsize *len = NULL);
/**
 * 将jstring转化为GBK编码char数组
 * @param [in]env
 * @param [in]jstr
 * @param [out]returnChar 用户自己管理内存，自己开辟自己销毁
 * @param [in]returnCharLen 用户开辟的returnChar的长度
 * @return 字符串长度,为-1则表示超过指定长度，不为0则表示字符串的长度，0表示空字符串
 */
int jstringToPCharGBK(JNIEnv *env, jstring jstr, char *returnChar, int returnCharLen);

jstring charToJstringUTF8(JNIEnv *env, const char *pat);
/**
 * 将GBK编码的字符串char*转化为jstring
 * @param env
 * @param pchar
 * @return
 */
jstring charToJstringGBK(JNIEnv *env, const char *pchar);

/**
 * 线程绑定
 * @param mJavaVM 
 * @param env 
 * @return 
 */
bool attachThread(JavaVM *mJavaVM, JNIEnv *&env);
#ifdef __cplusplus
}
#endif
#endif //CUNION_VMS_CUTIL_H
