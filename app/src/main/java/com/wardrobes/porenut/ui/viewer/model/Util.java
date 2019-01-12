package com.wardrobes.porenut.ui.viewer.model;

import android.opengl.GLES20;
import android.util.Log;

import androidx.annotation.NonNull;

final class Util {

    static int compileProgram() {
        final String[] attributes = new String[]{"a_Position", "a_Normal"};
        int program = GLES20.glCreateProgram();
        GLES20.glAttachShader(program, loadShader(GLES20.GL_VERTEX_SHADER, MODEL_VERTEX));
        GLES20.glAttachShader(program, loadShader(GLES20.GL_FRAGMENT_SHADER, MODEL_LIGHT));
        for (int i = 0; i < attributes.length; i++) {
            GLES20.glBindAttribLocation(program, i, attributes[i]);
        }
        GLES20.glLinkProgram(program);
        return program;
    }

    private static int loadShader(int type, @NonNull String shaderCode) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        final int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
        if (compileStatus[0] == 0) {
            Log.e("loadShader", "Error compiling shader: " + GLES20.glGetShaderInfoLog(shader));
            GLES20.glDeleteShader(shader);
            throw new RuntimeException("Shader compilation failed.");
        }
        return shader;
    }

    private static final String MODEL_VERTEX = "precision mediump float;\n" +
            "\n" +
            "attribute vec4 a_Position;\n" +
            "attribute vec3 a_Normal;\n" +
            "varying vec3 v_Normal;\n" +
            "varying vec3 v_Position;\n" +
            "uniform mat4 u_MVP;\n" +
            "\n" +
            "void main() {\n" +
            "    v_Normal =  normalize(vec3(u_MVP * vec4(a_Normal, 0.0)));\n" +
            "    gl_Position = u_MVP * a_Position;\n" +
            "    v_Position = gl_Position.xyz / gl_Position.w;\n" +
            "}\n";

    private static final String MODEL_LIGHT = "precision mediump float;\n" +
            "\n" +
            "uniform vec3 u_LightPos;\n" +
            "uniform vec3 u_ambientColor;\n" +
            "uniform vec3 u_diffuseColor;\n" +
            "uniform vec3 u_specularColor;\n" +
            "const float specular_exp = 16.0;\n" +
            "varying vec3 v_Normal;\n" +
            "varying vec3 v_Position;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    vec3 lightPosNorm = normalize(u_LightPos);\n" +
            "    vec3 cameraDir = normalize(-v_Position);\n" +
            "    vec3 halfDir = normalize(lightPosNorm + cameraDir);\n" +
            "    float specular = pow(max(dot(halfDir, v_Normal), 0.0), specular_exp);\n" +
            "    float diffuse = max(dot(lightPosNorm, v_Normal), 0.0);\n" +
            "    gl_FragColor = vec4(u_ambientColor * (1.0 - diffuse) + u_diffuseColor * (diffuse - specular) + u_specularColor * specular, 1.0);\n" +
            "}\n";
}
