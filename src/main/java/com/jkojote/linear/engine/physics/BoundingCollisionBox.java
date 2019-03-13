package com.jkojote.linear.engine.physics;

import com.jkojote.linear.engine.math.Vec3f;
import com.jkojote.linear.engine.shared.BaseTransformable;

public class BoundingCollisionBox extends BaseTransformable {
	private float width, height;

	public BoundingCollisionBox(float width, float height) {
		this.width = width;
		this.height = height;
	}

	public float getHeight() {
		return height;
	}

	public float getWidth() {
		return width;
	}

	@Override
	public void setScaleFactor(float scaleFactor) {
	}

	@Override
	public float getScaleFactor() {
		return 1;
	}

	@Override
	public void setRotationAngle(float rotationAngle) {
	}

	@Override
	public float getRotationAngle() {
		return 0;
	}

	public boolean collides(BoundingCollisionBox collisionBox) {
		Projection xProjection = projectOntoX();
		Projection yProjection = projectOntoY();
		Projection cbXProjection = collisionBox.projectOntoX();
		Projection cbYProjection = collisionBox.projectOntoY();
		return xProjection.overlaps(cbXProjection) && yProjection.overlaps(cbYProjection);
	}

	private Projection projectOntoX() {
		Vec3f topLeft = getTopLeft();
		return new Projection(topLeft.x(), topLeft.x() + width);
	}

	private Projection projectOntoY() {
		Vec3f topLeft = getTopLeft();
		return  new Projection(topLeft.y() - height, topLeft.y());
	}

	private Vec3f getTopLeft() {
		return getTranslation().copy().add(-width / 2, height / 2, 0);
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
