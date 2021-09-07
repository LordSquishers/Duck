#version 400 core

in vec2 _texCoords;

out vec4 _color;

uniform sampler2D texSampler;

void main(void) {
    _color = texture(texSampler, _texCoords);
}