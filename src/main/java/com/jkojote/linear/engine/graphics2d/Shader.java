package com.jkojote.linear.engine.graphics2d;

import com.jkojote.linear.engine.shared.Releasable;
import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.math.Vec3f;
import com.jkojote.linear.engine.utils.FileUtils;

import java.io.*;
import java.net.URL;

import static org.lwjgl.opengl.GL20.*;


/**
 * Program object that combines vertex and fragment shaders.
 * The purpose is to encapsulate control over using shaders in programObject.
 */
public final class Shader implements Releasable {

    private int programObject;

    private boolean released;

    private Shader(String vertex, String fragment) {
        // load vertex shader
        int vertexProg = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexProg, vertex);
        glCompileShader(vertexProg);
        int vertexCompileStatus = glGetShaderi(vertexProg, GL_COMPILE_STATUS);
        if (vertexCompileStatus == 0) {
            throw new ShaderCreationException(glGetShaderInfoLog(vertexProg));
        }
        // load fragment shader
        int fragmentProg = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentProg, fragment);
        glCompileShader(fragmentProg);
        int fragmentCompilation = glGetShaderi(fragmentProg, GL_COMPILE_STATUS);
        if (fragmentCompilation == 0) {
            throw new ShaderCreationException(glGetShaderInfoLog(fragmentProg));
        }
        // load programObject object
        programObject = glCreateProgram();
        // attach shaders and link programObject
        glAttachShader(programObject, vertexProg);
        glAttachShader(programObject, fragmentProg);
        glLinkProgram(programObject);
        int linkStatus = glGetProgrami(programObject, GL_LINK_STATUS);
        if (linkStatus == 0) {
            throw new ShaderCreationException(glGetProgramInfoLog(programObject));
        }
        // detach and delete shaders
        glDetachShader(programObject, vertexProg);
        glDetachShader(programObject, vertexProg);
        glDeleteShader(vertexProg);
        glDeleteShader(fragmentProg);
    }

    /**
     * Creates shader from given files.
     * Source code for vertex shader is inside {@code vertexShaderFile} and
     * source code for fragment shader is inside {@code fragmentShaderFile}
     * @param vertexShaderFile path to the file with vertex shader
     * @param fragmentShaderFile path to the file with fragment shader
     * @return new shader
     * @throws IOException if any of the files doesn't exist or some i/o error occurred while reading
     * @throws ShaderCreationException if shader cannot be created due to specific reason
     */
    public static Shader fromFiles(String vertexShaderFile, String fragmentShaderFile)
    throws IOException{
        String vertexShader = FileUtils.readAsString(vertexShaderFile);
        String fragmentShader = FileUtils.readAsString(fragmentShaderFile);
        return new Shader(vertexShader, fragmentShader);
    }

    /**
     * Create shader from source code in {@code vertexShader} and {@code fragmentShader}
     * @param vertexShader string that contains source code for vertex shader
     * @param fragmentShader string that contains source code for fragment shader
     * @return new shader
     * @throws ShaderCreationException if shader cannot be created due to specific reason
     */
    public static Shader fromSource(String vertexShader, String fragmentShader) {
        return new Shader(vertexShader, fragmentShader);
    }

    public static Shader fromResources(String vertexShader, String fragmentShader)
    throws IOException {
        String vertexShaderSource = readResource(vertexShader);
        String fragmentShaderSource = readResource(fragmentShader);
        return fromSource(vertexShaderSource, fragmentShaderSource);

    }

    private static String readResource(String resource) throws IOException {
        ClassLoader classLoader = Shader.class.getClassLoader();
        checkResourceExists(resource, classLoader);
        return readStreamAsString(classLoader.getResourceAsStream(resource));
    }

    private static String readStreamAsString(InputStream in) throws IOException {
        byte[] buffer = new byte[4096];
        int bytesRead;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        while ((bytesRead = in.read(buffer)) > 0) {
            out.write(buffer, 0, bytesRead);
        }
        return out.toString();
    }

    private static void checkResourceExists(String resource, ClassLoader classLoader) throws IOException {
        URL url = classLoader.getResource(resource);
        if (url == null)
            throw new IOException("resource not found: " + resource);
    }

    /**
     * Bind this shader to the current rendering state.
     * After invoking this method, subsequent calls to methods
     * that use programs (such as rendering commands) will use this programObject
     */
    public void bind() {
        glUseProgram(programObject);
    }

    /**
     * Unbind this shader from the current rendering state.
     */
    public void unbind() {
        glUseProgram(0);
    }

    public boolean setUniform(String name, Vec3f vec3f) {
        int location = glGetUniformLocation(programObject, name);
        if (location == -1)
            return false;
        glUniform3f(location, vec3f.x(), vec3f.y(), vec3f.z());
        return true;
    }

    public boolean setUniform(String name, Mat4f matrix, boolean transpose) {
        int location = glGetUniformLocation(programObject, name);
        if (location == -1)
            return false;
        glUniformMatrix4fv(location, transpose, matrix.toBuffer());
        return true;
    }

    @Override
    public void release() {
        released = true;
        glUseProgram(0);
        glDeleteProgram(programObject);
    }

    @Override
    public boolean isReleased() {
        return released;
    }
}
