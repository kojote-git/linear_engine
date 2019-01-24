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
        for (int i = 0; i < t1.length; i++)
            t1[i] = thisTransform.mult(this.vertices[i]);
        for (int i = 0; i < t2.length; i++)
            t2[i] = cbTransform.mult(collisionBox.vertices[i]);
        Vec3f start = t1[0];
        for (int i = 1; i < t1.length; i++) {
            Vec3f end = t1[i];
            Vec3f side = end.copy().sub(start);
            start = end;
            Vec3f normal = side.normal().unit();
            DotTuple thisProjections = getProjection(t1, normal);
            DotTuple thatProjections = getProjection(t2, normal);
            if (!checkOverlap(
                thisProjections.min, thisProjections.max,
                thatProjections.min, thatProjections.max
            )) return false;
        }
        return true;
    }

    /*
     * Project vertices onto normal by dot product and then return max and min values of the projections
     */
    private DotTuple getProjection(Vec3f[] vertices, Vec3f normal) {
        float minDot = Float.MAX_VALUE, maxDot = -Float.MAX_VALUE;
        for (int j = 0; j < vertices.length; j++) {
            Vec3f vertex = vertices[j];
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
        public DotTuple(float min, float max) {
            this.min = min;
            this.max = max;
        }

    }
}
