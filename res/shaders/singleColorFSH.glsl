#version 400 core

in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCameraVector;
in float visibility;

out vec4 _color;

uniform vec3 pointLightColor;
uniform vec3 skyColor;
uniform vec3 renderColor;

uniform float minBrightness;
uniform float shineDamper;
uniform float reflectivity;

void main(void) {

    vec3 uNormal = normalize(surfaceNormal);
    vec3 uLightVector = normalize(toLightVector);
    vec3 uCameraVector = normalize(toCameraVector);

    float nDot1 = dot(uNormal, uLightVector);
    float brightness = max(nDot1, minBrightness);
    vec3 diffuse = brightness * pointLightColor;

    vec3 lightDirection = -uLightVector;

    vec4 textureColor = vec4(renderColor, 1.0);

    _color = vec4(diffuse, 1.0) * textureColor;
    _color = mix( vec4(skyColor, 1.0), _color, visibility );
}