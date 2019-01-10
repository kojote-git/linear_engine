package com.jkojote.engine.graphics.text;

import com.jkojote.engine.graphics.TransformableCamera;
import com.jkojote.engine.graphics.TransformationController;
import com.jkojote.linear.engine.graphics2d.Camera;
import com.jkojote.linear.engine.graphics2d.StaticCamera;
import com.jkojote.linear.engine.graphics2d.text.TrueTypeFont;
import com.jkojote.linear.engine.graphics2d.primitives.renderers.TexturedObjectRenderer;
import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.window.Window;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.lwjgl.opengl.GL11.glClearColor;


public class TrueTypeFontTest {

    private FontTexture fontTexture;

    private TexturedObjectRenderer renderer;

    private Mat4f projectionMatrix;

    private String path =
        "src/test/java/com/jkojote/engine/graphics/text/HelveticaBlack.ttf";

    private TransformableCamera camera;

    private Window window;

    private int width = 400, height = 400;

    public TrueTypeFontTest() {
        projectionMatrix = Mat4f.ortho(-width / 2f, width / 2f, -height / 2f, height / 2f, 0.0f, 1.0f);
        renderer = new TexturedObjectRenderer(projectionMatrix);
        camera = new TransformableCamera();
    }

    @Before
    public void init() {
        window = new Window("w", width, height, false, false)
            .setInitCallback(() -> {
                try {
                    glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                    fontTexture = new FontTexture(new TrueTypeFont(path, 16));
                    renderer.init();
                } catch (Exception e) {
                    window.release();
                    if (!renderer.isReleased()) {
                        renderer.release();
                    }
                    throw new RuntimeException(e);
                }
            });
    }

    @After
    public void release() {
        fontTexture.release();
        window.release();
    }

    @Test
    public void test() {
        TransformationController controller = new TransformationController(camera);
        camera.setScaleFactor(0.2f);
        controller.setTranslationDelta(5f);
        controller.setRotationDelta(2f);
        window
            .setRenderCallback(() -> {
                renderer.render(fontTexture, camera);
            })
            .setKeyCallback(controller)
            .init();
        while (!window.isTerminated()) {
            window.update();
            controller.update();
        }
    }
}
