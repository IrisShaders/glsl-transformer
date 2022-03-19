layout (location = 0) attribute vec4 b, c;
uniform sampler2D Sampler;
uniform vec2 bar;
attribute vec4 tang;

uniform bool uniformDecl = true;
const bool constDecl = true;

layout(std140, binding = 0) uniform ubo_DynamicState {
    mat4 ModelViewMat;
    mat4 ProjMat;
};

void main() {
  const uint baz = 4;
  foo = a[3].f;
  foo = b.length();
}
