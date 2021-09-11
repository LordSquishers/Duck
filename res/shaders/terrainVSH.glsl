#version 400 core

in vec3 pos;
in vec2 texCoords;
in vec3 normal;

out vec2 _texCoords;
out vec3 surfaceNormal;
out vec3 toLightVector;
out vec3 toCamVector;
out float visibility;

uniform mat4 transformMat;
uniform mat4 projectionMat;
uniform mat4 viewMat;

uniform vec3 pointLightPos;

uniform float useFakeLighting;

uniform float fogDensity;
uniform float gradient;

void main(void) {

    vec4 worldPos = transformMat * vec4(pos, 1.0);
    vec4 posRelativeToCam = viewMat * worldPos;
    gl_Position = projectionMat * posRelativeToCam;
    _texCoords = texCoords * 40.0;

    vec3 outputNormal = normal;
    if(useFakeLighting > 0.5) { outputNormal = vec3(0, 1, 0); }

    surfaceNormal = (transformMat * vec4(outputNormal, 0.0)).xyz;
    toLightVector = pointLightPos - worldPos.xyz;
    toCamVector = (inverse(viewMat) * vec4(0.0, 0.0, 0.0, 1.0)).xyz - worldPos.xyz;

    float distance = length(posRelativeToCam.xyz);
    visibility = clamp( exp( -pow( distance * fogDensity , gradient) ) , 0.0, 1.0);

}