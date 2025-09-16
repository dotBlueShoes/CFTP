#version 150

uniform sampler2D _MainTex; // required by Minecraft shader system
uniform float u_time;       // passed in from Minecraft

in vec2 uv;

out vec4 FragColor;

// Simple pseudo-random
float random(vec2 st) {
    return fract(sin(dot(st.xy, vec2(12.9898,78.233))) * 43758.5453123);
}

void main() {
    float n = random(uv + u_time * 0.5); // noise moves over time
    FragColor = vec4(vec3(n), 0.2);      // alpha = 0.2 for overlay
}