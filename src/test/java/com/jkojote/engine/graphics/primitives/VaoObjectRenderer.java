package com.jkojote.engine.graphics.primitives;

import com.jkojote.linear.engine.ReleasableResource;
import com.jkojote.linear.engine.graphics2d.Shader;
import com.jkojote.linear.engine.graphics2d.VaoObject;
import com.jkojote.linear.engine.math.Mat4f;

import java.io.IOException;

import static org.lwjgl.opengl.GL11.glDrawArrays;

public class VaoObjectRenderer implements ReleasableResource {

    private Mat4f viewProj;

    private Shader shader;

    private boolean released;

    public VaoObjectRenderer(Mat4f view, Mat4f projection) {
        viewProj = projection.mult(view);
    }

    public void render(VaoObject vao) {
        shader.bind();
        shader.setUniform("viewProj", viewProj, false);
        shader.setUniform("model", vao.modelMatrix(), true);
        vao.bind();
        vao.enableAttributes();
        glDrawArrays(vao.renderingMode(), 0, vao.drawCount());
        vao.disableAttributes();
        vao.unbind();
        shader.unbind();
    }

    public void init() {
        try {
            shader = Shader.fromFiles(
                "src/test/java/com/jkojote/engine/graphics/primitives/vert.glsl",
                "src/test/java/com/jkojote/engine/graphics/primitives/fragm.glsl"
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void release() {
        shader.release();
        released = true;
    }

    @Override
    public boolean isReleased() {
        return released;
    }
}
