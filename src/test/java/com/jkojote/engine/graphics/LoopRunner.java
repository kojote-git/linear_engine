package com.jkojote.engine.graphics;

import com.jkojote.linear.engine.window.Window;

public class LoopRunner implements Runnable {

    private Window window;

    public LoopRunner(Window window) {
        this.window = window;
    }

    @Override
    public void run() {
        double amountOfTicks = 120.0;
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
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
    }
}
