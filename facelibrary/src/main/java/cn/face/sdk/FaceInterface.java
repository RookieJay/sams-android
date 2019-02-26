package cn.face.sdk;



public interface FaceInterface {
	
	// 图像格式
	interface cw_img_form_t extends FaceInterface {
		int CW_IMAGE_GRAY8 = 0;
		int CW_IMAGE_BGR888 = 1;
		int CW_IMAGE_BGRA8888 = 2;
		int CW_IMAGE_RGB888 = 3;
		int CW_IMAGE_RGBA8888 = 4;
		int CW_IMAGE_YUV420P = 5;
		int CW_IMAGE_YV12 = 6;
		int CW_IMAGE_NV12 = 7;
		int CW_IMAGE_NV21 = 8;
		int CW_IMAGE_BINARY = 9;
	}

	// 图像旋转角度（逆时针）
	interface cw_img_angle_t extends FaceInterface {
		int CW_IMAGE_ANGLE_0 = 0;
		int CW_IMAGE_ANGLE_90 = 1;
		int CW_IMAGE_ANGLE_180 = 2;
		int CW_IMAGE_ANGLE_270 = 3;
	}
	
	// 图像镜像
	interface cw_img_mirror_t extends FaceInterface {
		int CW_IMAGE_MIRROR_NONE = 0;        // 不镜像  
		int CW_IMAGE_MIRROR_HOR = 1;         // 水平镜像
		int CW_IMAGE_MIRROR_VER = 2;         // 垂直镜像
		int CW_IMAGE_MIRROR_HV = 3;          // 垂直和水平镜像
	}

	// 检测开关选项
	interface cw_op_t extends FaceInterface {
		int CW_OP_DET     = 0;                  // (1 << 0) 进行人脸检测，并返回人脸矩形位置
		int CW_OP_TRACK   = 2;                  // (1 << 1)进行人脸跟踪，并返回人脸跟踪的ID
		int CW_OP_KEYPT   = 4;                  // (1 << 2)进行人脸关键点检测，并返回人脸上的关键点坐标信息
		int CW_OP_ALIGN   = 8;                  // (1 << 3)进行人脸图像对齐，并返回对齐人脸数据，用来提特征
		int CW_OP_QUALITY = 16;                 // (1 << 4)人脸质量分（质量分子项开关在配置文件中配置）
		int CW_OP_ALL     = 30;                 // （所有开关综合）总开关
	}
	
	// 特征句柄功能
	interface cw_recog_pattern_t extends FaceInterface {
		int CW_FEATURE_EXTRACT = 0;
		int CW_RECOGNITION = 1;
	}

	// 通用错误码
	interface cw_errcode_t extends FaceInterface {
		int CW_SDKLIT_OK = 0;                        // 成功
		
		int CW_UNKNOWN_ERR = 20000;                  // 未知错误
		int CW_DETECT_INIT_ERR = 20001;				 // 初始化人脸检测器失败:如加载模型失败等
		int CW_KEYPT_INIT_ERR = 20002;				 // 初始化关键点检测器失败：如加载模型失败等
		int CW_QUALITY_INIT_ERR = 20003;			 // 初始化跟踪器失败：如加载模型失败等
		
		int CW_DET_ERR = 20004;                      // 检测失败
		int CW_TRACK_ERR = 20005;                    // 跟踪失败
		int CW_KEYPT_ERR = 20006;                    // 提取关键点失败
		int CW_ALIGN_ERR = 20007;                    // 对齐人脸失败
		int CW_QUALITY_ERR = 20008;                  // 质量评估失败

		int CW_EMPTY_FRAME_ERR = 20009;              // 空图像
		int CW_UNSUPPORT_FORMAT_ERR = 20010;         // 图像格式不支持
		int CW_ROI_ERR = 20011;                      // ROI设置失败
		int CW_UNINITIALIZED_ERR = 20012;            // 尚未初始化
		int CW_MINMAX_ERR = 20013;                   // 最小最大人脸设置失败
		int CW_OUTOF_RANGE_ERR = 20014;              // 数据范围错误	
		int CW_UNAUTHORIZED_ERR = 20015;             // 未授权	
		int CW_METHOD_UNAVAILABLE = 20016;	         // 方法无效
		int CW_PARAM_INVALID = 20017;                // 参数无效
		int CW_BUFFER_EMPTY = 20018;				 // 缓冲区空
		
		int CW_FILE_UNAVAILABLE = 20019;             // 文件不存在：如加载的模型不存在等.
		int CW_DEVICE_UNAVAILABLE = 20020;   	     // 设备不存在：如GPU等.
		int CW_DEVICE_ID_UNAVAILABLE = 20021; 		 // 设备id不存在：如GPU id等
		int CW_EXCEEDMAXHANDLE_ERR = 20022;          // 超过授权最大句柄数
			
