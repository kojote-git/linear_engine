package com.jkojote.engine.objects;

import com.jkojote.linear.engine.Movable;
import com.jkojote.linear.engine.graphics2d.Camera;
import com.jkojote.linear.engine.graphics2d.StaticCamera;
import com.jkojote.linear.engine.graphics2d.Transformable;
import com.jkojote.linear.engine.graphics2d.primitives.renderers.VertexShapeRenderer;
import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.math.Vec3f;
import com.jkojote.linear.engine.window.Window;
import org.junit.Test;

import static org.lwjgl.glfw.GLFW.*;

public class GameObjectTest {

    private int width = 400, height = 400;

    private float angleDelta = 10f;

    private float velDelta = 2;

    private float scaleDelta = 0.1f;

    private VertexShapeRenderer renderer;

    private Camera camera;

    public GameObjectTest() {
        Mat4f proj = Mat4f.ortho(-width / 2f, width / 2f, -height / 2f, height / 2f, 0.0f, 1.0f);
        renderer = new VertexShapeRenderer(proj);
        camera = new StaticCamera();
    }

    @Test
    public void move() throws InterruptedException {
        MovableSquare square = new MovableSquare(new Vec3f(30, 30, 0), 30);
        MovableTriangle triangle = new MovableTriangle(
                new Vec3f(),
                new Vec3f(-30, -30, 0),
                new Vec3f(0, 30, 0),
                new Vec3f(30, -30, 0)
        );
        triangle.setColor(new Vec3f(0.1f, 0.8f, 0.1f));
        Movable movable = square;
        Transformable activePrimitive = square.getRectangle();
        Window w = new Window("W", width, height, false, false)
            .setKeyCallback((key, action, mods) -> {
                Vec3f prev = movable.position().copy();
                Vec3f vel;
                switch (key) {
                    case GLFW_KEY_LEFT:
                        vel = new Vec3f(-velDelta, 0, 0);
                        movable.setVelocity(vel);
                        movable.move();
//                        assertEquals(prev.add(vel), square.position());
                        break;
                    case GLFW_KEY_UP:
                        vel = new Vec3f(0, velDelta, 0);
                        movable.setVelocity(vel);
                        movable.move();
//                        assertEquals(prev.add(vel), square.position());
                        break;
                    case GLFW_KEY_RIGHT:
                        vel = new Vec3f(velDelta, 0, 0);
                        movable.setVelocity(vel);
                        movable.move();
//                        assertEquals(prev.add(vel), square.position());
                        break;
                    case GLFW_KEY_DOWN:
                        vel = new Vec3f(0, -velDelta, 0);
                        movable.setVelocity(vel);
                        movable.move();
//                        assertEquals(prev.add(vel), square.position());
                        break;
                    case GLFW_KEY_A:
                        activePrimitive.setRotationAngle(activePrimitive.getRotationAngle() + angleDelta);
                        break;
                    case GLFW_KEY_D:
                        activePrimitive.setRotationAngle(activePrimitive.getRotationAngle() - angleDelta);
                        break;
                    case GLFW_KEY_W:
                        activePrimitive.setScaleFactor(activePrimitive.getScaleFactor() + scaleDelta);
                        break;
                    case GLFW_KEY_S:
                        activePrimitive.setScaleFactor(activePrimitive.getScaleFactor() - scaleDelta);
                        break;
                }
            })
            .setInitCallback(renderer::init)
            .setRenderCallback(() -> {
                renderer.render(square.getRectangle(), camera);
                renderer.render(triangle.getTriangle(), camera);
            })
            .setWindowClosedCallback(renderer::release);
        w.init();
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
