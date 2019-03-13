package com.jkojote.linear.engine.graphics2d.color;

import com.jkojote.linear.engine.math.Vec3f;

public final class HexColor implements Color {
	private int red;
	private int green;
	private int blue;

	private HexColor(int red, int green, int blue) {
		this.red = red;
		this.blue = blue;
		this.green = green;
	}

	public static HexColor of(String color) {
		if (color == null)
			throw new NullPointerException("color cannot be null");
		color = cleanHexColorString(color);
		checkHexColorString(color);
		return parse(color);
	}

	private static String cleanHexColorString(String hexColor) {
		if (hexColor.contains("#"))
			hexColor = hexColor.replace("#", "");
		if (hexColor.contains("0x"))
			hexColor = hexColor.replace("0x", "");
		return hexColor.trim();
	}

	private static void checkHexColorString(String hexColor) {
		if (hexColor.isEmpty() || hexColor.length() != 6)
			throw new IllegalArgumentException("illegal hexadecimal value of color");
	}

	private static HexColor parse(String hexColor) {
		int color;
		try {
			color = Integer.parseInt(hexColor, 16);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("cannot parse hexadecimal color: " + hexColor);
		}
		int red = (color >> 16) & 0xFF;
		int green = (color >> 8) & 0xFF;
		int blue = color & 0xFF;
		return new HexColor(red, green, blue);
	}

	@Override
	public Vec3f toVector() {
		return null;
	}
}
