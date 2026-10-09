// Builtin definitions for the type analysis, see BuiltinRegistry for the format.
// Lines starting with @ begin a section and list the conditions under which the
// declarations of the section are available.
// ---- 7.3 built-in constants
@
const int gl_MaxVertexAttribs;
const int gl_MaxVertexTextureImageUnits;
const int gl_MaxCombinedTextureImageUnits;
const int gl_MaxTextureImageUnits;
const int gl_MaxDrawBuffers;

@ glsl>=110 es>=100 es<300
const int gl_MaxVertexUniformVectors;
const int gl_MaxFragmentUniformVectors;
const int gl_MaxVaryingVectors;

@ glsl>=410 es>=300
const int gl_MaxVertexUniformVectors;
const int gl_MaxFragmentUniformVectors;
const int gl_MaxVertexOutputVectors;
const int gl_MaxFragmentInputVectors;

@ glsl>=130 es>=300
const int gl_MinProgramTexelOffset;
const int gl_MaxProgramTexelOffset;

@ glsl>=110
const int gl_MaxVertexUniformComponents;
const int gl_MaxFragmentUniformComponents;
const int gl_MaxVaryingFloats;
const int gl_MaxClipPlanes;

@ glsl>=130 ext:GL_EXT_clip_cull_distance ext:GL_APPLE_clip_distance
const int gl_MaxClipDistances;
const int gl_MaxVaryingComponents;

@ glsl>=150 es>=320 ext:GL_EXT_geometry_shader ext:GL_OES_geometry_shader ext:GL_ARB_geometry_shader4
const int gl_MaxVertexOutputComponents;
const int gl_MaxGeometryInputComponents;
const int gl_MaxGeometryOutputComponents;
const int gl_MaxFragmentInputComponents;
const int gl_MaxGeometryTextureImageUnits;
const int gl_MaxGeometryOutputVertices;
const int gl_MaxGeometryTotalOutputComponents;
const int gl_MaxGeometryUniformComponents;
const int gl_MaxGeometryVaryingComponents;

@ glsl>=400 es>=320 ext:GL_ARB_tessellation_shader ext:GL_EXT_tessellation_shader ext:GL_OES_tessellation_shader
const int gl_MaxTessControlInputComponents;
const int gl_MaxTessControlOutputComponents;
const int gl_MaxTessControlTextureImageUnits;
const int gl_MaxTessControlUniformComponents;
const int gl_MaxTessControlTotalOutputComponents;
const int gl_MaxTessEvaluationInputComponents;
const int gl_MaxTessEvaluationOutputComponents;
const int gl_MaxTessEvaluationTextureImageUnits;
const int gl_MaxTessEvaluationUniformComponents;
const int gl_MaxTessPatchComponents;
const int gl_MaxPatchVertices;
const int gl_MaxTessGenLevel;

@ glsl>=410
const int gl_MaxViewports;

// glslang has these from 1.30 on
@ glsl>=130 es>=310 ext:GL_ARB_shader_atomic_counters ext:GL_ARB_shader_image_load_store ext:GL_ARB_compute_shader
const int gl_MaxImageUnits;
const int gl_MaxCombinedImageUniforms;
const int gl_MaxVertexImageUniforms;
const int gl_MaxTessControlImageUniforms;
const int gl_MaxTessEvaluationImageUniforms;
const int gl_MaxGeometryImageUniforms;
const int gl_MaxFragmentImageUniforms;
const int gl_MaxComputeImageUniforms;
const int gl_MaxCombinedShaderOutputResources;
const int gl_MaxImageSamples;
const int gl_MaxVertexAtomicCounters;
const int gl_MaxTessControlAtomicCounters;
const int gl_MaxTessEvaluationAtomicCounters;
const int gl_MaxGeometryAtomicCounters;
const int gl_MaxFragmentAtomicCounters;
const int gl_MaxComputeAtomicCounters;
const int gl_MaxCombinedAtomicCounters;
const int gl_MaxAtomicCounterBindings;
const int gl_MaxVertexAtomicCounterBuffers;
const int gl_MaxTessControlAtomicCounterBuffers;
const int gl_MaxTessEvaluationAtomicCounterBuffers;
const int gl_MaxGeometryAtomicCounterBuffers;
const int gl_MaxFragmentAtomicCounterBuffers;
const int gl_MaxComputeAtomicCounterBuffers;
const int gl_MaxCombinedAtomicCounterBuffers;
const int gl_MaxAtomicCounterBufferSize;

