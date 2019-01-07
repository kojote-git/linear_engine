#version 320 es

precision mediump float;
in vec4 vertexColor;
out vec4 fragmenColor;

void main() {
    fragmenColor = vertexColor;
}