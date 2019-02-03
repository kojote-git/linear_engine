package com.jkojote.engine.window;

import com.jkojote.linear.engine.window.Window;
import com.jkojote.linear.engine.window.WindowStateEventListener;
import com.jkojote.linear.engine.window.events.WindowMatrixUpdatedEvent;
import com.jkojote.linear.engine.window.events.WindowResizedEvent;
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

    @Test
    public void eventsTest() {
        Window window = new Window("W", 300, 300, true, true);
        window.init();
        window.addStateListener(new MatrixUpdatedListener());
        window.addStateListener(new ResizeListener());
        while (!window.isTerminated()) {
            window.clear();
            window.pollEvents();
            window.update();
        }
        window.terminate();
    }

    private class MatrixUpdatedListener implements WindowStateEventListener<WindowMatrixUpdatedEvent> {

        @Override
        public Class<WindowMatrixUpdatedEvent> eventType() {
            return WindowMatrixUpdatedEvent.class;
        }

        @Override
        public void onEvent(WindowMatrixUpdatedEvent event) {
            System.out.println("matrix updated");
        }
    }

    private class ResizeListener implements WindowStateEventListener<WindowResizedEvent> {

        @Override
        public Class<WindowResizedEvent> eventType() {
            return WindowResizedEvent.class;
        }

        @Override
        public void onEvent(WindowResizedEvent event) {
            System.out.printf("width: %d\nheight: %d\n", event.getWidth(), event.getHeight());
        }
    }
}
