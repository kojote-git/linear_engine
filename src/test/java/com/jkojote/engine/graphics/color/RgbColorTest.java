package com.jkojote.engine.graphics.color;

import com.jkojote.linear.engine.graphics2d.color.RgbColor;
import org.junit.Test;

public class RgbColorTest {

	@Test
	public void createValidColors() {
		RgbColor.of(255, 255, 255);
		RgbColor.of(120, 120, 120);
	}

	@Test
	public void createInvalidColors() {
		createInvalidRgbColor(-1, -1, -1);
		createInvalidRgbColor(256, 256, 256);
	}

	private void createInvalidRgbColor(int red, int green, int blue) {
		try {
			RgbColor.of(red, green, blue);
		} catch (Exception e) {
			return;
		}
		throw new TestFailedException("value was: (" + red + ", " + green + ", " + blue + ")");
	}
}
