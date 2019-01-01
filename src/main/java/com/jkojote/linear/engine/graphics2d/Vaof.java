package com.jkojote.linear.engine.graphics2d;

import com.jkojote.linear.engine.ReleasableResource;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * Vertex Array Object
 */
public final class Vaof implements ReleasableResource {

    private int vao;

    private int[] buffers;

    private final int capacity;

    private int lastBuffer = -1;

    /**
     * @param capacity max number of buffers that this vao can contain
     */
    public Vaof(int capacity) {
        this.buffers = new int[capacity];
        this.capacity = capacity;
        this.vao = glGenVertexArrays();
    }

    /**
     * Binds this vao to the context
     */
    public void bind() {
        glBindVertexArray(vao);
    }

    /**
     * Unbinds this vao from the context
     */
    public void unbind() {
        glBindVertexArray(0);
    }

    /**
     * @return max number of buffers that this vao can contain
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * @return actual number of buffers that this vao contains
     */
    public int getSize() {
        return lastBuffer + 1;
    }

    public int getVao() {
        return vao;
    }

    @SuppressWarnings("Duplicates")
    public Vaof addArrayBuffer(float[] data, int usage, int index, int size, int stride, long pointer) {
        if (lastBuffer == capacity - 1) {
            throw new IndexOutOfBoundsException("cannot add buffer");
        }
        int vbo = glGenBuffers();
        buffers[++lastBuffer] = vbo;
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, data, usage);
        glVertexAttribPointer(index, size, GL_FLOAT, false, stride, pointer);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        return this;
    }

    @SuppressWarnings("Duplicates")
    public Vaof addArrayBuffer(FloatBuffer data, int usage, int index, int size, int stride, long pointer) {
        if (lastBuffer == capacity - 1) {
            throw new IndexOutOfBoundsException("cannot add buffer");
        }
        int vbo = glGenBuffers();
        buffers[++lastBuffer] = vbo;
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, data, usage);
        glVertexAttribPointer(index, size, GL_FLOAT, false, stride, pointer);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        return this;
    }

    @Override
    public void release() {
        glBindVertexArray(0);
        glDeleteBuffers(buffers);
        glDeleteVertexArrays(vao);
    }
}
