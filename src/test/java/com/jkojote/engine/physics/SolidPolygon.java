package com.jkojote.engine.physics;

import com.jkojote.linear.engine.graphics2d.primitives.VertexShape;
import com.jkojote.linear.engine.graphics2d.primitives.solid.Polygon;
import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.math.Vec3f;
import com.jkojote.linear.engine.physics.CollisionBox;
import com.jkojote.linear.engine.shared.Transformable;

import java.util.List;

public class SolidPolygon implements Transformable, VertexShape {

    private Polygon polygon;

    private CollisionBox collisionBox;

    public SolidPolygon(Vec3f[] vertices, Vec3f position, Vec3f color) {
        this.polygon = new Polygon(position, color, vertices);
        this.collisionBox = new CollisionBox(position, vertices);
    }

    public boolean checkCollides(SolidPolygon polygon) {
        return collisionBox.checkCollides(polygon.collisionBox);
    }

    @Override
    public void setTranslation(Vec3f translation) {
        this.polygon.setTranslation(translation);
        this.collisionBox.setTranslation(translation);
    }

    @Override
    public Vec3f getTranslation() {
        return this.polygon.getTranslation();
    }

    @Override
    public void setScaleFactor(float scaleFactor) {
        this.polygon.setScaleFactor(scaleFactor);
        this.collisionBox.setScaleFactor(scaleFactor);
    }

    @Override
    public float getScaleFactor() {
        return this.polygon.getScaleFactor();
    }

    @Override
    public void setRotationAngle(float rotationAngle) {
        this.polygon.setRotationAngle(rotationAngle);
        this.collisionBox.setRotationAngle(rotationAngle);
    }

    @Override
    public float getRotationAngle() {
        return this.polygon.getRotationAngle();
    }

    @Override
    public Mat4f transformationMatrix() {
        return this.polygon.transformationMatrix();
    }

    @Override
    public Mat4f modelMatrix() {
        return polygon.modelMatrix();
    }

    @Override
    public int renderingMode() {
        return polygon.renderingMode();
    }

    @Override
    public List<Vec3f> vertices() {
        return polygon.vertices();
    }

    @Override
    public Vec3f color() {
        return polygon.color();
    }

    @Override
    public void setColor(Vec3f color) {
        polygon.setColor(color);
    }
}
