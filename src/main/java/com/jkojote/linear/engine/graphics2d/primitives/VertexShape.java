package com.jkojote.linear.engine.graphics2d.primitives;

import com.jkojote.linear.engine.math.Vec3f;

import java.util.List;

public interface VertexShape extends Shape {
    /**
     * @return local space coordinates for this object
     */
    List<Vec3f> vertices();
}
