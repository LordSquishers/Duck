#version 400 core

in vec2 _texCoords;
in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCameraVector;
in float visibility;

out vec4 _color;

uniform sampler2D texSampler;

uniform vec3 pointLightColor;
uniform vec3 skyColor;

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

    vec4 textureColor = texture(texSampler, _texCoords);
    if(textureColor.a < 0.5) { discard; }

    _color = vec4(diffuse, 1.0) * textureColor + vec4(finalSpecular, 1.0);
    _color = mix( vec4(skyColor, 1.0), _color, visibility );
}