@ glsl>=430 es>=310 ext:GL_ARB_compute_shader
const ivec3 gl_MaxComputeWorkGroupCount;
const ivec3 gl_MaxComputeWorkGroupSize;
const int gl_MaxComputeUniformComponents;
const int gl_MaxComputeTextureImageUnits;

@ glsl>=440
const int gl_MaxTransformFeedbackBuffers;
const int gl_MaxTransformFeedbackInterleavedComponents;

@ glsl>=450 es>=320 ext:GL_OES_sample_variables ext:GL_ARB_sample_shading
const int gl_MaxSamples;

@ glsl>=450 ext:GL_EXT_clip_cull_distance ext:GL_ARB_cull_distance
const int gl_MaxCullDistances;
const int gl_MaxCombinedClipAndCullDistances;

// ---- 7.4 built-in uniform state
@
struct gl_DepthRangeParameters {
  float near;
  float far;
  float diff;
};
uniform gl_DepthRangeParameters gl_DepthRange;

@ glsl>=400 es>=320 ext:GL_OES_sample_variables ext:GL_ARB_sample_shading
uniform int gl_NumSamples;

// ---- 7.1 vertex shader special variables
@ vert
out vec4 gl_Position;
out float gl_PointSize;

@ glsl>=130 es>=300 ext:GL_EXT_gpu_shader4 vert
in int gl_VertexID;

@ glsl>=140 es>=300 ext:GL_ARB_draw_instanced ext:GL_EXT_draw_instanced vert
in int gl_InstanceID;

@ ext:GL_ARB_draw_instanced vert
in int gl_InstanceIDARB;

// the names of these in Vulkan, which cannot be distinguished from OpenGL
@ glsl>=140 es>=310 vert
in int gl_VertexIndex;
in int gl_InstanceIndex;

@ glsl>=460 ext:GL_ARB_shader_draw_parameters vert
in int gl_DrawID;
in int gl_BaseVertex;
in int gl_BaseInstance;

@ ext:GL_ARB_shader_draw_parameters vert
in int gl_DrawIDARB;
in int gl_BaseVertexARB;
in int gl_BaseInstanceARB;

// ---- tessellation and geometry shader special variables
// the output versions of variables that are both inputs and outputs come first
// so that they can be written to if the stage is not known
@ glsl>=150 es>=320 ext:GL_EXT_geometry_shader ext:GL_OES_geometry_shader ext:GL_ARB_geometry_shader4 ext:GL_EXT_geometry_shader4 geom
out vec4 gl_Position;
out float gl_PointSize;
out int gl_PrimitiveID;
out int gl_Layer;
in int gl_PrimitiveIDIn;
in gl_PerVertex {
  vec4 gl_Position;
  float gl_PointSize;
  float gl_ClipDistance[];
  float gl_CullDistance[];
} gl_in[];

@ glsl>=400 es>=320 ext:GL_ARB_gpu_shader5 ext:GL_EXT_geometry_shader ext:GL_OES_geometry_shader ext:GL_ARB_tessellation_shader ext:GL_EXT_tessellation_shader ext:GL_OES_tessellation_shader geom tesc
in int gl_InvocationID;

@ glsl>=410 es>=320 ext:GL_ARB_viewport_array ext:GL_OES_viewport_array geom
out int gl_ViewportIndex;

