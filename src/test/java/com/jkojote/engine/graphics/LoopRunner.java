package com.jkojote.engine.graphics;

import com.jkojote.linear.engine.window.Window;


public class LoopRunner implements Runnable {

    private Window window;

    private FpsCallback onFps;

    private LoopUpdateCallback updateCallback;

    private RenderCallback renderCallback;

    public LoopRunner(Window window) {
        this.window = window;
    }

    public LoopRunner(Window window, FpsCallback onFps) {
        this.window = window;
        this.onFps = onFps;
    }

    public void setUpdateCallback(LoopUpdateCallback updateCallback) {
        this.updateCallback = updateCallback;
    }

    public void setRenderCallback(RenderCallback renderCallback) {
        this.renderCallback = renderCallback;
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
                window.pollEvents();
                if (updateCallback != null)
                    updateCallback.update();
            }
            window.clear();
            if (renderCallback != null)
                renderCallback.perform();
            window.update();
            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                onFps.apply(frames);
                frames = 0;
            }
        }
    }
}
