#include <jni.h>
#include <include/GoNcFaceRegSDK.h>
#include <util/AndroidLog.h>
#include <util/CUtil.h>
#include <util/cJSON.h>
#include <cstdio>
#include <cstdlib>

//定义Log标签
//char *LogTagStr = "GSFaceRecog";

long long handle;
JavaVM *javaVM = NULL;
jobject globalObj = NULL;
jmethodID getNV21toBGRCallBack;

jmethodID getFaceFeatureCallBack;

jmethodID getFaceSimilarityCallBack;

jmethodID getFaceDetectCallBack;


void initCallBack(JNIEnv *env, jobject instance) {
    env->GetJavaVM(&javaVM);
    //函数参数中 jobject 或者它的子类，其参数都是 local reference。Local reference 只在这个 JNI函数中有效，JNI函数返回后，引用的对象就被释放，它的生命周期就结束了。
    // 若要留着日后使用，则需根据这个 local reference 创建 global reference。Global reference 不会被系统自动释放，它仅当被程序明确调用 DeleteGlobalReference 时才被回收。（JNI多线程机制）
    globalObj = env->NewGlobalRef(instance);
    //在子线程中不能这样用
    // jclass objclass = env->FindClass( "com/gosuncn/video/JniRGBPlayer");
    //这种写法可以用在子线程中，但限制了回调方法的位置必须在当前类中，如本例必须在JniInstant类里
    jclass objclass = env->GetObjectClass(instance);

    getNV21toBGRCallBack = env->GetMethodID(objclass, "JniGetNV21toBGRCallBack", "([BII)V");

    getFaceFeatureCallBack = env->GetMethodID(objclass, "JniGetFaceFeatureCallBack",
                                              "([BI)V");

    getFaceSimilarityCallBack = env->GetMethodID(objclass, "JniGetFaceSimilarityCallBack",
                                                 "(F)V");

    getFaceDetectCallBack = env->GetMethodID(objclass, "JniGetFaceDetectCallBack",
                                             "(Ljava/lang/String;)V");
}


void GetNV21toBGRCallBack(unsigned char *bgr, int outWidth, int outHeight, int len) {
    JNIEnv *env;
    bool attached = attachThread(javaVM, env);
    if (env != NULL && globalObj != NULL && getNV21toBGRCallBack != NULL) {
        jbyte *jbyte1 = reinterpret_cast<jbyte *>(bgr);
        jbyteArray jbyteArray1 = env->NewByteArray(len);
        env->SetByteArrayRegion(jbyteArray1, 0, len, jbyte1);
        env->CallVoidMethod(globalObj, getNV21toBGRCallBack, jbyteArray1, (jint) outWidth,
                            (jint) outHeight);
        //释放内存
        env->DeleteLocalRef(jbyteArray1);

    }
    if (attached) {
        javaVM->DetachCurrentThread();
    }
}


void GetFaceFeatureCallBack(char *pFaceFeature, int len) {

    JNIEnv *env;
    bool attached = attachThread(javaVM, env);
    if (env != NULL && globalObj != NULL && getFaceFeatureCallBack != NULL) {
        jbyte *jbyte1 = reinterpret_cast<jbyte *>(pFaceFeature);
        jbyteArray jbyteArray1 = env->NewByteArray(len);
        env->SetByteArrayRegion(jbyteArray1, 0, len, jbyte1);
        env->CallVoidMethod(globalObj, getFaceFeatureCallBack, jbyteArray1, (jint) len);
        //释放内存
        env->DeleteLocalRef(jbyteArray1);

    }
    if (attached) {
        javaVM->DetachCurrentThread();
    }
}

