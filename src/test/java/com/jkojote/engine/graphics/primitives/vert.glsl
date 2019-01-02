#version 320 es

layout(location = 0) in vec3 position;
layout(location = 1) in vec3 color;

uniform mat4 viewProj;
uniform mat4 model;

out vec4 vertexColor;
void main() {
    gl_Position = viewProj * model * vec4(position, 1);
    vertexColor = vec4(color, 1);
}