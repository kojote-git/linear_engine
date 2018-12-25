package com.jkojote.engine.math;

import com.jkojote.linear.engine.math.Vec3f;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Vec3fTest {

    @Test
    public void equals() {
        Vec3f v1 = new Vec3f(2.5f, 2.5f, 2.5f);
        Vec3f v2 = new Vec3f(2.5f, 2.5f, 2.5f);
        assertTrue(v1.equals(v2));
    }

    @Test
    public void add() {
        Vec3f v1 = new Vec3f();
        Vec3f v2 = new Vec3f(2, 3, 4);
        Vec3f res = new Vec3f(2, 3, 4);

        assertEquals(res, v1.add(v2));
    }

    @Test
    public void sub() {
        Vec3f v1 = new Vec3f();
        Vec3f v2 = new Vec3f(2, 3, 4);
        Vec3f res = new Vec3f(-2, -3, -4);

        assertEquals(res, v1.sub(v2));
    }

    @Test
    public void cross() {
        Vec3f v1 = new Vec3f(3, 4, 1);
        Vec3f v2 = new Vec3f(2, 3, 1);
        Vec3f expected = new Vec3f(1, -1, 1);
        assertEquals(expected, v1.cross(v2));
        expected = new Vec3f(-1, 1, -1);
        assertEquals(expected, v2.cross(v1));
    }

    @Test
    public void scalar() {
        float scalar = 5;
        Vec3f v1 = new Vec3f(1, 1, 1);
        Vec3f expected = new Vec3f(scalar, scalar, scalar);
        assertEquals(expected, v1.scalar(scalar));
    }

    @Test
    public void normalized() {
        Vec3f v1 = new Vec3f(3, 4, 0);
        float len = v1.len();
        Vec3f expected = new Vec3f(3 / len, 4 / len, 0);

        assertEquals(5.0f, len, 0.05f);
        assertEquals(expected, v1.normalized());
    }
}
