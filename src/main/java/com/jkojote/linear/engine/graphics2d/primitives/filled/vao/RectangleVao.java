package com.jkojote.linear.engine.graphics2d.primitives.filled.vao;

import com.jkojote.linear.engine.graphics2d.VaoObject;
import com.jkojote.linear.engine.graphics2d.Vaof;
import com.jkojote.linear.engine.graphics2d.primitives.BaseShape;
import com.jkojote.linear.engine.math.Vec3f;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;

public class RectangleVao extends BaseShape implements VaoObject {

    private float initialWidth, initialHeight;

    private ArrayList<Vec3f> vertices;

    private Vaof vaof;

    private boolean updateVao, vaoReleased;

    public RectangleVao(Vec3f translation, float width, float height) {
        initialWidth = width;
        initialHeight = height;
        vertices = new ArrayList<>(4);
        vertices.add(new Vec3f(-width / 2, -height / 2, 0));
        vertices.add(new Vec3f(-width / 2,  height / 2, 0));
        vertices.add(new Vec3f( width / 2,  height / 2, 0));
        vertices.add(new Vec3f( width / 2, -height / 2, 0));
        setTranslation(translation);
        setColor(new Vec3f());
        setScaleFactor(1.0f);
        setRotationAngle(0.0f);
    }

    public float getInitialWidth() { return initialWidth; }

    public float getInitialHeight() { return initialHeight; }

    @Override
    public void setColor(Vec3f color) {
        updateVao = true;
        super.setColor(color);
    }

    @Override
    public int renderingMode() {
        return GL_QUADS;
    }

    @Override
    public void bind() {
        checkVao();
        vaof.bind();
    }

    @Override
    public void unbind() {
        checkVao();
        vaof.unbind();
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
        return 4;
    }

    private void checkVao() {
        if (vaoReleased)
            throw new IllegalStateException("rectangle is released");
        if (updateVao) {
            if (vaof != null)
                vaof.release();
            vaof = VaoUtils.createPrimitiveVao(vertices, color);
            updateVao = false;
        }
    }

    @Override
    public void release() {
        vaoReleased = true;
        if (vaof != null)
            vaof.release();
    }

    @Override
    public boolean isReleased() {
        return vaoReleased;
    }
}
