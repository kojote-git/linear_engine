package com.jkojote.engine.graphics.primitives;

import com.jkojote.linear.engine.graphics2d.Shader;
import com.jkojote.linear.engine.graphics2d.Vaof;
import com.jkojote.linear.engine.graphics2d.primitives.Rectangle;
import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.math.Vec3f;
import com.jkojote.linear.engine.window.Window;
import org.junit.Test;

import java.io.IOException;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;

public class RectangleTest {

    private Shader shader = null;

    private Rectangle rectangle;

    private static final int FLOAT_SIZE = 4;

    private Mat4f viewProj;

    private Vaof vaof;

    private int width = 400, height = 400;

    public RectangleTest() {
        rectangle = new Rectangle()
                .withPosition(new Vec3f(0, 0, 0))
                .withWidth(80)
                .withHeight(80)
                .withRotationAngle(0)
                .withColor(new Vec3f(0.5f, 0.5f, 0.5f))
                .withScaleFactor(1.2f);
        viewProj = Mat4f.ortho(-width / 2f, width / 2f, -height / 2f, height / 2f, 0.0f, 1.0f);
    }

    // draw rectangle, rotate it pressing right and left arrows
    // and scale it pressing up and down arrows
    @Test
    public void draw() {
        Window window = new Window("w", width, height, false, false)
            .setInitCallback(() -> {
                try {
                    shader = Shader.fromFiles(
                        "src/test/java/com/jkojote/engine/graphics/primitives/vert.glsl",
                        "src/test/java/com/jkojote/engine/graphics/primitives/fragm.glsl"
                    );
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Vec3f pos = rectangle.getPosition();
                Vec3f color = rectangle.getColor();
                float w = rectangle.getWidth();
                float h = rectangle.getHeight();
                float
                    rX = pos.getX() + w / 2,
                    lX = pos.getX() - w / 2,
                    bY = pos.getY() - h / 2,
                    tY = pos.getY() + w / 2,
                    colorX = color.getX(),
                    colorY = color.getY(),
                    colorZ = color.getZ();
                float[] vertices = {
                    lX, bY, colorX, colorY, colorZ,
                    lX, tY, colorX, colorY, colorZ,
                    rX, tY, colorX, colorY, colorZ,
                    rX, bY, colorX, colorY, colorZ,
                };
                vaof = new Vaof(2, true)
                    .addArrayBuffer(vertices, GL_STATIC_DRAW, 0, 2, 5 * FLOAT_SIZE, 0)
                    .addArrayBuffer(vertices, GL_STATIC_DRAW, 1, 3, 5 * FLOAT_SIZE, 2 * FLOAT_SIZE);
                vaof.unbind();
            })
            .setRenderCallback(() -> {
                shader.bind();
                shader.setUniform("viewProj", viewProj);
                shader.setUniform("model", rectangle.modelMatrix());
                vaof.bind();
                glEnableVertexAttribArray(0);
                glEnableVertexAttribArray(1);

                glDrawArrays(GL_QUADS, 0, 4);

                glDisableVertexAttribArray(0);
                glDisableVertexAttribArray(1);
                vaof.unbind();
                shader.unbind();
            })
            .setKeyCallback((key, action, mods) -> {
                int rotationInc = 10;
                float scaleFactorInc = 0.1f;
                if (action != GLFW_PRESS && action != GLFW_REPEAT)
                    return;
                if (key == GLFW_KEY_RIGHT) {
                    rectangle.setRotationAngle(rectangle.getRotationAngle() + rotationInc);
                }
                if (key == GLFW_KEY_LEFT) {
                    rectangle.setRotationAngle(rectangle.getRotationAngle() - rotationInc);
                }
                if (key == GLFW_KEY_UP) {
                    rectangle.setScaleFactor(rectangle.getScale() + scaleFactorInc);
                }
                if (key == GLFW_KEY_DOWN) {
                    rectangle.setScaleFactor(rectangle.getScale() - scaleFactorInc);
                }
            })
            .setWindowClosedCallback(() -> {
                shader.release();
                vaof.release();
            })
            .init();
        while (!window.isTerminated()) {
            window.update();
        }
    }
}
