package pers.zjc.sams.module.face;

import android.graphics.Bitmap;
import android.util.Log;

import com.arcsoft.facerecognition.AFR_FSDKEngine;
import com.arcsoft.facerecognition.AFR_FSDKError;
import com.arcsoft.facerecognition.AFR_FSDKFace;
import com.arcsoft.facerecognition.AFR_FSDKVersion;
import com.guo.android_extend.java.ExtInputStream;
import com.guo.android_extend.java.ExtOutputStream;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by gqj3375 on 2017/7/11.
 */

public class FaceDB {
	private final String TAG = this.getClass().toString();

	//APP_ID
	public static String appid = "95xHCtagH13zkpnxWAfFuLq2q62syusK2xpX7fuB3wtL";
	public static String ft_key = "6a5yPY9tfq1A1WkzTgfzXZWJnqBQwjELvJmrVoVtdPxB";
	public static String fd_key = "6a5yPY9tfq1A1WkzTgfzXZWRxESeBo1abJNrUgCxPnNr";
	public static String fr_key = "6a5yPY9tfq1A1WkzTgfzXZWvbqVLbAxETS4ntnNYZ1G7";
	public static String age_key = "6a5yPY9tfq1A1WkzTgfzXZXAve1ctpS1rggMC59EuSiY";
	public static String gender_key = "6a5yPY9tfq1A1WkzTgfzXZXJ63GqFV1AkibiZkh59R4R";

	String mDBPath;
	List<FaceRegist> mRegister;
	AFR_FSDKEngine mFREngine;
	AFR_FSDKVersion mFRVersion;
	boolean mUpgrade;

	class FaceRegist {
		String mName;
		Map<String, AFR_FSDKFace> mFaceList;

		public FaceRegist(String name) {
			mName = name;
			mFaceList = new LinkedHashMap<>();
		}
	}

	public FaceDB(String path) {
		mDBPath = path;
		mRegister = new ArrayList<>();
		mFRVersion = new AFR_FSDKVersion();
		mUpgrade = false;
		mFREngine = new AFR_FSDKEngine();
		AFR_FSDKError error = mFREngine.AFR_FSDK_InitialEngine(FaceDB.appid, FaceDB.fr_key);
		if (error.getCode() != AFR_FSDKError.MOK) {
			Log.e(TAG, "AFR_FSDK_InitialEngine fail! error code :" + error.getCode());
		} else {
			mFREngine.AFR_FSDK_GetVersion(mFRVersion);
			Log.d(TAG, "AFR_FSDK_GetVersion=" + mFRVersion.toString());
		}
	}

	public void destroy() {
		if (mFREngine != null) {
			mFREngine.AFR_FSDK_UninitialEngine();
		}
	}

