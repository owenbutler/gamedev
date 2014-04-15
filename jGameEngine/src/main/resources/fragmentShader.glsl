
uniform sampler2D tex0;

uniform vec3 peturb;
uniform float currentTime;

void main() {

    if (peturb.z < 0.0) {
        gl_FragColor = texture2D(tex0, gl_TexCoord[0].st) * gl_Color;
        return;
    }

    if (currentTime > peturb.z) {

        float peturbPosition = (currentTime - peturb.z) * 0.26041666667;
        float upperPeturbPosition = peturbPosition + 0.0146484375;
        float lowerPeturbPosition = peturbPosition - 0.0146484375;

        float distance = distance(peturb.xy, gl_TexCoord[0].st);

        if (distance < upperPeturbPosition && distance > lowerPeturbPosition) {

            float diff = distance - peturbPosition;
            gl_FragColor = texture2D(tex0, gl_TexCoord[0].st + sin(diff)) * gl_Color;
            return;
        }
        
    }

    gl_FragColor = texture2D(tex0, gl_TexCoord[0].st) * gl_Color;
}
