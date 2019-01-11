#version 320 es

precision mediump float;

in vec4 vertexColor;
in vec2 fTexCoords;

uniform sampler2D sampler;

out vec4 fragmenColor;

void main() {
    fragmenColor = texture(sampler, fTexCoords) * vertexColor;
}