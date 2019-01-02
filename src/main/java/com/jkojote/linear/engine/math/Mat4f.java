package com.jkojote.linear.engine.math;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Arrays;

import static org.lwjgl.opengl.GL11.glOrtho;

/**
 * Represents 4x4 matrix.
 */
public final class Mat4f {

    private float[] matrix;

    public Mat4f() { matrix = new float[16]; }

    public Mat4f(float[] matrix) {
        if (matrix.length != 16)
            throw new IllegalArgumentException("length of matrix must be equal to 16");
        this.matrix = matrix;
    }

    /**
     * @return identity matrix
     */
    public static Mat4f identity() {
        float[] matrix = {
            1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1
        };
        return new Mat4f(matrix);
    }

    public static Mat4f ortho(float l, float r, float b, float t, float n, float f) {
        float[] matrix = {
            2 / (r - l), 0, 0, -(r + l) / (r - l),
            0, 2 / (t - b), 0, -(t + b) / (t - b),
            0, 0, -2 / (f - n), -(f + n) / (f - n),
            0, 0, 0, 1
        };
        return new Mat4f(matrix);
    }

    /**
     * Get value in {@code i}-th row and {@code j}-th column
     * @param i row number
     * @param j column number
     * @return value in i-th row and j-th column
     */
    public float get(int i, int j) {
        if (i < 0 || i > 3)
            throw new IllegalArgumentException("i must be in range [0;3]");
        if (j < 0 || j > 3)
            throw new IllegalArgumentException("j must be in range [0;3]");
        return matrix[i * 4 + j];
    }

    /**
     * Set value in {@code i}-th row and {@code j}-th column
     * @param i row number
     * @param j column number
     * @return value in i-th row and j-th column
     */
    public void set(int i, int j, float value) {
        if (i < 0 || i > 3)
            throw new IllegalArgumentException("i must be in range [0;3]");
        if (j < 0 || j > 3)
            throw new IllegalArgumentException("j must be in range [0;3]");
        matrix[i * 4 + j] = value;
    }

    /**
     * Copies this matrix
     * @return new matrix that is a copy of this matrix
     */
    public Mat4f copy() {
        return new Mat4f(Arrays.copyOf(matrix, 16));
    }

    /**
     * Performs scalar multiplication on this matrix
     * @param scalar scalar value
     * @return this matrix
     */
    public Mat4f scalar(float scalar) {
        for (int i = 0; i < 16; i++)
            matrix[i] *= scalar;
        return this;
    }

    /**
     * Performs matrix addition
     * @param other matrix to be added
     * @return this matrix
     */
    public Mat4f add(Mat4f other) {
        for (int i = 0; i < 16; i++)
            matrix[i] += other.matrix[i];
        return this;
    }

    /**
     * Performs matrix multiplication
     * @param other matrix to be multiplied by
     * @return new matrix that is the result of multiplication
     */
    public Mat4f mult(Mat4f other) {
        Mat4f res = new Mat4f();
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                for (int k = 0; k < 4; k++)
                    res.matrix[i * 4 + j] += matrix[i * 4 + k] * other.matrix[k * 4 + j];
        return res;
    }

    /**
     * Calculate product of a matrix and a vector
     * @param vec vector to be multiplied
     * @return new vector that is the result of the calculation
     */
    public Vec3f mult(Vec3f vec) {
        Vec3f res = new Vec3f();
        res.x = matrix[0] * vec.x + matrix[1] * vec.y + matrix[2] * vec.z + matrix[3];
        res.y = matrix[4] * vec.x + matrix[5] * vec.y + matrix[6] * vec.z + matrix[7];
        res.z = matrix[8] * vec.x + matrix[9] * vec.y + matrix[10] * vec.z + matrix[11];
        return res;
    }

    /**
     * Converts this matrix into a buffer
     * @return buffer
     */
    public FloatBuffer toBuffer() {
        FloatBuffer result = ByteBuffer.allocateDirect(16 << 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
        result.put(matrix).flip();
        return result;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Mat4f that = (Mat4f) object;
        return Arrays.equals(matrix, that.matrix);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(matrix);
    }
}
