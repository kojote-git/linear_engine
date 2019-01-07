package com.jkojote.engine.graphics.primitives;

import com.jkojote.linear.engine.ReleasableResource;
import com.jkojote.linear.engine.graphics2d.Shader;
import com.jkojote.linear.engine.graphics2d.Vaof;
import com.jkojote.linear.engine.graphics2d.primitives.VertexShape;
import com.jkojote.linear.engine.graphics2d.VaoUtils;
import com.jkojote.linear.engine.math.Mat4f;

import java.io.IOException;

import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;

public class VertexShapeRenderer implements ReleasableResource {

    private Mat4f viewProj;

    private Shader shader;

    private boolean released;

    public VertexShapeRenderer(Mat4f view, Mat4f proj) {
        viewProj = proj.mult(view);
    }

    public void render(VertexShape shape) {
        Vaof vao = VaoUtils.createPrimitiveVao(shape.vertices(), shape.color());
        shader.bind();
        shader.setUniform("viewProj", viewProj, false);
        shader.setUniform("model", shape.modelMatrix(), true);

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawArrays(shape.renderingMode(), 0, shape.vertices().size());

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        vao.release();
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
        released = true;
        shader.release();
    }

    @Override
    public boolean isReleased() {
        return released;
    }
}
