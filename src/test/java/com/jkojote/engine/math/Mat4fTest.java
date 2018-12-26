package com.jkojote.engine.math;

import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.math.Vec3f;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Mat4fTest {

    @Test
    public void mult() {
        float[] m1f = new float[] {
            2, 3, 5, 1,
            2, 5, 6, 8,
            3, 2, 1, 0,
            9, 2, 5, 6
        };
        float[] m2f = new float[] {
            2, 1, 2, 3,
            1, 3, 5, 9,
            2, 1, 3, 2,
            9, 2, 1, 6
        };
        float[] expectedM1xM2f = new float[] {
            26, 18, 35, 49,
            93, 39, 55, 111,
            10, 10, 19, 29,
            84, 32, 49, 91
        };
        float[] expectedM2xM1f = new float[] {
            39, 21, 33, 28,
            104, 46, 73, 79,
            33, 21, 29, 22,
            79, 51, 88, 61
        };
        Mat4f m1 = new Mat4f(m1f);
        Mat4f m2 = new Mat4f(m2f);
        Mat4f expectedM1xM2 = new Mat4f(expectedM1xM2f);
        Mat4f expectedM2xM1 = new Mat4f(expectedM2xM1f);
        Mat4f identity = Mat4f.identity();
        assertEquals(expectedM1xM2, m1.mult(m2));
        assertEquals(expectedM2xM1, m2.mult(m1));
        assertEquals(m1, m1.mult(identity));
        Vec3f v1 = new Vec3f(2, 3, 4);
        assertEquals(v1, identity.mult(v1));
    }

    @Test
    public void copy() {
        Mat4f m = new Mat4f(new float[] {
            1, 2, 3, 4,
            1, 2, 3, 4,
            1, 2, 3, 4,
            1, 2, 3, 4
        });
        assertEquals(m, m.copy());
    }

    @Test
    public void add() {
        Mat4f m1 = new Mat4f(new float[] {
            1, 2, 3, 4,
            1, 2, 3, 4,
            1, 2, 3, 4,
            1, 2, 3, 4
        });
        Mat4f m2 = new Mat4f(new float[] {
            1, 2, 3, 4,
            1, 2, 3, 4,
            1, 2, 3, 4,
            1, 2, 3, 4
        });
        Mat4f expected = new Mat4f(new float[] {
            2, 4, 6, 8,
            2, 4, 6, 8,
            2, 4, 6, 8,
            2, 4, 6, 8
        });
        assertEquals(expected, m1.copy().add(m2));
        assertEquals(expected, m2.copy().add(m1));
    }

    @Test
    public void scalar() {
        float s = 3;
        Mat4f m1 = new Mat4f(new float[] {
            1, 1, 1, 1,
            1, 1, 1, 1,
            1, 1, 1, 1,
            1, 1, 1, 1
        });
        Mat4f expected = new Mat4f(new float[] {
            1 * s, 1 * s, 1 * s, 1 * s,
            1 * s, 1 * s, 1 * s, 1 * s,
            1 * s, 1 * s, 1 * s, 1 * s,
            1 * s, 1 * s, 1 * s, 1 * s
        });
        assertEquals(expected, m1.scalar(s));
    }

    @Test
    public void set() {
        Mat4f m = new Mat4f(new float[] {
            0,  1,  2,  3,
            4,  5,  6,  7,
            8,  9,  10, 11,
            12, 13, 14, 15
        });
        Mat4f expected = new Mat4f(new float[] {
            1,  2,  3,  4,
            5,  6,  7,  8,
            9,  10, 11, 12,
            13, 14, 15, 16
        });
        m.set(0, 0, 1);
        m.set(0, 1, 2);
        m.set(0, 2, 3);
        m.set(0, 3, 4);

        m.set(1, 0, 5);
        m.set(1, 1, 6);
        m.set(1, 2, 7);
        m.set(1, 3, 8);

        m.set(2, 0, 9);
        m.set(2, 1, 10);
        m.set(2, 2, 11);
        m.set(2, 3, 12);

        m.set(3, 0, 13);
        m.set(3, 1, 14);
        m.set(3, 2, 15);
        m.set(3, 3, 16);
        assertEquals(expected, m);
    }

    @Test
    public void get() {
        float delta = 0.05f;
        Mat4f m = new Mat4f(new float[] {
            0,  1,  2,  3,
            4,  5,  6,  7,
            8,  9,  10, 11,
            12, 13, 14, 15
        });
        assertEquals(0, m.get(0, 0), delta);
        assertEquals(1, m.get(0, 1), delta);
        assertEquals(2, m.get(0, 2), delta);
        assertEquals(3, m.get(0, 3), delta);

        assertEquals(4, m.get(1, 0), delta);
        assertEquals(5, m.get(1, 1), delta);
        assertEquals(6, m.get(1, 2), delta);
        assertEquals(7, m.get(1, 3), delta);

        assertEquals(8, m.get(2, 0), delta);
        assertEquals(9, m.get(2, 1), delta);
        assertEquals(10, m.get(2, 2), delta);
        assertEquals(11, m.get(2, 3), delta);

        assertEquals(12, m.get(3, 0), delta);
        assertEquals(13, m.get(3, 1), delta);
        assertEquals(14, m.get(3, 2), delta);
        assertEquals(15, m.get(3, 3), delta);
    }
}
