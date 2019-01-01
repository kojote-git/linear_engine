package com.jkojote.engine.window;

import com.jkojote.linear.engine.window.Window;
import org.junit.Test;


public class WindowTest {

    @Test
    public void init() throws InterruptedException {
        Window window = new Window("W", 300, 300, true, true)
            .setCursorCallback((x, y) -> {
                System.out.println("x: " + x + ", y: " + y);
            })
            .setKeyCallback((key, action, mods) -> {
                System.out.println("key: " + key + ", action: " + action + ", mods: " + mods);
            })
            .setUpdateCallback(() -> {
                System.out.println("window is updated");
            })
            .setInitCallback(() -> {
                System.out.println("window is initialized");
            })
            .setWindowClosedCallback(() -> {
                System.out.println("window is closed");
            }).init();
        while (!window.isTerminated()) {
            Thread.sleep(1000 / 60);
            window.update();
        }
        window.terminate();
    }
}
