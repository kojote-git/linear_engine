package com.jkojote.engine.physics;

import com.jkojote.linear.engine.math.Vec3f;
import com.jkojote.linear.engine.physics.BoundingCollisionBox;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BoundingCollisionBoxTest {
	@Test
	public void boundingBoxCollisionDetectionTest() {
		BoundingCollisionBox cb1 = new BoundingCollisionBox(100, 100);
		BoundingCollisionBox cb2 = new BoundingCollisionBox(100, 100);

		cb1.setTranslation(new Vec3f(101, 0, 0));
		assertFalse(cb1.collides(cb2));
		cb1.setTranslation(new Vec3f(0, 101, 0));
		assertFalse(cb1.collides(cb2));
		cb1.setTranslation(new Vec3f(-101, 0, 0));
		assertFalse(cb1.collides(cb2));
		cb1.setTranslation(new Vec3f(0, -101, 0));
		assertFalse(cb1.collides(cb2));

		cb1.setTranslation(new Vec3f(50, 50, 0));
		assertTrue(cb1.collides(cb2));
		cb1.setTranslation(new Vec3f(-50, 50, 0));
		assertTrue(cb1.collides(cb2));
		cb1.setTranslation(new Vec3f(-50, -50, 0));
		assertTrue(cb1.collides(cb2));
		cb1.setTranslation(new Vec3f(50, -50, 0));
		assertTrue(cb1.collides(cb2));
	}
}
