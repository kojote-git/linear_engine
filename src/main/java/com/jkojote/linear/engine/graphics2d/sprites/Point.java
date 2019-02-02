package com.jkojote.linear.engine.graphics2d.sprites;

public final class Point {

    private float x;

    private float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() { return x; }

    public float getY() { return y; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Float.compare(point.x, x) == 0 &&
                Float.compare(point.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return 31 * Float.hashCode(x) + Float.hashCode(y);
    }
}
