/***************************************
* @file     GoNcFaceRegSDK.h
* @brief    人脸识别算法库
* @details
* @author
* @date     2018-9
* @mod      add  android log  ang yuv420(nv21) support
****************************************/
#ifndef GXX_FACE_RECOGNITION_SDK_H
#define GXX_FACE_RECOGNITION_SDK_H

#ifndef SVTCLIENT_EXTERN_C
#ifdef __cplusplus
#define SVTCLIENT_EXTERN_C extern "C"
#else
#define SVTCLIENT_EXTERN_C
#endif
#endif

#define SVTCLIENT_API_EXPORTS /*__declspec(dllimport)*/

#ifndef SVTCLIENT_STDCALL
#if (defined WIN32 || defined _WIN32 || defined WIN64 || defined _WIN64) && (defined _MSC_VER)
#define SVTCLIENT_STDCALL __stdcall
#else
#define SVTCLIENT_STDCALL
#endif
#endif

#define SVTCLIENT_IMPL SVTCLIENT_EXTERN_C

#define SVTCLIENT_API(rettype) SVTCLIENT_EXTERN_C SVTCLIENT_API_EXPORTS rettype SVTCLIENT_STDCALL

#define FR_POINT_NUM 5

//函数返回值
enum GoNcFRErrorCode {
    GO_NC_FR_SUCCEED = 0,         //成功
    GO_NC_FR_NOT_INIT = 1,        //未初始化
    GO_NC_FR_UNKNOWN_ERROR = 2,   //未知错误
    GO_NC_FR_INVAILD_PARAM,       //输入参数有误
    GO_NC_FR_UNREGISTERED,        //人脸识别算法库未注册
    GO_NC_FR_ID_NOT_EXIST,        //ID不存在
    GO_NC_FR_OUTOF_RES,           //系统资源不足
    GO_NC_FR_ALIGN_FAILED,        //人脸对齐失败
    GO_NC_FR_DET_FAILED,		  //人脸检测失败
    GO_NC_FR_FEA_EXT_FAILED       //人脸特征提取失败

};

//浮点类型的点坐标
typedef struct _GoPoint2f//
{
    float fX;
    float fY;
}GoPoint2f;

//人脸姿态
typedef struct _FacePose{
    float fHorizontal_threshold;    //人脸水平偏转程度限制，范围是0到1，越大则限制越小，1为不过滤。
} FacePose;

//人脸检测参数
typedef struct _FaceDetectFilterParams {
    int nX;                         //人脸检测区域左上角横坐标
    int nY;                         //人脸检测区域左上角纵坐标
    int nWidth;                     //人脸检测区域宽度
    int nHeight;                    //人脸检测区域高度
    int nMin_size;                  //最小人脸
    int nMax_size;                  //最大人脸
    FacePose pose_thresold;		   //姿态估计阈值
    int nStream_type;               //流类型：0表示视频流(带跟踪id号)，1表示图片流
} FaceDetectFilterParams;

//人脸区域，实际坐标值
typedef struct _FaceTrackedRect {
    int nX;                                     //人脸区域左上角横坐标
    int nY;                                     //人脸区域左上角纵坐标
    int nWidth;                                 //人脸区域宽度
    int nHeight;                                //人脸区域高度
    int nID;                                    //人脸跟踪ID
    GoPoint2f face_points[FR_POINT_NUM];	   //人脸特征点集
} FaceTrackedRect;


#ifdef __cplusplus
extern "C" {
#endif
// 得到最近一次的错误码
SVTCLIENT_API(int)          GoNc_FaceRecognition_GetLastErrorCode(void);

// 初始化
SVTCLIENT_API(int)	        GoNc_FaceRecognition_Init();
// 反初始化
SVTCLIENT_API(int)	        GoNc_FaceRecognition_CleanUp(void);

// 创建人脸识别实例
SVTCLIENT_API(int)	        GoNc_FaceRecognition_Create(const char* szModel_path,long long *phHandle);
// 释放人脸识别实例
SVTCLIENT_API(int)          GoNc_FaceRecognition_Release(const long long hHandle);

//YUV-NV21转换为BGR图像
//todo
SVTCLIENT_API(int)          GoNc_FaceRecognition_Nv21Tbgr(const long long hHandle, unsigned char *szImgYuv, unsigned char *szImgBgr,int nWidth, int nHeight );

// 对一帧数据检测人脸位置
// hHandle		         入参：实例的句柄
// szImg_buf		     入参：bgr图像数据
// nWidth
// nHeight
// pDet_filter_params    入参：过滤参数，具体看上面结构体定义
// nNum_threads          入参：运行该算法调用的线程数量（在合理范围内，数值越大则cpu占用越高，速度越快）
// nImg_type             入参：图像数据的类型
// ppFace_rc_arr         出参：检测结果信息
// pnFace_count          出参：人脸个数
SVTCLIENT_API(int)          GoNc_FaceRecognition_FaceDetect(const long long hHandle, const unsigned char* szImg_buf, int nWidth, int nHeight,
                                                            const FaceDetectFilterParams* pDet_filter_params, int nNum_threads, FaceTrackedRect** ppFace_rc_arr, int* pnFace_count);

// 提取人脸特征
// hHandle		          入参：实例的句柄
// szImg_buf		      入参：bgr图像数据
// nWidth
// nHeight
// pFace_rc               入参：输入人脸信息
// nNum_threads           入参：运行该算法调用的线程数量（在合理范围内，数值越大则cpu占用越高，速度越快）
// ppFeature              出参：人脸特征
// pnFeature_len          出参：人脸特征长度
SVTCLIENT_API(int)          GoNc_FaceRecognition_GetFaceFeature(const long long hHandle, const unsigned char* szImg_buf, int nWidth, int nHeight,
                                                                const FaceTrackedRect* pFace_rc, int nNum_threads, char** ppFeature, int* pnFeature_len);

// 对2个人脸特征数据进行比对，得到相似度0-1
// hHandle		     入参：实例的句柄
// szFeature1         入参：取自GoNc_FaceRecognition_GetFaceFeature的特征向量
// npFeature_len1      入参：特征长度
// szFeature2         入参：取自GoNc_FaceRecognition_GetFaceFeature的特征向量
// npFeature_len2      入参：特征长度
// fpSimilarity        出参：相似度
SVTCLIENT_API(int)          GoNc_FaceRecognition_CompareFeature(const long long hHandle, const char* szFeature1, const int* npFeature_len1, const char* szFeature2, const int* npFeature_len2, float* fpSimilarity);

#ifdef __cplusplus
}
#endif

#endif //GXX_FACE_RECOGNITION_SDK_H