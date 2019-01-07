package com.jkojote.engine.graphics;

import com.jkojote.linear.engine.graphics2d.Transformable;
import com.jkojote.linear.engine.math.Vec3f;
import com.jkojote.linear.engine.window.KeyCallback;

import static org.lwjgl.glfw.GLFW.*;

public class TransformationController implements KeyCallback {

    private Transformable shape;

    private static final int
        ROTATE_RIGHT = 0,
        ROTATE_LEFT = 1,
        SCALE_UP = 2,
        SCALE_DOWN = 3,
        MOVE_LEFT = 4, MOVE_UP = 5, MOVE_RIGHT = 6, MOVE_DOWN = 7;

    private boolean[] pressed;

    private float translationDelta = 1;

    private float rotationDelta = 5;

    private float scaleFactorDelta = 0.1f;

    public TransformationController(Transformable shape) {
        this.shape = shape;
        this.pressed = new boolean[8];
    }

    public void setTranslationDelta(float translationDelta) {
        this.translationDelta = translationDelta;
    }

    public void setRotationDelta(float rotationDelta) {
        this.rotationDelta = rotationDelta;
    }

    public void setScaleFactorDelta(float scaleFactorDelta) {
        this.scaleFactorDelta = scaleFactorDelta;
    }

    @Override
    public void perform(int key, int action, int mods) {
        if (action == GLFW_PRESS) {
            int actionPressed = translateKey(key);
            if (actionPressed == -1)
                return;
            pressed[actionPressed] = true;
        } else if (action == GLFW_RELEASE) {
            int actionPressed = translateKey(key);
            if (actionPressed == -1)
                return;
            pressed[actionPressed] = false;
        }
    }

    private int translateKey(int originalKey) {
        switch (originalKey) {
            case GLFW_KEY_LEFT:
                return MOVE_LEFT;
            case GLFW_KEY_UP:
                return MOVE_UP;
            case GLFW_KEY_RIGHT:
                return MOVE_RIGHT;
            case GLFW_KEY_DOWN:
                return MOVE_DOWN;
            case GLFW_KEY_A:
                return ROTATE_LEFT;
            case GLFW_KEY_D:
                return ROTATE_RIGHT;
            case GLFW_KEY_W:
                return SCALE_UP;
            case GLFW_KEY_S:
                return SCALE_DOWN;
            default:
                return -1;
        }
    }

    public void update() {
        for (int i = 0; i < pressed.length; i++) {
            if (pressed[i]) {
                switch (i) {
                    case MOVE_LEFT:
                        shape.setTranslation(shape.getTranslation().add(-translationDelta, 0, 0));
                        break;
                    case MOVE_RIGHT:
                        shape.setTranslation(shape.getTranslation().add(translationDelta, 0, 0));
                        break;
                    case MOVE_UP:
                        shape.setTranslation(shape.getTranslation().add(0, translationDelta, 0));
                        break;
                    case MOVE_DOWN:
                        shape.setTranslation(shape.getTranslation().add(0, -translationDelta, 0));
                        break;
                    case ROTATE_RIGHT:
                        shape.setRotationAngle(shape.getRotationAngle() - rotationDelta);
                        break;
                    case ROTATE_LEFT:
                        shape.setRotationAngle(shape.getRotationAngle() + rotationDelta);
                        break;
                    case SCALE_UP:
                        shape.setScaleFactor(shape.getScaleFactor() + scaleFactorDelta);
                        break;
                    case SCALE_DOWN:
                        shape.setScaleFactor(shape.getScaleFactor() - scaleFactorDelta);
                        break;
                }
            }
        }
    }
}
