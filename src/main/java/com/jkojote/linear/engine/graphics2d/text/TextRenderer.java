package com.jkojote.linear.engine.graphics2d.text;


import com.jkojote.linear.engine.InitializableResource;
import com.jkojote.linear.engine.ReleasableResource;
import com.jkojote.linear.engine.ResourceInitializationException;
import com.jkojote.linear.engine.graphics2d.Camera;
import com.jkojote.linear.engine.graphics2d.Renderer;
import com.jkojote.linear.engine.graphics2d.Shader;
import com.jkojote.linear.engine.graphics2d.ShaderCreationException;
import com.jkojote.linear.engine.math.Mat4f;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class TextRenderer implements Renderer<Text>, InitializableResource, ReleasableResource {

    private Mat4f projectionMatrix;

    private Shader shader;

    public TextRenderer(Mat4f projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
    }

    @Override
    public void render(Text text, Camera camera) {
        TrueTypeFont font = text.getFont();
        CharSequence seq = text.getSequence();
    }

    @Override
    public void init() throws ResourceInitializationException {
        try {
            shader = Shader.fromSource("shaders/text/vert.glsl", "shaders/text/fragm.glsl");
        } catch (ShaderCreationException e) {
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
