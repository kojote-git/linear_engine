package com.jkojote.linear.engine.graphics2d.engine;

import com.jkojote.linear.engine.ResourceInitializationException;
import com.jkojote.linear.engine.graphics2d.*;
import com.jkojote.linear.engine.graphics2d.primitives.VertexShape;
import com.jkojote.linear.engine.graphics2d.primitives.solid.Ellipse;
import com.jkojote.linear.engine.graphics2d.primitives.renderers.*;
import com.jkojote.linear.engine.graphics2d.text.FontMap;
import com.jkojote.linear.engine.graphics2d.text.PlainText;
import com.jkojote.linear.engine.graphics2d.text.TextRenderer;
import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.math.Vec3f;
import com.jkojote.linear.engine.window.Window;

import java.util.Collection;

import static org.lwjgl.opengl.GL11.GL_QUADS;

public class PrimitiveGraphicsEngineImpl implements PrimitiveGraphicsEngine {

    private Window window;

    private Camera camera;

    private VaoObjectRenderer vaoObjectRenderer;

    private TexturedObjectRenderer texturedObjectRenderer;

    private EllipseRenderer ellipseRenderer;

    private VertexShapeRenderer vertexShapeRenderer;

    private TextRenderer textRenderer;

    private TextureRenderer textureRenderer;

    public PrimitiveGraphicsEngineImpl(Window window) {
        if (window.isInitialized())
            throw new IllegalStateException();
        this.window = window;
        Mat4f proj = window.getProjectionMatrix();
        this.texturedObjectRenderer = new TexturedObjectRenderer(proj);
        this.vaoObjectRenderer = new VaoObjectRenderer(proj);
        this.ellipseRenderer = new EllipseRenderer(proj);
        this.vertexShapeRenderer = new VertexShapeRenderer(proj);
        this.textRenderer = new TextRenderer(proj);
        this.textureRenderer = new TextureRenderer(proj);
        this.window.setInitCallback(() -> {
           this.texturedObjectRenderer.init();
           this.vaoObjectRenderer.init();
           this.ellipseRenderer.init();
           this.vertexShapeRenderer.init();
           this.textRenderer.init();
           this.textureRenderer.init();
        });
    }

    @Override
    public void init() throws ResourceInitializationException { window.init(); }

    @Override
    public boolean isInitialized() { return window.isInitialized(); }

    @Override
    public void release() { window.terminate(); }

    @Override
    public boolean isReleased() { return window.isReleased(); }

    @Override
    public void setCamera(Camera camera) { this.camera = camera; }

    @Override
    public Camera getCamera() {return camera; }

    @Override
    public void render(Renderable renderable) {
        if (renderable instanceof VaoObject)
            vaoObjectRenderer.render((VaoObject) renderable, camera);
        else if (renderable instanceof VertexShape)
            vertexShapeRenderer.render((VertexShape) renderable, camera);
        else if (renderable instanceof TexturedObject)
            texturedObjectRenderer.render((TexturedObject) renderable, camera);
        else if (renderable instanceof Ellipse)
            ellipseRenderer.render((Ellipse) renderable, camera);
        else if (renderable instanceof RenderableTextureTuple) {
            RenderableTextureTuple tuple = (RenderableTextureTuple) renderable;
            Texture2D texture = tuple.texture;
            Mat4f transform = tuple.transformationMatrix;
            textureRenderer.render(texture, camera, transform);
        }
        else if (renderable instanceof PlainText) {
            PlainText text = (PlainText) renderable;
            CharSequence seq = text.getText();
            Vec3f color = text.getColor();
            FontMap fontMap = text.getFont();
            Mat4f transform = text.modelMatrix();
            textRenderer.renderText(seq, fontMap, color, transform, camera);
        }
    }

    @Override
    public void renderAll(Collection<Renderable> renderables) {
        for (Renderable renderable: renderables) {
            render(renderable);
        }
    }

    @Override
    public void renderTexture(Texture2D texture2D, Mat4f transformationMatrix) {
        render(new RenderableTextureTuple(texture2D, transformationMatrix));
    }

    @Override
    public void renderText(CharSequence text, FontMap font, Vec3f color, Mat4f transformationMatrix) {
        PlainText plainText = new PlainText(text, font, transformationMatrix);
        plainText.setColor(color);
        render(plainText);
    }


    private class RenderableTextureTuple implements Renderable {

        private Texture2D texture;

        private Mat4f transformationMatrix;

        public RenderableTextureTuple(Texture2D texture, Mat4f transformationMatrix) {
            this.texture = texture;
            this.transformationMatrix = transformationMatrix;
        }

        public Texture2D getTexture() {
            return texture;
        }

        @Override
        public Mat4f modelMatrix() {
            return transformationMatrix;
        }

        @Override
        public int renderingMode() {
            return GL_QUADS;
        }
    }
}
