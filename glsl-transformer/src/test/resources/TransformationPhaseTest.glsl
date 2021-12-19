#version 5432

#extension extension_name: require

layout(location = 0) out vec3 layoutOne;
layout(location = 1) out vec3 layoutTwo;

uniform sampler2D Sampler;

varying vec2 varyVec;

struct Struct {
    vec4 vector;
    float number;
};

#define DIRECTIVE_NAME DIRECTIVE_VALUE

Struct arrayName[arraySize];

void functionDef(const in float param) {
  functionCall();
}
