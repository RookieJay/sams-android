package com.gosuncn.facelib.bean;

public class ImageFrame   {

    private byte[] nv21;
    /**
     * 图片宽度
     */
    private int width;
    /**
     * 图片高度
     */
    private int height;

    private int rotation;

    public void setNv21(byte[] nv21) {
        this.nv21 = nv21;
    }

    public byte[] getNv21() {
        return nv21;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }


    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public int getRotation() {
        return rotation;
    }
    public  void setData(byte[] nv21,int width,int height){
        this.nv21=nv21;
        this.width=width;
        this.height=height;
    }
}
