package com.jkojote.engine.graphics.shader;

import com.jkojote.linear.engine.graphics2d.Shader;
import com.jkojote.linear.engine.graphics2d.Vaof;
import com.jkojote.linear.engine.window.Window;
import org.junit.Test;

import java.io.IOException;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;

public class ShaderTest {

    private Shader shader;

    private int FLOAT_SIZE = 4;

    private String vert = "src/test/java/com/jkojote/engine/graphics/shader/vert.glsl";

    private String fragm = "src/test/java/com/jkojote/engine/graphics/shader/fragm.glsl";

    private Vaof vao;

    @Test
    public void draw() {
        int width = 300, height = 300;
        float colorX = 0.0f, colorY = 0.0f, colorZ = 0.0f;
        float[] vertices = {
        //  -position-  ------- color --------
            0.0f, 0.0f, colorX, colorY, colorZ,
            0.0f, 0.5f, colorX, colorY, colorZ,
            0.5f, 0.5f, colorX, colorY, colorZ,
            0.5f, 0.0f, colorX, colorY, colorZ
        };
        Window w = new Window("w", width, height, false, false)
            .setInitCallback(() -> {
                try {
                    shader = Shader.fromFiles(vert, fragm);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                vao = new Vaof(2);
                vao.bind();
                // add position and color
                vao.addArrayBuffer(vertices, GL_STATIC_DRAW, 0, 2, 5 * FLOAT_SIZE, 0)
                   .addArrayBuffer(vertices, GL_STATIC_DRAW, 1, 3, 5 * FLOAT_SIZE, 2 * FLOAT_SIZE);
                vao.unbind();
            })
            .setRenderCallback(() -> {
                shader.bind();
                vao.bind();
                glEnableVertexAttribArray(0);
                glEnableVertexAttribArray(1);
                glDrawArrays(GL_QUADS, 0, 4);
                glDisableVertexAttribArray(0);
                glDisableVertexAttribArray(1);
                vao.unbind();
                shader.unbind();
            })
            .setWindowClosedCallback(() -> {
                vao.release();
                shader.release();
            })
            .init();
        System.out.println("************ Shader Test ***********");
        System.out.println("Do you see black square?");
        while (!w.isTerminated()) {
            w.update();
        }
    }
}
