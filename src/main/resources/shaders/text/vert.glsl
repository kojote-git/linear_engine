#version 320 es

layout(location = 0) in vec2 pos;
layout(location = 1) in vec3 color;
layout(location = 2) in vec2 texCoords;

uniform mat4 pv;
uniform mat4 model;

out vec4 vertexColor;
out vec2 fTexCoords;

void main() {
    gl_Position = pv *  model * vec4(pos.x, pos.y, 0, 1);
    vertexColor = vec4(color, 1);
    fTexCoords = texCoords;
}