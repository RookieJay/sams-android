package com.gosuncn.facelib.bean;

/**
 * Copyright © 1997 - 2018 Gosuncn. All Rights Reserved.
 * Created by Michael on 10/9/2018.
 * Contact：2751358839@qq.com
 * Describe：
 */
public class FaceFeature {
    private byte[] faceVector;
    private int length;

    public FaceFeature(byte[] faceVector, int length) {
        this.faceVector = faceVector;
        this.length = length;
    }

    public byte[] getFaceVector() {
        return faceVector;
    }

    public void setFaceVector(byte[] faceVector) {
        this.faceVector = faceVector;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
