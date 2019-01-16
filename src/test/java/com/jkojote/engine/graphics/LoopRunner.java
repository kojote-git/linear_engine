package com.jkojote.engine.graphics;

import com.jkojote.linear.engine.window.Window;


public class LoopRunner implements Runnable {

    private Window window;

    private FpsCallback onFps;

    public LoopRunner(Window window) {
        this.window = window;
    }

    public LoopRunner(Window window, FpsCallback onFps) {
        this.window = window;
        this.onFps = onFps;
    }

    @Override
    public void run() {
        if (onFps == null)
            onFps = System.out::println;
        double amountOfTicks = 75.0;
        double lastTime = System.nanoTime();
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (!window.isTerminated()) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                delta--;
                window.update();
            }
            window.render();
            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                onFps.apply(frames);
                frames = 0;
            }
        }
    }
}
