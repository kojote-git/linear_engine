package com.jkojote.engine.graphics.texture;

import com.jkojote.linear.engine.ReleasableResource;
import com.jkojote.linear.engine.graphics2d.Shader;
import com.jkojote.linear.engine.graphics2d.Texture2D;
import com.jkojote.linear.engine.graphics2d.TexturedObject;
import com.jkojote.linear.engine.graphics2d.Vaof;
import com.jkojote.linear.engine.math.Mat4f;

import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;

public class TextureObjectRenderer implements ReleasableResource {

    private Mat4f viewProj;

    private Shader shader;

    public TextureObjectRenderer(Mat4f view, Mat4f proj) {
        this.viewProj = proj.mult(view);
    }

    public void init() {
        try {
            shader = Shader.fromFiles(
                "src/test/java/com/jkojote/engine/graphics/texture/vert.glsl",
                "src/test/java/com/jkojote/engine/graphics/texture/fragm.glsl"
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void render(TexturedObject obj) {
        Texture2D texture = obj.getTexture();
        int
            width = texture.getWidth(),
            height = texture.getHeight();
        float[] coords = {
        //  ---------- position -----------  -coords-
            -width / 2f,  height / 2f, 0.0f,   0, 0,
            -width / 2f, -height / 2f, 0.0f,   0, 1,
             width / 2f, -height / 2f, 0.0f,   1, 1,
             width / 2f,  height / 2f, 0.0f,   1, 0
        };
        Vaof vao = new Vaof(2, true)
            .addArrayBuffer(coords, GL_STATIC_DRAW, 0, 3, 20, 0)
            .addArrayBuffer(coords, GL_STATIC_DRAW, 1, 2, 20, 12);
        vao.unbind();
        shader.bind();
        shader.setUniform("viewProj", viewProj, false);
        shader.setUniform("model", obj.modelMatrix(), true);
        texture.bind();
        vao.bind();
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glDrawArrays(GL_QUADS, 0, 4);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        texture.unbind();
        vao.unbind();
        vao.release();
        shader.unbind();
    }

    @Override
    public void release() {
        shader.release();
    }

    @Override
    public boolean isReleased() {
        return shader.isReleased();
    }
}
