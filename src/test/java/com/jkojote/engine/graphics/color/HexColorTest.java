package com.jkojote.engine.graphics.color;

import com.jkojote.linear.engine.graphics2d.color.HexColor;
import org.junit.Test;

public class HexColorTest {

	@Test
	public void testValidColors() {
		HexColor.of("0xffffff");
		HexColor.of("#ffffff");
		HexColor.of("ffffff");
		HexColor.of("ffFFfF");
	}

	@Test
	public void testInvalidColors() {
		tryInvalidValue("");
		tryInvalidValue(null);
		tryInvalidValue("ffffffff");
		tryInvalidValue("fffff");
		tryInvalidValue("value");
		tryInvalidValue("abc");
	}

	private void tryInvalidValue(String value) {
		try {
			HexColor.of(value);
		} catch (Exception e) {
			return;
		}
		throw new TestFailedException("value was: " + value);
	}

	private static class TestFailedException extends RuntimeException {

		public TestFailedException() {
		}

		public TestFailedException(String message) {
			super(message);
		}

		public TestFailedException(String message, Throwable cause) {
			super(message, cause);
		}

		public TestFailedException(Throwable cause) {
			super(cause);
		}

		public TestFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
			super(message, cause, enableSuppression, writableStackTrace);
		}
	}
}
