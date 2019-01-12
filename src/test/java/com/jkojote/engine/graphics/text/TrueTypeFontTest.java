package com.jkojote.engine.graphics.text;

import com.jkojote.engine.graphics.TransformableCamera;
import com.jkojote.engine.graphics.TransformationController;
import com.jkojote.linear.engine.graphics2d.text.Text;
import com.jkojote.linear.engine.graphics2d.text.TextRenderer;
import com.jkojote.linear.engine.graphics2d.text.FontMap;
import com.jkojote.linear.engine.graphics2d.primitives.renderers.TexturedObjectRenderer;
import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.math.Vec3f;
import com.jkojote.linear.engine.window.Window;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.lwjgl.opengl.GL11.glClearColor;


public class TrueTypeFontTest {

    private FontTexture fontTexture;

    private TexturedObjectRenderer renderer;

    private FontMap font;

    private Mat4f projectionMatrix;

    private String path =
        "src/test/java/com/jkojote/engine/graphics/text/HelveticaRegular.ttf";

    private TransformableCamera camera;

    private TextRenderer textRenderer;

    private Window window;

    private int width = 400, height = 400;

    public TrueTypeFontTest() {
        projectionMatrix = Mat4f.ortho(-width / 2f, width / 2f, -height / 2f, height / 2f, 0.0f, 1.0f);
        camera = new TransformableCamera();
    }

    @Before
    public void init() {
        textRenderer = new TextRenderer(projectionMatrix);
        renderer = new TexturedObjectRenderer(projectionMatrix);
        window = new Window("w", width, height, false, false)
            .setInitCallback(() -> {
                try {
                    font = new FontMap(new Font("Calibri", Font.PLAIN, 58));
                    glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
//                    font = FontMap.FontMapBuilder.aFont()
//                        .fromFile(path)
//                        .withSize(40)
//                        .withStyle(Font.PLAIN)
//                        .withAntialiasingEnabled()
//                        .withFontFormat(Font.TRUETYPE_FONT)
//                        .build();
                    fontTexture = new FontTexture(font);
                    renderer.init();
                    textRenderer.init();
                } catch (Exception e) {
                    window.release();
                    if (!renderer.isReleased()) {
                        renderer.release();
                    }
                    if (!textRenderer.isReleased()) {
                        textRenderer.release();
                    }
                    throw new RuntimeException(e);
                }
            });
    }
    @After
    public void release() {
        if (!renderer.isReleased()) {
            renderer.release();
        }
        if (!textRenderer.isReleased()) {
            textRenderer.release();
        }
        if (!window.isReleased()) {
            window.release();
        }
    }

    @Test
    public void drawFontTexture() {
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

    @Test
    public void drawText() {
        TransformationController controller = new TransformationController(camera);
        camera.setScaleFactor(1.0f);
        controller.setTranslationDelta(5f);
        controller.setRotationDelta(2f);
        controller.setScaleFactorDelta(0.01f);
        window
            .setRenderCallback(() -> {
                Text text = new Text(font);
                text.setTranslation(new Vec3f(0, 0, 0));
                text.append("Hello World!\nHello World!\nПривет мир!\nПривет мир!");
                textRenderer.render(text, camera);
            })
            .setKeyCallback(controller)
            .init();
        while (!window.isTerminated()) {
            window.update();
            controller.update();
        }
    }
}