package com.threehalf.scanner.common;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapUtils {

	public static Bitmap decodeSampledBitmapFromResource(Resources res,
			int resId, int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}

	public static Bitmap getCompressedBitmap(String path) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		options.inSampleSize = calculateInSampleSize(options, 480, 800);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(path, options);
	}

	public static Bitmap getBitmap(String path,int reqWidth,int reqHeight){
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path,options);
		options.inSampleSize = calculateSampleSize(options,reqWidth,reqHeight);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(path,options);
	}
	private static int calculateSampleSize(BitmapFactory.Options options,int reqWidth,int reqHeight){
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if(height > reqHeight || width > reqWidth){
			final int heightRatio = Math.round((float) height/(float) reqHeight);
			final int widthRatio = Math.round((float) width/(float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}

	// untested function
	public static byte[] getNV21(int inputWidth, int inputHeight, Bitmap scaled) {

		int[] argb = new int[inputWidth * inputHeight];

		scaled.getPixels(argb, 0, inputWidth, 0, 0, inputWidth, inputHeight);

		//解决下面数组越界问题
		byte[] yuv = new byte[inputHeight * inputWidth + 2 * (int) Math.ceil(inputHeight / 2.0) * (int) Math.ceil(inputWidth / 2.0)];//inputWidth * inputHeight * 3 / 2
		encodeYUV420SP(yuv, argb, inputWidth, inputHeight);

		scaled.recycle();

		return yuv;
	}

	/**
	 * @param yuv420sp
	 * @param argb
	 * @param width
	 * @param height   TODO:
	 *                 encodeYUV420SP throws ArrayIndexOutOfRange Exception when inputWidth or inputHeight is an odd number.
	 *                 Declaring the yuv array as
	 *                 byte [] yuv = new byte[inputHeight * inputWidth + 2 * (int) Math.ceil(inputHeight/2.0) *(int) Math.ceil(inputWidth/2.0)];
	 *                 solved the issue
	 * @// FIXME: 10/8/2018 java.lang.ArrayIndexOutOfBoundsException: length=954616; index=954616
	 */
	public static void encodeYUV420SP(byte[] yuv420sp, int[] argb, int width, int height) {
		final int frameSize = width * height;

		int yIndex = 0;
		int uvIndex = frameSize;

		int a, R, G, B, Y, U, V;
		int index = 0;
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {

				a = (argb[index] & 0xff000000) >> 24; // a is not used obviously
				R = (argb[index] & 0xff0000) >> 16;
				G = (argb[index] & 0xff00) >> 8;
				B = (argb[index] & 0xff) >> 0;

				// well known RGB to YUV algorithm
				Y = ((66 * R + 129 * G + 25 * B + 128) >> 8) + 16;
				U = ((-38 * R - 74 * G + 112 * B + 128) >> 8) + 128;
				V = ((112 * R - 94 * G - 18 * B + 128) >> 8) + 128;

				// NV21 has a plane of Y and interleaved planes of VU each sampled by a factor of 2
				//    meaning for every 4 Y pixels there are 1 V and 1 U.  Note the sampling is every other
				//    pixel AND every other scanline.
				yuv420sp[yIndex++] = (byte) ((Y < 0) ? 0 : ((Y > 255) ? 255 : Y));
				if (j % 2 == 0 && index % 2 == 0) {
					yuv420sp[uvIndex++] = (byte) ((V < 0) ? 0 : ((V > 255) ? 255 : V));
					yuv420sp[uvIndex++] = (byte) ((U < 0) ? 0 : ((U > 255) ? 255 : U));
				}

				index++;
			}
		}
	}

}
