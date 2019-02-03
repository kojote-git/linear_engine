package com.jkojote.engine.window;

import com.jkojote.linear.engine.window.Window;
import org.junit.Test;


public class WindowTest {

    @Test
    public void init() throws InterruptedException {
        Window window = new Window("W", 300, 300, true, true)
            .setCursorCallback((win, x, y) -> {
                System.out.println("x: " + x + ", y: " + y);
            })
            .setKeyCallback((win, key, action, mods) -> {
                System.out.println("key: " + key + ", action: " + action + ", mods: " + mods);
            })
            .setInitCallback((win) -> {
                System.out.println("window is initialized");
            })
            .setTerminationCallback((win) -> {
                System.out.println("window is closed");
            });
        window.init();
        while (!window.isTerminated()) {
            Thread.sleep(1000 / 60);
            window.pollEvents();
        }
        window.terminate();
    }
}
