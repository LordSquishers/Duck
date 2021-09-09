#version 400 core

in vec3 pos;
in vec2 texCoords;
in vec3 normal;

out vec2 _texCoords;
out vec3 surfaceNormal;
out vec3 toLightVector;
out vec3 toCamVector;

uniform mat4 transformMat;
uniform mat4 projectionMat;
uniform mat4 viewMat;

uniform vec3 pointLightPos;

void main(void) {

    vec4 worldPos = transformMat * vec4(pos, 1.0);
    gl_Position = projectionMat * viewMat * worldPos;
    _texCoords = texCoords;

    surfaceNormal = (transformMat * vec4(normal, 0.0)).xyz;
    toLightVector = pointLightPos - worldPos.xyz;
    toCamVector = (inverse(viewMat) * vec4(0.0, 0.0, 0.0, 1.0)).xyz - worldPos.xyz;

}