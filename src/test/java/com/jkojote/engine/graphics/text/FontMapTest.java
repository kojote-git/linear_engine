package com.jkojote.engine.graphics.text;

import com.jkojote.engine.graphics.LoopRunner;
import com.jkojote.engine.graphics.TransformableCamera;
import com.jkojote.engine.graphics.TransformationController;
import com.jkojote.linear.engine.graphics2d.cameras.StaticCamera;
import com.jkojote.linear.engine.graphics2d.text.ModifiableText;
import com.jkojote.linear.engine.graphics2d.text.TextRenderer;
import com.jkojote.linear.engine.graphics2d.text.FontMap;
import com.jkojote.linear.engine.graphics2d.primitives.renderers.TexturedObjectRenderer;
import com.jkojote.linear.engine.window.Window;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.lwjgl.opengl.GL11.glClearColor;


public class FontMapTest {

    private FontTexture fontTexture;

    private TexturedObjectRenderer texturedObjectRenderer;

    private FontMap font;

    private String path =
        "src/test/java/com/jkojote/engine/graphics/text/Cousine-Regular.ttf";

    private TransformableCamera transformableCamera;

    private StaticCamera staticCamera;

    private TextRenderer textRenderer;

    private Window window;

    private int width = 400, height = 400;

    private ModifiableText text;

    @Before
    public void init() {
        textRenderer = new TextRenderer();
        texturedObjectRenderer = new TexturedObjectRenderer();
        text = new ModifiableText(font);
        window = new Window("w", width, height, false, false, true)
            .setInitCallback((win) -> {
                try {
                    font = FontMap.FontMapBuilder.aFont()
                        .fromFile(path)
                        .withSize(32)
                        .withStyle(Font.PLAIN)
                        .withAntialiasingEnabled()
                        .withFontFormat(Font.TRUETYPE_FONT)
                        .build();
                    fontTexture = new FontTexture(font);
                    text.setFontMap(font);
                    text.append("Hello World!\n\tHello World!\nПривет мир!\nПривет мир!");

                    font.init();
                    texturedObjectRenderer.init();
                    textRenderer.init();
                } catch (Exception e) {
                    window.release();
                    if (!texturedObjectRenderer.isReleased()) {
                        texturedObjectRenderer.release();
                    }
                    if (!textRenderer.isReleased()) {
                        textRenderer.release();
                    }
                    throw new RuntimeException(e);
                }
            })
            .setSamplePoints(4);
        transformableCamera = new TransformableCamera(window);
        staticCamera = new StaticCamera(window);
    }
    @After
    public void release() {
        if (!texturedObjectRenderer.isReleased()) {
            texturedObjectRenderer.release();
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
            .setKeyCallback(controller)
            .init();
        runner.setRenderCallback(() -> {
            glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            texturedObjectRenderer.render(fontTexture, transformableCamera);
        });
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
            .setKeyCallback(controller)
            .init();
        runner.setRenderCallback(() -> {
            textRenderer.render(text, transformableCamera);
        });
        runner.setUpdateCallback(controller::update);
        runner.run();
    }
}
