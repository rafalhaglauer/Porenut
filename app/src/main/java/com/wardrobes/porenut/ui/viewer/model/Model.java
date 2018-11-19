package com.wardrobes.porenut.ui.viewer.model;

import android.opengl.Matrix;

import androidx.annotation.NonNull;

public abstract class Model {
    float centerMassX;
    float centerMassY;
    float centerMassZ;
    float floorOffset;

    int glProgram = -1;
    float[] modelMatrix = new float[16];
    float[] mvMatrix = new float[16];
    float[] mvpMatrix = new float[16];

    private float maxX;
    private float maxY;
    private float maxZ;
    private float minX;
    float minY;
    private float minZ;

    Model() {
        maxX = Float.MIN_VALUE;
        maxY = Float.MIN_VALUE;
        maxZ = Float.MIN_VALUE;
        minX = Float.MAX_VALUE;
        minY = Float.MAX_VALUE;
        minZ = Float.MAX_VALUE;
    }

    public void init(float boundSize) {
        initModelMatrix(boundSize);
    }

    protected void initModelMatrix(float boundSize) {
        initModelMatrix(boundSize, 0.0f, 0.0f, 0.0f);
    }

    void initModelMatrix(float boundSize, float rotateX, float rotateY, float rotateZ) {
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.rotateM(modelMatrix, 0, rotateX, 1.0f, 0.0f, 0.0f);
        Matrix.rotateM(modelMatrix, 0, rotateY, 0.0f, 1.0f, 0.0f);
        Matrix.rotateM(modelMatrix, 0, rotateZ, 0.0f, 0.0f, 1.0f);
        scaleModelMatrixToBounds(boundSize);
        Matrix.translateM(modelMatrix, 0, -centerMassX, -centerMassY, -centerMassZ);
    }

    abstract public void draw(float[] viewMatrix, float[] projectionMatrix, @NonNull Light light);

    void adjustMaxMin(float x, float y, float z) {
        if (x > maxX) {
            maxX = x;
        }
        if (y > maxY) {
            maxY = y;
        }
        if (z > maxZ) {
            maxZ = z;
        }
        if (x < minX) {
            minX = x;
        }
        if (y < minY) {
            minY = y;
        }
        if (z < minZ) {
            minZ = z;
        }
    }

    float getBoundScale(float boundSize) {
        float scaleX = (maxX - minX) / boundSize;
        float scaleY = (maxY - minY) / boundSize;
        float scaleZ = (maxZ - minZ) / boundSize;
        float scale = scaleX;
        if (scaleY > scale) {
            scale = scaleY;
        }
        if (scaleZ > scale) {
            scale = scaleZ;
        }
        return scale;
    }

    private void scaleModelMatrixToBounds(float boundSize) {
        float scale = getBoundScale(boundSize);
        if (scale != 0f) {
            scale = 1f / scale;
            Matrix.scaleM(modelMatrix, 0, scale, scale, scale);
        }
    }
}
