package com.jkojote.linear.engine.graphics2d.color;

import com.jkojote.linear.engine.math.Vec3f;

public final class RgbColor implements Color {
	public static final RgbColor RED = RgbColor.of(255, 0, 0);
	public static final RgbColor GREEN = RgbColor.of(0, 255, 0);
	public static final RgbColor BLUE = RgbColor.of(0, 0, 255);
	public static final RgbColor BLACK = RgbColor.of(0, 0, 0);
	public static final RgbColor WHITE = RgbColor.of(255, 255, 255);

	private int red;
	private int green;
	private int blue;

	private RgbColor(int red, int green, int blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	public static RgbColor of(int red, int green, int blue) {
		checkComponent(red);
		checkComponent(green);
		checkComponent(blue);
		return new RgbColor(red, green, blue);
	}

	private static void checkComponent(int value) {
		if (value < 0 || value > 255)
			throw new IllegalArgumentException("illegal value for rgb component");
	}

	public int getGreen() {
		return green;
	}

	public int getBlue() {
		return blue;
	}

	public int getRed() {
		return red;
	}

	@Override
	public Vec3f toVector() {
		return new Vec3f(red / 255f, green / 255f, blue / 255f);
	}
}
