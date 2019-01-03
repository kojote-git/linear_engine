package com.jkojote.engine.graphics.primitives;

import com.jkojote.linear.engine.graphics2d.primitives.Rectangle;
import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.math.Vec3f;
import com.jkojote.linear.engine.window.Window;
import org.junit.Test;


import static org.lwjgl.glfw.GLFW.*;

public class RectangleTest {

    private Rectangle rectangle;

    private PrimitiveRenderer renderer;

    private int width = 400, height = 400;

    public RectangleTest() {
        rectangle = new Rectangle(new Vec3f(100, 100, 100), 80, 80);
        rectangle.setColor(new Vec3f(0.5f, 0.5f, 0.5f));
        Mat4f proj = Mat4f.ortho(-width / 2f, width / 2f, -height / 2f, height / 2f, 0.0f, 1.0f);
        renderer = new PrimitiveRenderer(Mat4f.identity(), proj);
    }

    // draw rectangle, rotate it pressing right and left arrows
    // and scale it pressing up and down arrows
    @Test
    public void draw() {
        Window window = new Window("w", width, height, false, false)
            .setInitCallback(() -> {
                renderer.init();
            })
            .setRenderCallback(() -> {
                renderer.render(rectangle);
            })
            .setKeyCallback((key, action, mods) -> {
                int rotationInc = 10;
                if (action != GLFW_PRESS && action != GLFW_REPEAT)
                    return;
                if (key == GLFW_KEY_RIGHT) {
                    rectangle.setRotationAngle(rectangle.getRotationAngle() + rotationInc);
                }
                if (key == GLFW_KEY_LEFT) {
                    rectangle.setRotationAngle(rectangle.getRotationAngle() - rotationInc);
                }
                if (key == GLFW_KEY_UP) {
                    rectangle.setScaleFactor(rectangle.getScaleFactor() + 0.05f);
                }
                if (key == GLFW_KEY_DOWN) {
                    rectangle.setScaleFactor(rectangle.getScaleFactor() - 0.05f);
                }
            })
            .setWindowClosedCallback(() -> {
                renderer.release();
            })
            .init();
        while (!window.isTerminated()) {
            window.update();
        }
    }
}
