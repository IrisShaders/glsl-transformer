// Builtin definitions for the type analysis, see BuiltinRegistry for the format.
// Lines starting with @ begin a section and list the conditions under which the
// declarations of the section are available.
// ---- 7.2 compatibility profile vertex shader built-in inputs
// everything in this file is not available in the core profile from 1.40 on
@ glsl>=110 glsl<140
const int gl_MaxTextureUnits;
const int gl_MaxTextureCoords;
const int gl_MaxLights;

@ glsl>=110 glsl<140 vert
in vec4 gl_Color;
in vec4 gl_SecondaryColor;
in vec3 gl_Normal;
in vec4 gl_Vertex;
in vec4 gl_MultiTexCoord0;
in vec4 gl_MultiTexCoord1;
in vec4 gl_MultiTexCoord2;
in vec4 gl_MultiTexCoord3;
in vec4 gl_MultiTexCoord4;
in vec4 gl_MultiTexCoord5;
in vec4 gl_MultiTexCoord6;
in vec4 gl_MultiTexCoord7;
in float gl_FogCoord;
vec4 ftransform();

// ---- 7.1.1 compatibility profile built-in outputs
@ glsl>=110 glsl<140 vert geom tesc tese
out vec4 gl_FrontColor;
out vec4 gl_BackColor;
out vec4 gl_FrontSecondaryColor;
out vec4 gl_BackSecondaryColor;
out vec4 gl_TexCoord[];
out float gl_FogFragCoord;
out vec4 gl_ClipVertex;

// ---- 7.1.1 compatibility profile fragment shader built-in inputs
@ glsl>=110 glsl<140 frag
in vec4 gl_Color;
in vec4 gl_SecondaryColor;
in vec4 gl_TexCoord[];
in float gl_FogFragCoord;

// ---- 7.4.1 compatibility profile state
@ glsl>=110 glsl<140
uniform mat4 gl_ModelViewMatrix;
uniform mat4 gl_ProjectionMatrix;
uniform mat4 gl_ModelViewProjectionMatrix;
uniform mat4 gl_TextureMatrix[gl_MaxTextureCoords];
uniform mat3 gl_NormalMatrix;
uniform mat4 gl_ModelViewMatrixInverse;
uniform mat4 gl_ProjectionMatrixInverse;
uniform mat4 gl_ModelViewProjectionMatrixInverse;
uniform mat4 gl_TextureMatrixInverse[gl_MaxTextureCoords];
uniform mat4 gl_ModelViewMatrixTranspose;
uniform mat4 gl_ProjectionMatrixTranspose;
uniform mat4 gl_ModelViewProjectionMatrixTranspose;
uniform mat4 gl_TextureMatrixTranspose[gl_MaxTextureCoords];
uniform mat4 gl_ModelViewMatrixInverseTranspose;
uniform mat4 gl_ProjectionMatrixInverseTranspose;
uniform mat4 gl_ModelViewProjectionMatrixInverseTranspose;
uniform mat4 gl_TextureMatrixInverseTranspose[gl_MaxTextureCoords];
uniform float gl_NormalScale;
uniform vec4 gl_ClipPlane[gl_MaxClipPlanes];

struct gl_PointParameters {
  float size;
  float sizeMin;
  float sizeMax;
  float fadeThresholdSize;
  float distanceConstantAttenuation;
  float distanceLinearAttenuation;
  float distanceQuadraticAttenuation;
};
uniform gl_PointParameters gl_Point;

struct gl_MaterialParameters {
  vec4 emission;
  vec4 ambient;
  vec4 diffuse;
  vec4 specular;
  float shininess;
};
uniform gl_MaterialParameters gl_FrontMaterial;
uniform gl_MaterialParameters gl_BackMaterial;

struct gl_LightSourceParameters {
  vec4 ambient;
  vec4 diffuse;
  vec4 specular;
  vec4 position;
  vec4 halfVector;
  vec3 spotDirection;
  float spotExponent;
  float spotCutoff;
  float spotCosCutoff;
  float constantAttenuation;
  float linearAttenuation;
  float quadraticAttenuation;
};
uniform gl_LightSourceParameters gl_LightSource[gl_MaxLights];

struct gl_LightModelParameters {
  vec4 ambient;
};
uniform gl_LightModelParameters gl_LightModel;

struct gl_LightModelProducts {
  vec4 sceneColor;
};
uniform gl_LightModelProducts gl_FrontLightModelProduct;
uniform gl_LightModelProducts gl_BackLightModelProduct;

struct gl_LightProducts {
  vec4 ambient;
  vec4 diffuse;
  vec4 specular;
};
uniform gl_LightProducts gl_FrontLightProduct[gl_MaxLights];
uniform gl_LightProducts gl_BackLightProduct[gl_MaxLights];

uniform vec4 gl_TextureEnvColor[gl_MaxTextureUnits];
uniform vec4 gl_EyePlaneS[gl_MaxTextureCoords];
uniform vec4 gl_EyePlaneT[gl_MaxTextureCoords];
uniform vec4 gl_EyePlaneR[gl_MaxTextureCoords];
uniform vec4 gl_EyePlaneQ[gl_MaxTextureCoords];
uniform vec4 gl_ObjectPlaneS[gl_MaxTextureCoords];
uniform vec4 gl_ObjectPlaneT[gl_MaxTextureCoords];
uniform vec4 gl_ObjectPlaneR[gl_MaxTextureCoords];
uniform vec4 gl_ObjectPlaneQ[gl_MaxTextureCoords];

struct gl_FogParameters {
  vec4 color;
  float density;
  float start;
  float end;
  float scale;
};
uniform gl_FogParameters gl_Fog;