@ ext:GL_ARB_shader_viewport_layer_array ext:GL_AMD_vertex_shader_layer ext:GL_AMD_vertex_shader_viewport_index ext:GL_NV_viewport_array2 vert tese
out int gl_Layer;
out int gl_ViewportIndex;

@ glsl>=400 es>=320 ext:GL_ARB_tessellation_shader ext:GL_EXT_tessellation_shader ext:GL_OES_tessellation_shader tese
out vec4 gl_Position;
out float gl_PointSize;
in vec3 gl_TessCoord;

@ glsl>=400 es>=320 ext:GL_ARB_tessellation_shader ext:GL_EXT_tessellation_shader ext:GL_OES_tessellation_shader tesc
out gl_PerVertex {
  vec4 gl_Position;
  float gl_PointSize;
  float gl_ClipDistance[];
  float gl_CullDistance[];
} gl_out[];

@ glsl>=400 es>=320 ext:GL_ARB_tessellation_shader ext:GL_EXT_tessellation_shader ext:GL_OES_tessellation_shader tesc tese
in gl_PerVertex {
  vec4 gl_Position;
  float gl_PointSize;
  float gl_ClipDistance[];
  float gl_CullDistance[];
} gl_in[gl_MaxPatchVertices];
in int gl_PatchVerticesIn;
in int gl_PrimitiveID;
patch out float gl_TessLevelOuter[4];
patch out float gl_TessLevelInner[2];

@ glsl>=130 ext:GL_EXT_clip_cull_distance ext:GL_APPLE_clip_distance vert geom tese
out float gl_ClipDistance[];

@ glsl>=450 ext:GL_EXT_clip_cull_distance ext:GL_ARB_cull_distance vert geom tese
out float gl_CullDistance[];

// ---- 7.1 fragment shader special variables
@ frag
in vec4 gl_FragCoord;
in bool gl_FrontFacing;

@ glsl>=110 es>=300 ext:GL_EXT_frag_depth frag
out float gl_FragDepth;

@ ext:GL_EXT_frag_depth frag
out float gl_FragDepthEXT;

@ glsl>=120 es>=100 frag
in vec2 gl_PointCoord;

// removed from the core profile in 4.20, like glslang does
@ glsl>=110 glsl<420 es>=100 es<300 frag
out vec4 gl_FragColor;
out vec4 gl_FragData[gl_MaxDrawBuffers];

@ glsl>=130 ext:GL_EXT_clip_cull_distance ext:GL_APPLE_clip_distance frag
in float gl_ClipDistance[];

@ glsl>=450 ext:GL_EXT_clip_cull_distance ext:GL_ARB_cull_distance frag
in float gl_CullDistance[];

@ glsl>=150 es>=320 ext:GL_EXT_geometry_shader ext:GL_OES_geometry_shader frag
in int gl_PrimitiveID;

@ glsl>=400 es>=320 ext:GL_OES_sample_variables ext:GL_ARB_sample_shading frag
in int gl_SampleID;
in vec2 gl_SamplePosition;
in int gl_SampleMaskIn[];
out int gl_SampleMask[];

@ glsl>=430 es>=320 ext:GL_EXT_geometry_shader ext:GL_OES_geometry_shader ext:GL_ARB_fragment_layer_viewport frag
in int gl_Layer;

@ glsl>=430 ext:GL_ARB_fragment_layer_viewport ext:GL_OES_viewport_array frag
in int gl_ViewportIndex;

@ glsl>=450 es>=310 frag
in bool gl_HelperInvocation;

// ---- 7.1 compute shader special variables
@ glsl>=430 es>=310 ext:GL_ARB_compute_shader comp
in uvec3 gl_NumWorkGroups;
const uvec3 gl_WorkGroupSize;
in uvec3 gl_WorkGroupID;
in uvec3 gl_LocalInvocationID;
in uvec3 gl_GlobalInvocationID;
in uint gl_LocalInvocationIndex;
