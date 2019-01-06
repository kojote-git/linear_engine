package com.jkojote.linear.engine.graphics2d.primitives.filled.vao;

import com.jkojote.linear.engine.graphics2d.VaoObject;
import com.jkojote.linear.engine.graphics2d.Vaof;
import com.jkojote.linear.engine.graphics2d.primitives.BaseShape;
import com.jkojote.linear.engine.math.Vec3f;

import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;

public class PolygonVao extends BaseShape implements VaoObject {

    private Vaof vao;

    private boolean updateVao, vaoReleased;

    private List<Vec3f> vertices;

    public PolygonVao(Vec3f[] vertices) {
        this.vertices = Arrays.asList(vertices);
        setTranslation(new Vec3f());
        setColor(new Vec3f());
        setScaleFactor(1.0f);
        setRotationAngle(0);
    }

    public PolygonVao(Vec3f translation, Vec3f color, Vec3f[] vertices) {
        this.vertices = Arrays.asList(vertices);
        setTranslation(translation);
        setColor(color);
        vao = VaoUtils.createPrimitiveVao(this.vertices, color);
        setScaleFactor(1.0f);
        setRotationAngle(0);
    }

    @Override
    public void setColor(Vec3f color) {
        updateVao = true;
        super.setColor(color);
    }

    @Override
    public void bind() {
        checkVao();
        vao.bind();
    }

    @Override
    public void unbind() {
        checkVao();
        vao.unbind();
    }

    @Override
    public void enableAttributes() {
        checkVao();
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
    }

    @Override
    public void disableAttributes() {
        checkVao();
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
    }

    @Override
    public int drawCount() {
        return vertices.size();
    }

    @Override
    public void release() {
        vaoReleased = true;
        if (vao != null)
            vao.release();
    }

    @Override
    public boolean isReleased() {
        return vaoReleased;
    }

    @Override
    public int renderingMode() {
        return GL_TRIANGLE_FAN;
    }

    private void checkVao() {
        if (vaoReleased)
            throw new IllegalStateException("polygon is released");
        if (updateVao) {
            if (vao != null)
                vao.release();
            vao = VaoUtils.createPrimitiveVao(vertices, color);
            updateVao = false;
        }
    }
}
