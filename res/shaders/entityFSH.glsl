#version 400 core

in vec2 _texCoords;
in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCameraVector;

out vec4 _color;

uniform sampler2D texSampler;

uniform vec3 pointLightColor;
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
    vec3 reflectedLightDir = reflect(lightDirection, uNormal);
    float specular = max(dot(reflectedLightDir, uCameraVector), 0.0);
    float damp = pow(specular, shineDamper);
    vec3 finalSpecular = damp * reflectivity * pointLightColor;

    _color = vec4(diffuse, 1.0) * texture(texSampler, _texCoords) + vec4(finalSpecular, 1.0);
}