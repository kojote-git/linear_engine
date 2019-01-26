package com.jkojote.linear.engine.graphics2d;

import com.jkojote.linear.engine.Initializable;
import com.jkojote.linear.engine.Releasable;
import com.jkojote.linear.engine.ResourceInitializationException;
import com.jkojote.linear.engine.graphics2d.primitives.VertexShape;
import com.jkojote.linear.engine.graphics2d.primitives.filled.Ellipse;
import com.jkojote.linear.engine.graphics2d.primitives.renderers.EllipseRenderer;
import com.jkojote.linear.engine.graphics2d.primitives.renderers.TexturedObjectRenderer;
import com.jkojote.linear.engine.graphics2d.primitives.renderers.VaoObjectRenderer;
import com.jkojote.linear.engine.graphics2d.primitives.renderers.VertexShapeRenderer;
import com.jkojote.linear.engine.graphics2d.text.FontMap;
import com.jkojote.linear.engine.graphics2d.text.Text;
import com.jkojote.linear.engine.graphics2d.text.TextRenderer;
import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.math.Vec3f;
import com.jkojote.linear.engine.window.Window;

import java.util.Collection;

public class SimpleGraphicsEngine implements GraphicsEngine, Releasable, Initializable {

    private Window window;

    private Camera camera;

    private VaoObjectRenderer vaoObjectRenderer;

    private TexturedObjectRenderer texturedObjectRenderer;

    private EllipseRenderer ellipseRenderer;

    private VertexShapeRenderer vertexShapeRenderer;

    private TextRenderer textRenderer;

    public SimpleGraphicsEngine(Window window) {
        if (window.isInitialized())
            throw new IllegalStateException();
        this.window = window;
        Mat4f proj = window.getProjectionMatrix();
        this.texturedObjectRenderer = new TexturedObjectRenderer(proj);
        this.vaoObjectRenderer = new VaoObjectRenderer(proj);
        this.ellipseRenderer = new EllipseRenderer(proj);
        this.vertexShapeRenderer = new VertexShapeRenderer(proj);
        this.textRenderer = new TextRenderer(proj);
        this.window.setInitCallback(() -> {
           this.texturedObjectRenderer.init();
           this.vaoObjectRenderer.init();
           this.ellipseRenderer.init();
           this.vertexShapeRenderer.init();
           this.textRenderer.init();
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
    public Window getWindow() { return window; }

    @Override
    public void setCamera(Camera camera) { this.camera = camera; }

    @Override
    public Camera getCamera() {return camera; }

    @Override
    public void renderAll(Collection<Renderable> renderables) {
        for (Renderable renderable : renderables) {
            if (renderable instanceof VertexShape)
                renderVertexShape((VertexShape) renderable);
            else if (renderable instanceof VaoObject)
                renderVaoObject((VaoObject) renderable);
            else if (renderable instanceof Ellipse)
                renderEllipse((Ellipse) renderable);
            else if (renderable instanceof Text) {
                Text text = (Text) renderable;
                renderText(text.getSequence(), text.getFontMap(), text.getColor(), text.modelMatrix());
            }
            else if (renderable instanceof TexturedObject)
                renderTexturedObject((TexturedObject) renderable);
        }
    }

    @Override
    public void renderTexture(Texture2D texture2D, Mat4f transformationMatrix) {
        texturedObjectRenderer.renderTexture(texture2D, transformationMatrix, camera);
    }

    @Override
    public void renderTexturedObject(TexturedObject texturedObject) {
        texturedObjectRenderer.render(texturedObject, camera);
    }

    @Override
    public void renderText(CharSequence text, FontMap font, Vec3f color, Mat4f transformationMatrix) {
        textRenderer.renderText(text, font, color, transformationMatrix, camera);
    }

    @Override
    public void renderVertexShape(VertexShape shape) {
        vertexShapeRenderer.render(shape, camera);
    }

    @Override
    public void renderVaoObject(VaoObject object) {
        vaoObjectRenderer.render(object, camera);
    }

    @Override
    public void renderEllipse(Ellipse ellipse) {
        ellipseRenderer.render(ellipse, camera);
    }
}
