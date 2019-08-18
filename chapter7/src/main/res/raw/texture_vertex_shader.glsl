uniform mat4 u_Matrix;

attribute vec4 a_Position;
attribute vec2 aTextureCoordinates;

varying vec2 v_TexureCoordinates;

void main(){
    v_TexureCoordinates = aTextureCoordinates;
    gl_Position = u_Matrix * a_Position;
}