		int CW_RECOG_FEATURE_MODEL_ERR = 20023;      // 加载特征识别模型失败
		int CW_RECOG_ALIGNEDFACE_ERR = 20024;        // 对齐图片数据错误
		int CW_RECOG_MALLOCMEMORY_ERR = 20025;       // 预分配特征空间不足
		int CW_RECOG_FILEDDATA_ERR = 20026;          // 用于注册的特征数据错误
		int CW_RECOG_PROBEDATA_ERR = 20027;          // 用于检索的特征数据错误
		int CW_RECOG_EXCEEDMAXFEASPEED = 20028;      // 超过授权最大提特征速度
		int CW_RECOG_EXCEEDMAXCOMSPEED = 20029;      // 超过授权最大比对速度	
		int CW_ATTRI_AGEGENDER_MODEL_ERR = 20030;    // 加载年龄性别模型失败 
		int CW_ATTRI_EVAL_AGEGENDER_ERR = 20031;     // 年龄性别识别失败
		
		int CW_SERVICE_REGISTID_EXIST = 20032;       // 注册人脸id已存在
		int CW_SERVICE_UNREGISTID_NOEXIST = 20033;   // 反注册人脸的id不存在
		int CW_SERVICE_RECOG_GROUP_EMPTY = 20034;    // 检索的底库为空

		int CW_LICENCE_ACCOUNT_TIMEOUT = 20035;      // 安卓网络授权账号过期
		int CW_LICENCE_HTTP_ERROR = 20036;           // 安卓网络授权http错误
		int CW_LICENCE_MALLOCMEMORY_ERR = 20037;     // 安卓网络授权内存分配不足
		
		int CW_INSTALL_KEY_ENCRYPT_ERR = 20038;      // 加密失败
		int CW_INSTALL_KEY_EXIST_ERR = 20039;        // 网络授权错误：已存在正式授权
		int CW_INSTALL_KEY_GETC2V_ERR = 20040;       // 网络授权错误：获取C2V设备文件错误（是否安装驱动）
		int CW_INSTALL_KEY_GETV2C_ERR = 20041;       // 网络授权错误：获取V2C授权文件错误
		int CW_INSTALL_KEY_INSTALL_ERR = 20042;      // 网络授权错误：安装V2C授权文件错误（在当前目录找到V2C授权文件，使用工具进行安装）
	}
	
	// 质量分检测错误码
	interface cw_quality_errcode_t extends FaceInterface {
		int CW_QUALITY_OK            = 0;				// 质量分数据有效
		int	CW_QUALITY_NO_DATA       = 20150;		    // 质量分数据无效，原因：尚未检测
		int	CW_QUALITY_ERROR_UNKNOWN = 20151;           // 未知错误
	}
	
	// 红外活体检测摄像头数目类型
	interface cw_nirliv_camera_type_t extends FaceInterface {	
		int CW_NIR_LIV_DET_MONOCULAR = 0;   // 红外单目摄像头活体检测，只使用红外摄像头检测活体
		int CW_NIR_LIV_DET_BINOCULAR = 1;   // 红外双目摄像头活体检测，使用双目摄像头检测活体
	}
	
	// 红外活体检测模式：距离最近人脸检测/多人检测
	interface cw_nirliv_det_type_t extends FaceInterface {		
		int CW_NIR_LIV_MODE_NEARST_FACE = 0;		  // 对图中的最近进行人脸活体检测
		int CW_NIR_LIV_MODE_MULTI_FACES = 1;	      // 对图中的所有符合要求的人脸进行活体检测
	}
	
	// 红外活体检测结果返回值
	interface cw_nirliv_det_rst_t extends FaceInterface {
		int CW_NIR_LIV_DET_LIVE = 0;				// 以阈值0.5判断为活体
		int CW_NIR_LIV_DET_UNLIVE = 1;				// 以阈值0.5判断为非活体
		int CW_NIR_LIV_DET_DIST_FAILED = 2;			// 人脸距离检测未通过
		int CW_NIR_LIV_DET_SKIN_FAILED = 3;			// 人脸肤色检测未通过
		int CW_NIR_LIV_DET_NO_PAIR_FACE = 4;		// 未匹配到人脸
		int CW_NIR_LIV_DET_IS_INIT = 5;				// 红外活体检测结果初始值
	}

	// 红外活体检测错误码
	interface cw_nirliveness_err_t extends FaceInterface {
		int CW_NIRLIV_OK = 0;                          // 红外检测成功
			
