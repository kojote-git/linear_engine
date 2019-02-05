package com.jkojote.linear.engine.physics;

import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.math.Vec3f;
import com.jkojote.linear.engine.shared.BaseTransformable;

public class CollisionBox extends BaseTransformable {

    private Vec3f[] vertices;

    public CollisionBox(Vec3f[] vertices) {
        super(new Vec3f(), 0.0f, 1.0f);
        this.vertices = vertices;
    }

    public CollisionBox(Vec3f translation, Vec3f[] vertices) {
        super(translation, 0.0f, 1.0f);
        this.vertices = vertices;
    }

    public boolean checkCollides(CollisionBox collisionBox) {
        Vec3f[] t1 = new Vec3f[this.vertices.length];
        Vec3f[] t2 = new Vec3f[collisionBox.vertices.length];
        Mat4f thisTransform = transformationMatrix();
        Mat4f cbTransform = collisionBox.transformationMatrix();
        //transform vertices using transformation matrices
        for (int i = 0; i < t1.length; i++)
            t1[i] = thisTransform.mult(this.vertices[i]);
        for (int i = 0; i < t2.length; i++)
            t2[i] = cbTransform.mult(collisionBox.vertices[i]);
        //test if vertices overlap
        if (testOverlap(t1, t2) && testOverlap(t2, t1))
            return true;
        return false;
    }

    private boolean testOverlap(Vec3f[] t1, Vec3f[] t2) {
        Vec3f start = t1[0];
        Vec3f end;
        for (int i = 1; i < t1.length; i++) {
            end = t1[i];
            Vec3f side = end.copy().sub(start);
            start = end;
            if (!checkOverlap(side, t1, t2))
                return false;
        }
        start = t1[t1.length - 1];
        end = t1[0];
        return checkOverlap(end.copy().sub(start), t1, t2);
    }

    /*
     * check if projections of t1 and t2 onto normal vector of the side overlap
     */
    private boolean checkOverlap(Vec3f side, Vec3f[] t1, Vec3f[] t2) {
        Vec3f normal = side.normal();
        DotTuple thisProjections = getProjection(t1, normal);
        DotTuple thatProjections = getProjection(t2, normal);
        return checkOverlap(
            thisProjections.min, thisProjections.max,
            thatProjections.min, thatProjections.max
        );
    }

    /*
     * Project vertices onto normal by dot product and then return max and min values of the projections
     */
    private DotTuple getProjection(Vec3f[] vertices, Vec3f normal) {
        float minDot = Float.MAX_VALUE, maxDot = -Float.MAX_VALUE;
        for (Vec3f vertex : vertices) {
            float dot = vertex.dot(normal);
            if (dot < minDot) minDot = dot;
            if (dot > maxDot) maxDot = dot;
        }
        return new DotTuple(minDot, maxDot);
    }

    private boolean checkOverlap(float aMin, float aMax, float bMin, float bMax) {
        if (bMin <= aMin && bMax >= aMin && bMax <= aMax)
            return true;
        if (aMin <= bMin && aMax >= bMin && aMax <= bMax)
            return true;
        if (bMin >= aMin && bMax <= aMax)
            return true;
        if (aMin >= bMin && aMax <= bMax)
            return true;
        return false;
    }

    private class DotTuple {

        private float min, max;

        private DotTuple(float min, float max) {
            this.min = min;
            this.max = max;
        }

    }
}
