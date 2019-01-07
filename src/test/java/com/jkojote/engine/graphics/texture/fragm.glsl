#version 320 es

precision mediump float;

out vec4 outColor;
in vec2 texCoords;

uniform sampler2D sampler;

void main() {
    outColor = texture(sampler, texCoords);
}
