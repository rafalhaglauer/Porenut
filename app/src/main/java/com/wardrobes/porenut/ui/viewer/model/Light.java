package com.wardrobes.porenut.ui.viewer.model;

import android.opengl.Matrix;

import androidx.annotation.NonNull;

public class Light {

    @NonNull
    private float[] lightPosInWorldSpace;
    private final float[] lightPosInEyeSpace = new float[4];
    private float[] ambientColor = new float[]{0.1f, 0.1f, 0.4f};
    private float[] diffuseColor = new float[]{1.0f, 1.0f, 1.0f};
    private float[] specularColor = new float[]{1.0f, 1.0f, 1.0f};

    public Light(@NonNull float[] position) {
        this.lightPosInWorldSpace = position;
    }

    public void setPosition(@NonNull float[] position) {
        this.lightPosInWorldSpace = position;
    }

    public float[] getAmbientColor() {
        return ambientColor;
    }

    public float[] getDiffuseColor() {
        return diffuseColor;
    }

    public float[] getSpecularColor() {
        return specularColor;
    }

    public void applyViewMatrix(@NonNull float[] viewMatrix) {
        Matrix.multiplyMV(lightPosInEyeSpace, 0, viewMatrix, 0, lightPosInWorldSpace, 0);
    }

    public float[] getPositionInEyeSpace() {
        return lightPosInEyeSpace;
    }

}
