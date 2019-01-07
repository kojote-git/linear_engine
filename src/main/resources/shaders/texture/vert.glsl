#version 320 es

layout(location = 0) in vec3 pos;
layout(location = 1) in vec2 inTexCoords;

uniform mat4 pv;
uniform mat4 model;

out vec2 texCoords;

void main() {
    gl_Position = pv * model * vec4(pos, 1);
    texCoords = inTexCoords;
}

