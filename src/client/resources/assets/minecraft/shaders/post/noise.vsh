#version 150

in vec2 position;
in vec2 uvsIn;

out vec2 uv;

void main() {
    uv = uvsIn;
    gl_Position = vec4(position, 0.0, 1.0);
}