void GetFaceDetectCallBack(FaceTrackedRect *faceTrackedRectArray, int faceCount) {

    cJSON *root;
    root = cJSON_CreateObject();
/* int nX;                                     //人脸区域左上角横坐标
    int nY;                                     //人脸区域左上角纵坐标
    int nWidth;                                 //人脸区域宽度
    int nHeight;                                //人脸区域高度
    int nID;                                    //人脸跟踪ID
    GoPoint2f face_points[FR_POINT_NUM];	   //人脸特征点集*/
    if (NULL == root) {
        LOGE(" root should not be NULL!!!")
        return;
    }

    cJSON_AddNumberToObject(root, "faceCount", faceCount);
    cJSON *trackArray = cJSON_CreateArray();
    /*往根里面添加数组*/
    cJSON_AddItemToObject(root, "faceTrackedRects", trackArray);


    for (int i = 0; i < faceCount; i++) {
        /*创建json对象*/
        cJSON *item = cJSON_CreateObject();
        cJSON_AddItemToArray(trackArray, item);
        cJSON_AddNumberToObject(item, "nX", faceTrackedRectArray[i].nX);
        cJSON_AddNumberToObject(item, "nY", faceTrackedRectArray[i].nY);
        cJSON_AddNumberToObject(item, "nWidth", faceTrackedRectArray[i].nWidth);
        cJSON_AddNumberToObject(item, "nHeight", faceTrackedRectArray[i].nHeight);
        cJSON_AddNumberToObject(item, "nID", faceTrackedRectArray[i].nID);

        cJSON *pointArray = cJSON_CreateArray();
        cJSON_AddItemToObject(item, "face_points", pointArray);
        for (int j = 0; j < FR_POINT_NUM; j++) {
            cJSON *item = cJSON_CreateObject();
            cJSON_AddItemToArray(pointArray, item);
            cJSON_AddNumberToObject(item, "fX", faceTrackedRectArray[i].face_points[j].fX);
            cJSON_AddNumberToObject(item, "fY", faceTrackedRectArray[i].face_points[j].fY);

        }


    }


    char *str = cJSON_Print(root);
    cJSON_Delete(root);
    LOGI("GetFaceDetectCallBack %s", str);
    JNIEnv *env;
    bool attached = attachThread(javaVM, env);
    if (env != NULL && globalObj != NULL && getFaceDetectCallBack != NULL) {
        jstring json = charToJstringGBK(env, reinterpret_cast<const char *>(str));
        free(str);
        env->CallVoidMethod(globalObj, getFaceDetectCallBack, json);

    }
    if (attached) {
        javaVM->DetachCurrentThread();
    }
}


/**
 * 
 * 初始化
 */
extern "C"
JNIEXPORT jint JNICALL
Java_com_gosuncn_facelib_JniFaceRecog_init(JNIEnv *env, jobject instance) {
    initCallBack(env, instance);
    return GoNc_FaceRecognition_Init();

}


/**
 * 反初始化
 */
extern "C"
JNIEXPORT jint JNICALL
Java_com_gosuncn_facelib_JniFaceRecog_cleanUp(JNIEnv *env, jobject instance) {

    return GoNc_FaceRecognition_CleanUp();

}

/**
 * 得到最近一次的错误码
 */
extern "C"
JNIEXPORT jint JNICALL
Java_com_gosuncn_facelib_JniFaceRecog_getLastErrorCode(JNIEnv *env, jobject instance) {

    return GoNc_FaceRecognition_GetLastErrorCode();

}

/**
 * 创建人脸识别实例
 */
extern "C"
JNIEXPORT jint JNICALL
Java_com_gosuncn_facelib_JniFaceRecog_detectorCreate(JNIEnv *env, jobject instance,
                                                     jstring modelPath_) {
    const char *modelPath = env->GetStringUTFChars(modelPath_, 0);
    int ret = GoNc_FaceRecognition_Create(modelPath, &handle);
    LOGD("GoNc_FaceRecognition_Create %d", ret);

    env->ReleaseStringUTFChars(modelPath_, modelPath);

    return ret;
}

/**
 * 释放人脸识别实例
 */
extern "C"
JNIEXPORT jint JNICALL
Java_com_gosuncn_facelib_JniFaceRecog_detectorRelease(JNIEnv *env, jobject instance) {

    return GoNc_FaceRecognition_Release(handle);

}


/**
 * NV21 转BGR,转换后宽高和入参是一致的
 */
extern "C"
JNIEXPORT jint JNICALL
Java_com_gosuncn_facelib_JniFaceRecog_faceImgNV21toBGR(JNIEnv *env, jobject instance,
                                                       jbyteArray imgYuv_, jint width,
                                                       jint height) {
    jbyte *imgYuv = (env)->GetByteArrayElements(imgYuv_, NULL);
    int len = width * height * 3;
    //注意bgr的通道应该是3倍的
    unsigned char *bgrData = new unsigned char[len];

    int ret = GoNc_FaceRecognition_Nv21Tbgr(handle, reinterpret_cast<unsigned char *>(imgYuv),
                                            bgrData, width, height);
    LOGD("GoNc_FaceRecognition_Nv21Tbgr ret = %d", ret);
    //回调
    GetNV21toBGRCallBack(bgrData, width, height, len);
    env->ReleaseByteArrayElements(imgYuv_, imgYuv, 0);
    //释放内存
    delete[] bgrData;
    return ret;

}

