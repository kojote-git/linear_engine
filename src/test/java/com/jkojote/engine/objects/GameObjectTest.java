package com.jkojote.engine.objects;

import com.jkojote.engine.graphics.primitives.PrimitiveRenderer;
import com.jkojote.linear.engine.graphics2d.primitives.Rectangle;
import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.math.Vec3f;
import com.jkojote.linear.engine.window.Window;
import org.junit.Test;

import static org.lwjgl.glfw.GLFW.*;

public class GameObjectTest {

    private int width = 400, height = 400;

    private float angleDelta = 5f;

    private float scaleDelta = 0.05f;

    private PrimitiveRenderer renderer;

    public GameObjectTest() {
        Mat4f proj = Mat4f.ortho(-width / 2f, width / 2f, -height / 2f, height / 2f, 0.0f, 1.0f);
        renderer = new PrimitiveRenderer(Mat4f.identity(), proj);
    }

    @Test
    public void move() throws InterruptedException {
        MovableSquare square = new MovableSquare(10, 30);
        Window w = new Window("W", width, height, false, false)
            .setKeyCallback((key, action, mods) -> {
                Vec3f prev = square.position().copy();
                Vec3f vel;
                Rectangle r = square.getRectangle();
                switch (key) {
                    case GLFW_KEY_LEFT:
                        vel = new Vec3f(-1, 0, 0);
                        square.setVelocity(vel);
                        square.move();
//                        assertEquals(prev.add(vel), square.position());
                        break;
                    case GLFW_KEY_UP:
                        vel = new Vec3f(0, 1, 0);
                        square.setVelocity(vel);
                        square.move();
//                        assertEquals(prev.add(vel), square.position());
                        break;
                    case GLFW_KEY_RIGHT:
                        vel = new Vec3f(1, 0, 0);
                        square.setVelocity(vel);
                        square.move();
//                        assertEquals(prev.add(vel), square.position());
                        break;
                    case GLFW_KEY_DOWN:
                        vel = new Vec3f(0, -1, 0);
                        square.setVelocity(vel);
                        square.move();
//                        assertEquals(prev.add(vel), square.position());
                        break;
                    case GLFW_KEY_A:
                        r.setRotationAngle(r.getRotationAngle() - angleDelta);
                        break;
                    case GLFW_KEY_D:
                        r.setRotationAngle(r.getRotationAngle() + angleDelta);
                        break;
                    case GLFW_KEY_W:
                        r.setScaleFactor(r.getScaleFactor() + scaleDelta);
                        break;
                    case GLFW_KEY_S:
                        r.setScaleFactor(r.getScaleFactor() - scaleDelta);
                        break;
                }
            })
            .setInitCallback(renderer::init)
            .setRenderCallback(() -> renderer.render(square.getRectangle()))
            .setWindowClosedCallback(renderer::release)
            .init();
        long last = System.currentTimeMillis();
        int frames = 0;
        while (!w.isTerminated()) {
            long now = System.currentTimeMillis();
            w.update();
            frames++;
            if (now - last > 1000) {
                System.out.println("FPS:" + frames);
                frames = 0;
                last = now;
            }
        }
    }
}
