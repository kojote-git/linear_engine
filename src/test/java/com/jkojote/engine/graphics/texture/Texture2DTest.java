package com.jkojote.engine.graphics.texture;

import com.jkojote.linear.engine.graphics2d.Texture2D;
import com.jkojote.linear.engine.window.Window;
import org.junit.Test;

import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;

public class Texture2DTest {

    private Texture2D t;

    private Window window;

    @Test
    public void drawTexture() {
        int width = 400, height = 400;
        window = new Window("window", width, height, false, false)
            .setInitCallback((win) -> {
                try {
                    t = new Texture2D("src/test/java/com/jkojote/engine/graphics/texture/bird.png");
                    t.init();
                } catch (IOException e) {
                    window.release();
                }
            });
        window.init();
        System.out.println("************ Texture2D test ************");
        System.out.println("Is the texture being rendered right?");
        while (!window.isTerminated()) {
            window.pollEvents();
            window.clear();

            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            glOrtho(-width / 2.0f, width / 2.0f, -height / 2.0f, height / 2.0f, 0.0f, 1.0f);
            glMatrixMode(GL_MODELVIEW);
            glLoadIdentity();
            glScalef(0.2f, 0.2f, 0.2f);
            t.bind();
            glEnable(GL_TEXTURE_2D);
            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            // OpenGL expects the (0;0) coordinate on the y-axis to be on the bottom side of the image,
            // but images usually have (0;0) at the top of the y-axis.
            glBegin(GL_QUADS);
            glTexCoord2f(0, 0);
            glVertex2f(-t.getWidth() / 2f, t.getHeight() / 2f);

            glTexCoord2f(0, 1);
            glVertex2f(-t.getWidth() / 2f, -t.getHeight() / 2f);

            glTexCoord2f(1, 1);
            glVertex2f(t.getWidth() / 2f, -t.getHeight() / 2f);

            glTexCoord2f(1, 0);
            glVertex2f(t.getWidth() / 2f, t.getHeight() / 2f);
            glEnd();
            t.unbind();

            window.update();
        }
        t.release();
    }
}
