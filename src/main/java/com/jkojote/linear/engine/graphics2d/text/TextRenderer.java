package com.jkojote.linear.engine.graphics2d.text;


import com.jkojote.linear.engine.Initializable;
import com.jkojote.linear.engine.Releasable;
import com.jkojote.linear.engine.ResourceInitializationException;
import com.jkojote.linear.engine.graphics2d.*;
import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.math.Vec3f;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;

public class TextRenderer implements Renderer<ModifiableText>, Initializable, Releasable {

    private Shader shader;

    public TextRenderer() { }

    @Override
    public void render(ModifiableText text, Camera camera) {
        FontMap font = text.getFontMap();
        CharSequence seq = text.getSequence();
        Vec3f color = text.getColor();
        renderText(seq, font, color, text.modelMatrix(), camera);
    }

    public void renderText(CharSequence seq, FontMap font, Vec3f color, Mat4f transformationMatrix, Camera camera) {
        Texture2D fontTexture = font.getTexture();
        float xRatio = 1f / fontTexture.getWidth();
        float yRatio = 1f / fontTexture.getHeight();
        int lines = 0;
        int len = 0;
        for (int i = 0; i < seq.length(); i++) {
            char c = seq.charAt(i);
            if (c == '\n') {
                lines++;
                continue;
            }
            len++;
        }
        int capacity = (len * 28) << 2;
        FloatBuffer buffer = createVbo(seq, color, font, xRatio, yRatio, capacity, lines);
        Vaof vao = new Vaof(3, true)
                // position
                .addArrayBuffer(buffer, GL_STATIC_DRAW, 0, 2, 28, 0)
                // color
                .addArrayBuffer(buffer, GL_STATIC_DRAW, 1, 3, 28, 8)
                // texture coordinates
                .addArrayBuffer(buffer, GL_STATIC_DRAW, 2, 2, 28, 20);
        shader.bind();
        fontTexture.bind();
        vao.bind();
        shader.setUniform("pv", camera.viewProjection(), true);
        shader.setUniform("model", transformationMatrix, true);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);

        glDrawArrays(GL_QUADS, 0, len << 2);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        vao.release();
        fontTexture.unbind();
        shader.unbind();
    }

    private FloatBuffer createVbo(CharSequence seq, Vec3f color, FontMap font,
                                  float xRatio, float yRatio, int capacity, int lines) {
        int lineHeight = font.getLineHeight();
        float colorX = color.x(),
              colorY = color.y(),
              colorZ = color.z();
        FloatBuffer buffer = ByteBuffer.allocateDirect(capacity).order(ByteOrder.nativeOrder()).asFloatBuffer();
        float xPosition = 0, yPosition = 0;
        for (int i = 0; i < seq.length(); i++) {
            char c = seq.charAt(i);
            if (c == '\n') {
                yPosition -= lineHeight;
                xPosition = 0;
                continue;
            }
            Glyph g = font.getGlyph(c);
            buffer.put(xPosition)
                    .put(yPosition - g.height)
                    .put(colorX).put(colorY).put(colorZ)
                    .put(g.offsetX * xRatio)
                    .put((g.offsetY + g.height) * yRatio);
            buffer.put(xPosition)
                    .put(yPosition)
                    .put(colorX).put(colorY).put(colorZ)
                    .put(g.offsetX * xRatio)
                    .put(g.offsetY * yRatio);
            buffer.put(xPosition + g.width)
                    .put(yPosition)
                    .put(colorX).put(colorY).put(colorZ)
                    .put((g.offsetX + g.width) * xRatio)
                    .put(g.offsetY * yRatio);
            buffer.put(xPosition + g.width)
                    .put(yPosition - g.height)
                    .put(colorX).put(colorY).put(colorZ)
                    .put((g.offsetX + g.width) * xRatio)
                    .put((g.offsetY + g.height) * yRatio);
            xPosition += g.width;
        }
        buffer.flip();
        return buffer;
    }

    @Override
    public void init() throws ResourceInitializationException {
        try {
            shader = Shader.fromResources("shaders/text/vert.glsl", "shaders/text/fragm.glsl");
        } catch (ShaderCreationException e) {
            throw new ResourceInitializationException(e);
        } catch (IOException e) {
            throw new ResourceInitializationException(e);
        }
    }

    @Override
    public boolean isInitialized() {
        return shader != null;
    }

    @Override
    public void release() {
        if (!isInitialized())
            return;
        shader.release();
    }

    @Override
    public boolean isReleased() {
        if (!isInitialized())
            return false;
        return shader.isReleased();
    }
}
