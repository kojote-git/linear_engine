package com.jkojote.linear.engine.graphics2d.primitives;

import com.jkojote.linear.engine.math.Vec3f;

import java.util.List;

/**
 * This extension of the {@link Shape} interface represents a shape that consists of vertices connected
 * in a specific order. The order of the vertices is determined by the order of elements, returned by
 * {@link #vertices()} method. Also, the vertices reside in the local-space.
 * For example, this is a simple square, defined by vertices:
 * <ul style="margin: 5px;">
 *  <li>1. (-10, -5, 0)</li>
 *  <li>2. (-10, 5, 0)</li>
 *  <li>3. (10, 5, 0)</li>
 *  <li>4. (10, -5, 0)</li>
 * </ul>
 * Where 1, 2, 3, 4 - the number of the vertex and (x, y, z) - coordinates in the local space.
 */
public interface VertexShape extends Shape {
    /**
     * @return vertices of the shape
     */
    List<Vec3f> vertices();
}
