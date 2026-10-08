layout (location = 0) in vec4 a;
layout (location = 0) attribute vec4 b, c;

void main() {
  foo = a[3].f;
  foo = b.length();
}

layout (location = 0) attribute vec4 b, c;