	private boolean saveInfo() {
		try {
			FileOutputStream fs = new FileOutputStream(mDBPath + "/face.txt");
			ExtOutputStream bos = new ExtOutputStream(fs);
			bos.writeString(mFRVersion.toString() + "," + mFRVersion.getFeatureLevel());
			bos.close();
			fs.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean loadInfo() {
		if (!mRegister.isEmpty()) {
			return false;
		}
		try {
			FileInputStream fs = new FileInputStream(mDBPath + "/face.txt");
			ExtInputStream bos = new ExtInputStream(fs);
			//load version
			String version_saved = bos.readString();
			if (version_saved.equals(mFRVersion.toString() + "," + mFRVersion.getFeatureLevel())) {
				mUpgrade = true;
			}
			//load all regist name.
			if (version_saved != null) {
				for (String name = bos.readString(); name != null; name = bos.readString()){
					if (new File(mDBPath + "/" + name + ".data").exists()) {
						mRegister.add(new FaceRegist(new String(name)));
					}
				}
			}
			bos.close();
			fs.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean loadFaces(){
		if (loadInfo()) {
			try {
				for (FaceRegist face : mRegister) {
					Log.d(TAG, "load name:" + face.mName + "'s face feature data.");
					FileInputStream fs = new FileInputStream(mDBPath + "/" + face.mName + ".data");
					ExtInputStream bos = new ExtInputStream(fs);
					AFR_FSDKFace afr = null;
					do {
						if (afr != null) {
							if (mUpgrade) {
								//upgrade data.
							}
							String keyFile = bos.readString();
							face.mFaceList.put(keyFile, afr);
						}
						afr = new AFR_FSDKFace();
					} while (bos.readBytes(afr.getFeatureData()));
					bos.close();
					fs.close();
					Log.d(TAG, "load name: size = " + face.mFaceList.size());
				}
				return true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public	void addFace(String name, AFR_FSDKFace face, Bitmap faceicon) {
		try {
            //upload to server
			final String keyPath = mDBPath + "/" + System.nanoTime() + ".jpg";
			File keyFile = new File(keyPath);
			OutputStream stream = new FileOutputStream(keyFile);
			if (faceicon.compress(Bitmap.CompressFormat.JPEG, 80, stream)) {
				Log.d(TAG, "saved face bitmap to jpg!");
			}
			stream.close();
			new Thread(new Runnable() {
				@Override
				public void run() {
					uploadLogFile(keyPath);
				}
			}).start();

			//check if already registered.
			boolean add = true;
			for (FaceRegist frface : mRegister) {
				if (frface.mName.equals(name)) {
					frface.mFaceList.put(keyPath, face);
					add = false;
					break;
				}
			}

			if (add) { // not registered.
				FaceRegist frface = new FaceRegist(name);
				frface.mFaceList.put(keyPath, face);
				mRegister.add(frface);
			}

			if (saveInfo()) {
				//update all names
				FileOutputStream fs = new FileOutputStream(mDBPath + "/face.txt", true);
				ExtOutputStream bos = new ExtOutputStream(fs);
				for (FaceRegist frface : mRegister) {
					bos.writeString(frface.mName);
				}
				bos.close();
				fs.close();

				//save new feature
				fs = new FileOutputStream(mDBPath + "/" + name + ".data", true);
				bos = new ExtOutputStream(fs);
				bos.writeBytes(face.getFeatureData());
				bos.writeString(keyPath);
				bos.close();
				fs.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean delete(String name) {
		try {
			//check if already registered.
			boolean find = false;
			for (FaceRegist frface : mRegister) {
				if (frface.mName.equals(name)) {
					File delfile = new File(mDBPath + "/" + name + ".data");
					if (delfile.exists()) {
						delfile.delete();
					}
					mRegister.remove(frface);
					find = true;
					break;
				}
			}

			if (find) {
				if (saveInfo()) {
					//update all names
					FileOutputStream fs = new FileOutputStream(mDBPath + "/face.txt", true);
					ExtOutputStream bos = new ExtOutputStream(fs);
					for (FaceRegist frface : mRegister) {
						bos.writeString(frface.mName);
					}
					bos.close();
					fs.close();
				}
			}
			return find;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean upgrade() {
		return false;
	}

	/**
	 * 上传文件到服务器
	 * @param oldFilePath       本地文件路径
	 */
	public static void uploadLogFile(String oldFilePath){
//		try {
//			String BOUNDARY = "---------------------------7d4a6d158c9"; // 分隔符
//			URL url = new URL("http://192.168.1.4:8080/mall/file/faceUpload");
//			HttpURLConnection con = (HttpURLConnection)url.openConnection();
//
//			// 允许Input、Output，不使用Cache
//			con.setDoInput(true);
//			con.setDoOutput(true);
//			con.setUseCaches(false);
//
//			con.setConnectTimeout(50000);
//			con.setReadTimeout(50000);
//			// 设置传送的method=POST
//			con.setRequestMethod("POST");
//			//在一次TCP连接中可以持续发送多份数据而不会断开连接
//			con.setRequestProperty("Connection", "Keep-Alive");
//			//设置编码
//			con.setRequestProperty("Charset", "UTF-8");
//			//text/plain能上传纯文本文件的编码格式
//			con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + BOUNDARY);
//
//			// 设置DataOutputStream
//			DataOutputStream ds = new DataOutputStream(con.getOutputStream());
//
//			// 取得文件的FileInputStream
//			FileInputStream fStream = new FileInputStream(oldFilePath);
//			// 设置每次写入1024bytes
//			int bufferSize = 1024;
//			byte[] buffer = new byte[bufferSize];
//
//			int length = -1;
//			// 从文件读取数据至缓冲区
//			while ((length = fStream.read(buffer)) != -1) {
//				// 将资料写入DataOutputStream中
//				ds.write(buffer, 0, length);
//				ds.writeChars("file");
//			}
//			ds.flush();
//			fStream.close();
//			ds.close();
//			if(con.getResponseCode() == 200){
//				Log.d("FaceDB","文件上传成功！上传文件为：" + oldFilePath);
//			} else {
//				Log.d("code", String.valueOf(con.getResponseCode()));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			Log.d("FaceDB","文件上传失败！上传文件为：" + oldFilePath);
//			Log.d("FaceDB", "报错信息toString：" + e.toString());
//		}

        OkHttpClient client = new OkHttpClient();

		//一种：参数请求体
		FormBody paramsBody=new FormBody.Builder()
				.add("stuId","10010001")
				.build();

		//二种：文件请求体  application/octet-stream    /data/data/com.example.company/files/plan/plans.xml
		MediaType type=MediaType.parse("application/octet-stream");//"text/xml;charset=utf-8"
		File file=new File(oldFilePath);
		RequestBody fileBody=RequestBody.create(type,file);


		//三种：混合参数和文件请求
		RequestBody multipartBody = new MultipartBody.Builder()
				.setType(MultipartBody.ALTERNATIVE)
				.addPart(Headers.of(
						"Content-Disposition",
						"form-data; name=\"params\"")
						,paramsBody)
				.addPart(Headers.of(
						"Content-Disposition",
						"form-data; name=\"file\"; filename=\"plans.xml\"")
						, fileBody)
                .build();

        Request request=new Request.Builder().url("http://localhost:8080/sams/api/mobile/face/upload")
                                             .addHeader("User-Agent","android")
                                             .header("Content-Type","text/html; charset=utf-8;")
                                             .post(fileBody)//传参数、文件或者混合，改一下就行请求体就行
                                             .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Log.i("xxx","1、连接的消息"+response.message());
                if(response.isSuccessful()){
                    String body = response.body() != null ? response.body().string() : null;
                    Log.i("xxx","2、连接成功获取的内容"+ body);
                }
            }
        });
	}
}
