package com.jkojote.linear.engine.math;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Arrays;


/**
 * Represents 4x4 matrix that is stored in <b>row-major order</b>
 */
public final class Mat4f {

    private static final float PI_180 = (float) Math.PI / 180;

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
            2 / (r - l), 0, 0, 0,
            0, 2 / (t - b), 0, 0,
            0, 0, -2 / (f - n), 0,
            -(r + l) / (r - l), -(t + b) / (t - b), -(f + n) / (f - n), 1
        };
        return new Mat4f(matrix);
    }

    public static Mat4f translation(Vec3f vec) {
        Mat4f translation = Mat4f.identity();
        translation.matrix[3] = vec.x;
        translation.matrix[7] = vec.y;
        translation.matrix[11] = vec.z;
        return translation;
    }

    public static Mat4f scale(Vec3f vec) {
        Mat4f scale = Mat4f.identity();
        scale.matrix[0] = vec.x;
        scale.matrix[5] = vec.y;
        scale.matrix[10] = vec.z;
        return scale;
    }

    public static Mat4f scale(float scaleFactor) {
        Mat4f scale = Mat4f.identity();
        scale.matrix[0] = scaleFactor;
        scale.matrix[5] = scaleFactor;
        scale.matrix[10] = scaleFactor;
        return scale;
    }

    public static Mat4f rotationZ(float angleRad) {
        Mat4f res = Mat4f.identity();
        float sin = (float) Math.sin(angleRad * PI_180);
        float cos = (float) Math.cos(angleRad * PI_180);
        res.matrix[0] = cos;
        res.matrix[1] = -sin;
        res.matrix[4] = sin;
        res.matrix[5] = cos;
        return res;
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
