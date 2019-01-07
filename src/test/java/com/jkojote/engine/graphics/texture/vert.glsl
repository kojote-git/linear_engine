#version 320 es

layout(location = 0) in vec3 pos;
layout(location = 1) in vec2 inTexCoords;

uniform mat4 viewProj;
uniform mat4 model;

out vec2 texCoords;

void main() {
    gl_Position = viewProj * model * vec4(pos, 1);
    texCoords = inTexCoords;
}

