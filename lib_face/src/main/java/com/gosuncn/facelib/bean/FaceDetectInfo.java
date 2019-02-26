package com.gosuncn.facelib.bean;

import java.util.List;

/**
 * Copyright © 1997 - 2018 Gosuncn. All Rights Reserved.
 * Created by Michael on 10/9/2018.
 * Contact：2751358839@qq.com
 * Describe：
 */
public class FaceDetectInfo {
    private int faceCount;
    private List<FaceTrackedRect> faceTrackedRects;

    public int getFaceCount() {
        return faceCount;
    }

    public void setFaceCount(int faceCount) {
        this.faceCount = faceCount;
    }

    public List<FaceTrackedRect> getFaceTrackedRects() {
        return faceTrackedRects;
    }

    public void setFaceTrackedRects(List<FaceTrackedRect> faceTrackedRects) {
        this.faceTrackedRects = faceTrackedRects;
    }

    @Override
    public String toString() {
        return "FaceDetectInfo{" +
                "faceCount=" + faceCount +
                ", faceTrackedRects=" + faceTrackedRects +
                '}';
    }

    public static class FaceTrackedRect {
        private int nX;                                     //人脸区域左上角横坐标
        private int nY;                                     //人脸区域左上角纵坐标
        private int nWidth;                                 //人脸区域宽度
        private int nHeight;                                //人脸区域高度
        private int nID;                                    //人脸跟踪ID

        private List<GoPoint2f> face_points;   //  //人脸特征点集

        public int getnX() {
            return nX;
        }

        public void setnX(int nX) {
            this.nX = nX;
        }

        public int getnY() {
            return nY;
        }

        public void setnY(int nY) {
            this.nY = nY;
        }

        public int getnWidth() {
            return nWidth;
        }

        public void setnWidth(int nWidth) {
            this.nWidth = nWidth;
        }

        public int getnHeight() {
            return nHeight;
        }

        public void setnHeight(int nHeight) {
            this.nHeight = nHeight;
        }

        public int getnID() {
            return nID;
        }

        public void setnID(int nID) {
            this.nID = nID;
        }

        public List<GoPoint2f> getFace_points() {
            return face_points;
        }

        public void setFace_points(List<GoPoint2f> face_points) {
            this.face_points = face_points;
        }

        @Override
        public String toString() {
            return "FaceTrackedRect{" +
                    "nX=" + nX +
                    ", nY=" + nY +
                    ", nWidth=" + nWidth +
                    ", nHeight=" + nHeight +
                    ", nID=" + nID +
                    ", face_points=" + face_points +
                    '}';
        }
    }

    public static class GoPoint2f {
        float fX;
        float fY;

        public float getfX() {
            return fX;
        }

        public void setfX(float fX) {
            this.fX = fX;
        }

        public float getfY() {
            return fY;
        }

        public void setfY(float fY) {
            this.fY = fY;
        }

        @Override
        public String toString() {
            return "GoPoint2f{" +
                    "fX=" + fX +
                    ", fY=" + fY +
                    '}';
        }
    }
}
