package com.jkojote.linear.engine.math;

/**
 * Represents three dimensional vector;
 * it's made mutable due to performance reasons.
 */
public final class Vec3f {

    float x, y, z;

    public Vec3f() { x = y = z = 0; }

    public Vec3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * @return x component
     */
    public float getX() { return x; }

    /**
     * @return y component
     */
    public float getY() { return y; }

    /**
     * @return z component
     */
    public float getZ() { return z; }


    /**
     * Copies this vector components into new vector.
     * @return new vector
     */
    public Vec3f copy() { return new Vec3f(x, y, z); }

    /**
     * Addition of two vectors.
     * <b>Changes</b> this vector.
     * @param other vector to be added
     * @return this vector
     */
    public Vec3f add(Vec3f other) {
        x += other.x;
        y += other.y;
        z += other.z;
        return this;
    }

    /**
     * Subtraction of two vectors.
     * <b>Changes</b>this vector
     * @param other vector to be subtracted
     * @return this
     */
    public Vec3f sub(Vec3f other) {
        x -= other.x;
        y -= other.y;
        z -= other.z;
        return this;
    }

    /**
     * Dot product of two vectors.
     * <b>Doesn't change</b> this vector.
     * @param other the second vector
     * @return dot product of two vectors
     */
    public float dot(Vec3f other) {
        return x * other.x + y * other.y + z * other.z;
    }

    /**
     * Cross product of two vectors.
     * <b>Doesn't change</b> this vector.
     * @param other the second
     * @return new vector that is the result of cross product
     */
    public Vec3f cross(Vec3f other) {
        return new Vec3f(
            y * other.z - z * other.y,
            z * other.x - x * other.z,
            x * other.y - y * other.x
        );
    }

    /**
     * Performs scalar multiplication.
     * <b>Changes</b> this vector components.
     * @param scalar scalar to be multiplied by
     * @return this
     */
    public Vec3f scalar(float scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
        return this;
    }

    /**
     * Calculates length of this vector.
     * @return length of this vector
     */
    public float len() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    /**
     * Calculates squared length of this vector.
     * @return squared length of this vector
     */
    public float lenSqr() {
        return x * x + y * y + z * z;
    }

    /**
     * Returns normalized vector.
     * <b>Doesn't change</b> this vector
     * @return
     */
    public Vec3f normalized() {
        float len = len();
        return new Vec3f(x / len, y / len, z / len);
    }

    @Override
    public String toString() {
        return "( " + x + " ; " + y + " ; " + z + " )";
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Vec3f vec3f = (Vec3f) object;
        return Float.compare(vec3f.x, x) == 0 &&
                Float.compare(vec3f.y, y) == 0 &&
                Float.compare(vec3f.z, z) == 0;
    }

    @Override
    public int hashCode() {
        return (int) (31 * (31 * y + x) + z);
    }
}