/**
 * 人脸检测
 */
extern "C"
JNIEXPORT jint JNICALL
Java_com_gosuncn_facelib_JniFaceRecog_faceDetect(JNIEnv *env, jobject instance, jbyteArray bgr_,
                                                 jint width, jint height, jint streamType,
                                                 jint threadNums) {
    jbyte *bgr = (env)->GetByteArrayElements(bgr_, NULL);

    FaceDetectFilterParams filterParams = {0};
    filterParams.nX = 0;//人脸检测区域左上角横坐标
    filterParams.nY = 0;//人脸检测区域左上角纵坐标
    filterParams.nWidth = width;//人脸检测区域宽度
    filterParams.nHeight = height;//人脸检测区域高度
    filterParams.nMin_size = 10;//最小人脸
    filterParams.nMax_size = 5000;//最大人脸
    filterParams.nStream_type = streamType;////0表示视频流，1表示图片流
    FacePose stuFacePose = {0};
    stuFacePose.fHorizontal_threshold = 0.75;   // 人脸偏移程度限制
    filterParams.pose_thresold = stuFacePose;

    //注意这里是 数组
    FaceTrackedRect *faceTrackedRectArray;
    //数组长度
    int faceCount;

    int ret = GoNc_FaceRecognition_FaceDetect(handle, reinterpret_cast<const unsigned char *>(bgr),
                                              width, height, &filterParams, threadNums,
                                              &faceTrackedRectArray, &faceCount);
    LOGE("faceDetect ret = %d", ret);

    if (ret != GO_NC_FR_SUCCEED) {
        (env)->ReleaseByteArrayElements(bgr_, bgr, 0);
        LOGD("ERROR: GoNc_FaceRecognition_FaceDetect Failed, code: %d.\n", ret);
        return ret;
    }
    LOGD("face_count = %d\n", faceCount);
    /* if (faceCount != 1) {
         (env)->ReleaseByteArrayElements(bgr_, bgr, 0);
         return -1;
     }*/
    /**
     * 当没有检测到人脸的时候注意不能进行下一步获取人脸特征值！！！
     */
    if (faceCount <= 0) {
        LOGE("face Count =0 not found the face!!");
        env->ReleaseByteArrayElements(bgr_, bgr, 0);
        return -11;
    }

    //回调人脸检测数据
    GetFaceDetectCallBack(faceTrackedRectArray, faceCount);

    char *pfeature = NULL;
    int feature_len = 0;
    GoNc_FaceRecognition_GetFaceFeature(handle, reinterpret_cast<const unsigned char *>(bgr), width,
                                        height, &faceTrackedRectArray[0], threadNums,
                                        &pfeature, &feature_len);

    LOGD("faceFeature  len = %d", feature_len);

    GetFaceFeatureCallBack(pfeature, feature_len);

    env->ReleaseByteArrayElements(bgr_, bgr, 0);

    return ret;
}


/**
 * 人脸相似度
 */
extern "C"
JNIEXPORT jint JNICALL
Java_com_gosuncn_facelib_JniFaceRecog_compareFeature(JNIEnv *env, jobject instance,
                                                     jbyteArray faceFeature1_,
                                                     jint faceFeatureLength1,
                                                     jbyteArray faceFeature2_,
                                                     jint faceFeatureLength2) {
    jbyte *faceFeature1 = (env)->GetByteArrayElements(faceFeature1_, NULL);
    jbyte *faceFeature2 = (env)->GetByteArrayElements(faceFeature2_, NULL);

    float fpSimilarity;
    int ret = GoNc_FaceRecognition_CompareFeature(handle,
                                                  reinterpret_cast<const char *>(faceFeature1),
                                                  &faceFeatureLength1,
                                                  reinterpret_cast<const char *>(faceFeature2),
                                                  &faceFeatureLength2, &fpSimilarity);
    //回调到应用层
    if (ret == GO_NC_FR_SUCCEED && getFaceSimilarityCallBack != NULL) {
        env->CallVoidMethod(instance, getFaceSimilarityCallBack, fpSimilarity);
    }
    (env)->ReleaseByteArrayElements(faceFeature1_, faceFeature1, 0);
    (env)->ReleaseByteArrayElements(faceFeature2_, faceFeature2, 0);

    return ret;
}

