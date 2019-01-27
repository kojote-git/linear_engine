package com.jkojote.engine.graphics.text;

import com.jkojote.engine.graphics.LoopRunner;
import com.jkojote.engine.graphics.TransformableCamera;
import com.jkojote.engine.graphics.TransformationController;
import com.jkojote.linear.engine.graphics2d.StaticCamera;
import com.jkojote.linear.engine.graphics2d.text.ModifiableText;
import com.jkojote.linear.engine.graphics2d.text.TextRenderer;
import com.jkojote.linear.engine.graphics2d.text.FontMap;
import com.jkojote.linear.engine.graphics2d.primitives.renderers.TexturedObjectRenderer;
import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.window.Window;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.lwjgl.opengl.GL11.glClearColor;


public class FontMapTest {

    private FontTexture fontTexture;

    private TexturedObjectRenderer renderer;

    private FontMap font;

    private Mat4f projectionMatrix;

    private String path =
        "src/test/java/com/jkojote/engine/graphics/text/Cousine-Regular.ttf";

    private TransformableCamera transformableCamera;

    private StaticCamera staticCamera;

    private TextRenderer textRenderer;

    private Window window;

    private int width = 400, height = 400;

    private ModifiableText text;

    public FontMapTest() {
        projectionMatrix = Mat4f.ortho(-width / 2f, width / 2f, -height / 2f, height / 2f, 0.0f, 1.0f);
        transformableCamera = new TransformableCamera();
        staticCamera = new StaticCamera();
    }

    @Before
    public void init() {
        textRenderer = new TextRenderer(projectionMatrix);
        renderer = new TexturedObjectRenderer(projectionMatrix);
        text = new ModifiableText(font);
        window = new Window("w", width, height, false, false)
            .setInitCallback(() -> {
                try {
                    // use larger size for better font quality
                    // it later can be scaled
//                    font = new FontMap(new Font("Calibri", Font.BOLD, 32), false);
                    font = FontMap.FontMapBuilder.aFont()
                        .fromFile(path)
                        .withSize(128)
                        .withStyle(Font.PLAIN)
                        .withAntialiasingEnabled()
                        .withFontFormat(Font.TRUETYPE_FONT)
                        .build();
                    fontTexture = new FontTexture(font);
                    text.setFontMap(font);
                    text.append("Hello World!\n\tHello World!\nПривет мир!\nПривет мир!");

                    font.init();
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
        TransformationController controller = new TransformationController(transformableCamera);
        LoopRunner runner = new LoopRunner(window);
        transformableCamera.setScaleFactor(0.2f);
        controller.setTranslationDelta(5f);
        controller.setRotationDelta(2f);
        window
            .setUpdateCallback(() -> {
                glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                renderer.render(fontTexture, transformableCamera);
            })
            .setKeyCallback(controller)
            .init();
        runner.setUpdateCallback(controller::update);
        runner.run();
    }

    @Test
    public void drawText() {
        TransformationController controller = new TransformationController(transformableCamera);
        LoopRunner runner = new LoopRunner(window);
        transformableCamera.setScaleFactor(1.0f);
        controller.setTranslationDelta(5f);
        controller.setRotationDelta(2f);
        controller.setScaleFactorDelta(0.01f);
        window
            .setUpdateCallback(() -> {
                textRenderer.render(text, transformableCamera);
            })
            .setKeyCallback(controller)
            .init();
        runner.setUpdateCallback(controller::update);
        runner.run();
    }
}
