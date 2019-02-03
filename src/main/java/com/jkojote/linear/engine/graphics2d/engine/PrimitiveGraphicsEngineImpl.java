package com.jkojote.linear.engine.graphics2d.engine;

import com.jkojote.linear.engine.ResourceInitializationException;
import com.jkojote.linear.engine.graphics2d.*;
import com.jkojote.linear.engine.graphics2d.primitives.Ellipse;
import com.jkojote.linear.engine.graphics2d.primitives.VertexShape;
import com.jkojote.linear.engine.graphics2d.primitives.renderers.*;
import com.jkojote.linear.engine.graphics2d.sprites.SpriteObject;
import com.jkojote.linear.engine.graphics2d.sprites.SpriteObjectRenderer;
import com.jkojote.linear.engine.graphics2d.text.FontMap;
import com.jkojote.linear.engine.graphics2d.text.ModifiableText;
import com.jkojote.linear.engine.graphics2d.text.PlainText;
import com.jkojote.linear.engine.graphics2d.text.TextRenderer;
import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.math.Vec3f;
import com.jkojote.linear.engine.window.Window;

import java.util.Collection;

import static org.lwjgl.opengl.GL11.GL_QUADS;

public class PrimitiveGraphicsEngineImpl implements PrimitiveGraphicsEngine {

    private Camera camera;

    private boolean initialized;

    private boolean released;

    private VaoObjectRenderer vaoObjectRenderer;

    private TexturedObjectRenderer texturedObjectRenderer;

    private EllipseRenderer ellipseRenderer;

    private VertexShapeRenderer vertexShapeRenderer;

    private TextRenderer textRenderer;

    private TextureRenderer textureRenderer;

    private SpriteObjectRenderer spriteObjectRenderer;

    public PrimitiveGraphicsEngineImpl() {
        this.texturedObjectRenderer = new TexturedObjectRenderer();
        this.vaoObjectRenderer = new VaoObjectRenderer();
        this.ellipseRenderer = new EllipseRenderer();
        this.vertexShapeRenderer = new VertexShapeRenderer();
        this.textRenderer = new TextRenderer();
        this.textureRenderer = new TextureRenderer();
        this.spriteObjectRenderer = new SpriteObjectRenderer();
    }

    @Override
    public void init() throws ResourceInitializationException {
        if (initialized)
            return;
        spriteObjectRenderer.init();
        texturedObjectRenderer.init();
        vaoObjectRenderer.init();
        ellipseRenderer.init();
        vertexShapeRenderer.init();
        textRenderer.init();
        textureRenderer.init();
        initialized = true;
    }

    @Override
    public boolean isInitialized() { return initialized; }

    @Override
    public void release() {
        if (released)
            return;
        spriteObjectRenderer.release();
        texturedObjectRenderer.release();
        ellipseRenderer.release();
        vaoObjectRenderer.release();
        ellipseRenderer.release();
        vertexShapeRenderer.release();
        textRenderer.release();
        textureRenderer.release();
        released = true;
    }

    @Override
    public boolean isReleased() { return released; }

    @Override
    public void setCamera(Camera camera) { this.camera = camera; }

    @Override
    public Camera getCamera() {return camera; }

    @Override
    public void render(Renderable renderable) {
        if (renderable instanceof SpriteObject) {
            spriteObjectRenderer.render((SpriteObject) renderable, camera);
        }
        else if (renderable instanceof VaoObject)
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
        else if (renderable instanceof ModifiableText) {
            textRenderer.render((ModifiableText) renderable, camera);
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
