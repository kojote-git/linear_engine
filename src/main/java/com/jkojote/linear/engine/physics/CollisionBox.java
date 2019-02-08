package com.jkojote.linear.engine.physics;

import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.math.Vec3f;
import com.jkojote.linear.engine.shared.BaseTransformable;

/**
 * This collision box represents an idea of Oriented Collision Box.
 * It can intersect with other collision boxes of different shapes and sizes.
 * As it implements {@link com.jkojote.linear.engine.shared.Transformable} interface, a collision box
 * may be transformed in terms of rotation, scale and can be translated to any position in a world space.
 */
public class CollisionBox extends BaseTransformable {

    private Vec3f[] vertices;

    /**
     * Create collision box that consist of vertices in a local space
     * @param vertices vertices of the collision box
     */
    public CollisionBox(Vec3f[] vertices) {
        super(new Vec3f(), 0.0f, 1.0f);
        this.vertices = vertices;
    }

    /**
     * Create collision box that consist of vertices in a local space and is initially
     * translated to {@code translation} coordinates.
     * @param translation vector that represents translation
     * @param vertices vertices of the collision box
     */
    public CollisionBox(Vec3f translation, Vec3f[] vertices) {
        super(translation, 0.0f, 1.0f);
        this.vertices = vertices;
    }

    /**
     * Checks if two collision boxes collide
     * @param collisionBox a collision box to be checked on collision
     * @return {@code true} if collision boxes collide and {@code false} otherwise
     */
    public boolean checkCollides(CollisionBox collisionBox) {
        /*
         * To check whether two collision boxes collide I applied the separating axis theorem
         * assuming that both collision boxes represent a convex hull.
         * At first we need to transform the vertices of the boxes from local-space coordinates
         * to world-space coordinates by using their transformation matrices.
         * Then the actual collision detection algorithm is performed on these transformed vertices.
         */

        // v1 and v2 are used to store transformed vertices
        Vec3f[] v1 = new Vec3f[this.vertices.length];
        Vec3f[] v2 = new Vec3f[collisionBox.vertices.length];
        Mat4f tm1 = transformationMatrix();
        Mat4f collisionBoxTransformationMatrix = collisionBox.transformationMatrix();
        //transform vertices using transformation matrices
        for (int i = 0; i < v1.length; i++)
            v1[i] = tm1.mult(this.vertices[i]);
        for (int i = 0; i < v2.length; i++)
            v2[i] = collisionBoxTransformationMatrix.mult(collisionBox.vertices[i]);
        if (testOverlap(v1, v2) && testOverlap(v2, v1))
            return true;
        return false;
    }

    private boolean testOverlap(Vec3f[] v1, Vec3f[] v2) {
        Vec3f begin, end, edge, normal;
        for (int i = 0; i < v1.length; i++) {
            begin = v1[i];
            end = (i == v1.length - 1) ? v1[0].copy() : v1[i + 1].copy();
            edge = end.sub(begin);
            normal = edge.normal();
            Projection p1 = projectOntoNormal(v1, normal);
            Projection p2 = projectOntoNormal(v2, normal);

            if (!p1.overlaps(p2))
                return false;
        }
        return true;
    }

    private Projection projectOntoNormal(Vec3f[] vertices, Vec3f normal) {
        float minDot = Float.MAX_VALUE, maxDot = -Float.MAX_VALUE;
        for (Vec3f vertex : vertices) {
            float dot = vertex.dot(normal);
            if (dot < minDot) minDot = dot;
            if (dot > maxDot) maxDot = dot;
        }
        return new Projection(minDot, maxDot);
    }


    private class Projection {

        float min, max;

        Projection(float min, float max) {
            this.min = min;
            this.max = max;
        }

        boolean overlaps(Projection other) {
            return isBetween(this.min, other.min, other.max) ||
                    isBetween(other.min, this.min, this.max);
        }

        boolean isBetween(float value, float lowerBound, float upperBound) {
            return lowerBound <= value && value <= upperBound;
        }
    }
}
