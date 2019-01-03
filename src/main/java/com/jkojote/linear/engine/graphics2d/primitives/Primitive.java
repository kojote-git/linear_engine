package com.jkojote.linear.engine.graphics2d.primitives;

import com.jkojote.linear.engine.graphics2d.Renderable;
import com.jkojote.linear.engine.math.Vec3f;

import java.util.List;


public interface Primitive extends Renderable {

    List<Vec3f> vertices();

    Vec3f color();

}
