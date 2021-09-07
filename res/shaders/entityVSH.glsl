#version 400 core

in vec3 pos;
in vec2 texCoords;

out vec2 _texCoords;

uniform mat4 transformMat;
uniform mat4 projectionMat;
uniform mat4 viewMat;

void main(void) {
    gl_Position = projectionMat * viewMat * transformMat * vec4(pos, 1.0);
    _texCoords = texCoords;
}