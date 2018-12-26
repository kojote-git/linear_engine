package com.jkojote.engine.math;

import com.jkojote.linear.engine.Movable;
import com.jkojote.linear.engine.math.Vec3f;
import com.jkojote.linear.engine.window.Window;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.lwjgl.glfw.GLFW.*;

public class GameObjectTest {

    @Test
    public void move() throws InterruptedException {
        Movable<MovableSquare> square = new MovableSquare(1, 10, 10);
        Window w = new Window("W", 400, 400, false, false)
            .setKeyCallback((key, action, mods) -> {
                Vec3f prev = square.position().copy();
                Vec3f vel;
                switch (key) {
                    case GLFW_KEY_LEFT:
                        vel = new Vec3f(-1, 0, 0);
                        square.setVelocity(vel);
                        square.move();
                        assertEquals(prev.add(vel), square.position());
                        System.out.println("(" + square.position().getX() + "; " + square.position().getY() + ")");
                        break;
                    case GLFW_KEY_UP:
                        vel = new Vec3f(0, 1, 0);
                        square.setVelocity(vel);
                        square.move();
                        assertEquals(prev.add(vel), square.position());
                        System.out.println("(" + square.position().getX() + "; " + square.position().getY() + ")");
                        break;
                    case GLFW_KEY_RIGHT:
                        vel = new Vec3f(1, 0, 0);
                        square.setVelocity(vel);
                        square.move();
                        assertEquals(prev.add(vel), square.position());
                        System.out.println("(" + square.position().getX() + "; " + square.position().getY() + ")");
                        break;
                    case GLFW_KEY_DOWN:
                        vel = new Vec3f(0, -1, 0);
                        square.setVelocity(vel);
                        square.move();
                        assertEquals(prev.add(vel), square.position());
                        System.out.println("(" + square.position().getX() + "; " + square.position().getY() + ")");
                        break;
                }
            }).init();
        while (!w.isTerminated()) {
            Thread.sleep(1000 / 60);
            w.update();
        }
    }
}