		int CW_NIRLIV_ERR_CREATE_HANDLE = 26000;       // 创建红外活体检测句柄失败
		int CW_NIRLIV_ERR_FREE_HANDLE = 26001;		   // 释放红外活体检测句柄失败
		int CW_NIRLIV_ERR_FACE_PAIR = 26002;		   // 人脸匹配初始化失败
		int CW_NIRLIV_ERR_CREAT_LOG_DIR = 26003;	   // 创建日志路径失败	
		int CW_NIRLIV_ERR_MODEL_NOTEXIST = 26004;      // 输入模型不存在
		int CW_NIRLIV_ERR_MODEL_FAILED = 26005;        // 输入模型初始化失败
		int CW_NIRLIV_ERR_INPUT_UNINIT = 26006;        // 输入未初始化
		int CW_NIRLIV_ERR_NIR_NO_FACE = 26007;         // 输入红外图片没有人脸
		int CW_NIRLIV_ERR_VIS_NO_FACE = 26008;         // 输入可见光图片没有人脸
		int CW_NIRLIV_ERR_NO_PAIR_FACE = 26009;        // 输入可见光和红外图片人脸未能匹配
		int CW_NIRLIV_ERR_PUSH_DATA = 26010;           // 输入数据失败
		int CW_NIRLIV_ERR_NUM_LANDMARKS = 26011;       // 输入可见光图片和红外图片关键点个数不等
		int CW_NIRLIV_ERR_NO_LANDMARKS = 26012;        // 输入红外图片没有人脸关键点
		int CW_NIRLIV_ERR_INPUT_IMAGE = 26013;         // 输入红外图片或者可见光图片不是多通道
		int CW_NIRLIV_ERR_UNAUTHORIZED = 26014;        // 没有license（未授权）
		int CW_NIRLIV_ERR_FACE_NUM_ERR = 26015;        // 未开启人脸匹配开关时，可见光或红外图像人脸大于1
		int CW_NIRLIV_ERR_CAM_UNCW = 26016;	           // 非云从定制摄像头
		int CW_NIRLIV_ERR_UNKNOWN = 26017;             // 未知结果
		int CW_NIRLIV_ERR_MAXHANDLE = 26018;		   // 超过最大红外活体最大授权句柄数
		int CW_NIRLIV_ERR_NIRIMAGE = 26019;		       // 输入红外图片数据错误
		int CW_NIRLIV_ERR_VISIMAGE = 26020;			   // 输入可见光图片数据错误
	}
	
	// 红外双面摄像头操作错误码
	interface cw_camera_errcode_t extends FaceInterface {
		int CW_HWSDK_ERR_OK = 0;                         // 摄像头操作成功
		
		int CW_HWSDK_ERR_OPEN_VIS_CAMERA = 30000;        // 可见光摄像头打开失败
		int CW_HWSDK_ERR_OPEN_NIS_CAMERA = 30001;	     // 红外摄像头打开失败
		int CW_HWSDK_ERR_GRAB_VIS_CAMERA = 30002;		 // 可见光摄像头抓取一帧失败
		int CW_HWSDK_ERR_GRAB_NIS_CAMERA = 30003;		 // 红外摄像头抓取一帧失败	
		int CW_HWSDK_ERR_VIS_MEMORY = 30004;             // 获取可见光图片数据时内存分配不足
		int CW_HWSDK_ERR_NIS_MEMORY = 30005;             // 获取红外图片数据时内存分配不足
		int HWSDK_ERR_CAMERA_OPEND = 30006;              // 摄像头已打开
	}
	
	// 获取可见光或红外图片帧yuv420p数据的数据格式
	interface cw_camera_fmt_t extends FaceInterface {
		int CW_HW_IMAGE_FMT_NONE = 0;                 // 不获取数据，主要用于红外摄像头，红外视频一般不显示，那么没必要获取
		int CW_HW_IMAGE_FMT_YUV420P = 1;              // 获取yuv420p数据，主要用于获取图片帧后显示在控件上
	}
	
	// 安装授权标志	
	interface cw_install_key_flag_t extends FaceInterface {			
		int CW_RETERROR_IFEXIST = 0;	   //  如果存在正式授权，则不继续安装，返回错误码（建议使用此标志，防止多次授权）
		int CW_INSTALLSTILL_IFEXIST = 1;   //  不管是否存在正式授权，仍然安装新授权
	}
		
	// 安装授权时保存文件标志		
	interface cw_save_key_flag_t extends FaceInterface {		
		int CW_SAVE_ALL = 0;	       //  授权文件和设备文件均保存，文件保存在当前程序运行目录
		int CW_SAVE_ONLY_V2C = 1;      //  仅保存V2C授权文件
		int CW_SAVE_ONLY_C2V = 2;      //  仅保存C2V设备文件
		int CW_SAVE_NONE = 3;          //  都不保存，如果机器无文件保存权限，建议使用该参数，防止出错
	}
	
}
