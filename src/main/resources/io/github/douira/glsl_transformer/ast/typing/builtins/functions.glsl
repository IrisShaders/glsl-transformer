// Builtin definitions for the type analysis, see BuiltinRegistry for the format.
// Lines starting with @ begin a section and list the conditions under which the
// declarations of the section are available.
// ---- 8.1 angle and trigonometry functions
@
genType radians(genType degrees);
genType degrees(genType radians);
genType sin(genType angle);
genType cos(genType angle);
genType tan(genType angle);
genType asin(genType x);
genType acos(genType x);
genType atan(genType y, genType x);
genType atan(genType y_over_x);

@ glsl>=130 es>=300
genType sinh(genType x);
genType cosh(genType x);
genType tanh(genType x);
genType asinh(genType x);
genType acosh(genType x);
genType atanh(genType x);

// ---- 8.2 exponential functions
@
genType pow(genType x, genType y);
genType exp(genType x);
genType log(genType x);
genType exp2(genType x);
genType log2(genType x);
genType sqrt(genType x);
genType inversesqrt(genType x);

// ---- 8.3 common functions
@
genType abs(genType x);
genType sign(genType x);
genType floor(genType x);
genType ceil(genType x);
genType fract(genType x);
genType mod(genType x, float y);
genType mod(genType x, genType y);
genType min(genType x, genType y);
genType min(genType x, float y);
genType max(genType x, genType y);
genType max(genType x, float y);
genType clamp(genType x, genType minVal, genType maxVal);
genType clamp(genType x, float minVal, float maxVal);
genType mix(genType x, genType y, genType a);
genType mix(genType x, genType y, float a);
genType step(genType edge, genType x);
genType step(float edge, genType x);
genType smoothstep(genType edge0, genType edge1, genType x);
genType smoothstep(float edge0, float edge1, genType x);

@ glsl>=130 es>=300 ext:GL_EXT_gpu_shader4
genIType abs(genIType x);
genIType sign(genIType x);
genType trunc(genType x);
genType round(genType x);
genType roundEven(genType x);
genType modf(genType x, out genType i);
genIType min(genIType x, genIType y);
genIType min(genIType x, int y);
genUType min(genUType x, genUType y);
genUType min(genUType x, uint y);
genIType max(genIType x, genIType y);
genIType max(genIType x, int y);
genUType max(genUType x, genUType y);
genUType max(genUType x, uint y);
genIType clamp(genIType x, genIType minVal, genIType maxVal);
genIType clamp(genIType x, int minVal, int maxVal);
genUType clamp(genUType x, genUType minVal, genUType maxVal);
genUType clamp(genUType x, uint minVal, uint maxVal);
genType mix(genType x, genType y, genBType a);
genBType isnan(genType x);
genBType isinf(genType x);

@ glsl>=330 es>=300 ext:GL_ARB_shader_bit_encoding ext:GL_ARB_gpu_shader5
genIType floatBitsToInt(genType value);
genUType floatBitsToUint(genType value);
genType intBitsToFloat(genIType value);
genType uintBitsToFloat(genUType value);

@ glsl>=400 es>=320 ext:GL_ARB_gpu_shader5 ext:GL_EXT_gpu_shader5 ext:GL_OES_gpu_shader5
genType fma(genType a, genType b, genType c);

@ glsl>=400 es>=310 ext:GL_ARB_gpu_shader5
genType frexp(genType x, out genIType exp);
genType ldexp(genType x, genIType exp);

@ glsl>=450 es>=310
genIType mix(genIType x, genIType y, genBType a);
genUType mix(genUType x, genUType y, genBType a);
genBType mix(genBType x, genBType y, genBType a);

// ---- double precision versions of the functions above
@ glsl>=400 ext:GL_ARB_gpu_shader_fp64
genDType sqrt(genDType x);
genDType inversesqrt(genDType x);
genDType abs(genDType x);
genDType sign(genDType x);
genDType floor(genDType x);
genDType trunc(genDType x);
genDType round(genDType x);
genDType roundEven(genDType x);
genDType ceil(genDType x);
genDType fract(genDType x);
genDType mod(genDType x, double y);
genDType mod(genDType x, genDType y);
genDType modf(genDType x, out genDType i);
genDType min(genDType x, genDType y);
genDType min(genDType x, double y);
genDType max(genDType x, genDType y);
genDType max(genDType x, double y);
genDType clamp(genDType x, genDType minVal, genDType maxVal);
genDType clamp(genDType x, double minVal, double maxVal);
genDType mix(genDType x, genDType y, genDType a);
genDType mix(genDType x, genDType y, double a);
genDType mix(genDType x, genDType y, genBType a);
genDType step(genDType edge, genDType x);
genDType step(double edge, genDType x);
genDType smoothstep(genDType edge0, genDType edge1, genDType x);
genDType smoothstep(double edge0, double edge1, genDType x);
genBType isnan(genDType x);
genBType isinf(genDType x);
genDType fma(genDType a, genDType b, genDType c);
genDType frexp(genDType x, out genIType exp);
genDType ldexp(genDType x, genIType exp);
double packDouble2x32(uvec2 v);
uvec2 unpackDouble2x32(double v);
double length(genDType x);
double distance(genDType p0, genDType p1);
double dot(genDType x, genDType y);
dvec3 cross(dvec3 x, dvec3 y);
genDType normalize(genDType x);
genDType faceforward(genDType N, genDType I, genDType Nref);
genDType reflect(genDType I, genDType N);
genDType refract(genDType I, genDType N, double eta);
dmat matrixCompMult(dmat x, dmat y);
dmat2 outerProduct(dvec2 c, dvec2 r);
dmat3 outerProduct(dvec3 c, dvec3 r);
dmat4 outerProduct(dvec4 c, dvec4 r);
dmat2x3 outerProduct(dvec3 c, dvec2 r);
dmat3x2 outerProduct(dvec2 c, dvec3 r);
dmat2x4 outerProduct(dvec4 c, dvec2 r);
dmat4x2 outerProduct(dvec2 c, dvec4 r);
dmat3x4 outerProduct(dvec4 c, dvec3 r);
dmat4x3 outerProduct(dvec3 c, dvec4 r);
dmat2 transpose(dmat2 m);
dmat3 transpose(dmat3 m);
dmat4 transpose(dmat4 m);
dmat2x3 transpose(dmat3x2 m);
dmat3x2 transpose(dmat2x3 m);
dmat2x4 transpose(dmat4x2 m);
dmat4x2 transpose(dmat2x4 m);
dmat3x4 transpose(dmat4x3 m);
dmat4x3 transpose(dmat3x4 m);
double determinant(dmat2 m);
double determinant(dmat3 m);
double determinant(dmat4 m);
dmat2 inverse(dmat2 m);
dmat3 inverse(dmat3 m);
dmat4 inverse(dmat4 m);
bvec lessThan(dvec x, dvec y);
bvec lessThanEqual(dvec x, dvec y);
bvec greaterThan(dvec x, dvec y);
bvec greaterThanEqual(dvec x, dvec y);
bvec equal(dvec x, dvec y);
bvec notEqual(dvec x, dvec y);

// ---- 8.4 floating-point pack and unpack functions
@ glsl>=400 es>=300 ext:GL_ARB_shading_language_packing
uint packUnorm2x16(vec2 v);
uint packSnorm2x16(vec2 v);
vec2 unpackUnorm2x16(uint p);
vec2 unpackSnorm2x16(uint p);

@ glsl>=400 es>=310 ext:GL_ARB_shading_language_packing
uint packUnorm4x8(vec4 v);
uint packSnorm4x8(vec4 v);
vec4 unpackUnorm4x8(uint p);
vec4 unpackSnorm4x8(uint p);

@ glsl>=420 es>=300 ext:GL_ARB_shading_language_packing
uint packHalf2x16(vec2 v);
vec2 unpackHalf2x16(uint v);

// ---- 8.5 geometric functions
@
float length(genType x);
float distance(genType p0, genType p1);
float dot(genType x, genType y);
vec3 cross(vec3 x, vec3 y);
genType normalize(genType x);
genType faceforward(genType N, genType I, genType Nref);
genType reflect(genType I, genType N);
genType refract(genType I, genType N, float eta);

// ---- 8.6 matrix functions
@
mat matrixCompMult(mat x, mat y);

@ glsl>=120 es>=300
mat2 outerProduct(vec2 c, vec2 r);
mat3 outerProduct(vec3 c, vec3 r);
mat4 outerProduct(vec4 c, vec4 r);
mat2x3 outerProduct(vec3 c, vec2 r);
mat3x2 outerProduct(vec2 c, vec3 r);
mat2x4 outerProduct(vec4 c, vec2 r);
mat4x2 outerProduct(vec2 c, vec4 r);
mat3x4 outerProduct(vec4 c, vec3 r);
mat4x3 outerProduct(vec3 c, vec4 r);
mat2 transpose(mat2 m);
mat3 transpose(mat3 m);
mat4 transpose(mat4 m);
mat2x3 transpose(mat3x2 m);
mat3x2 transpose(mat2x3 m);
mat2x4 transpose(mat4x2 m);
mat4x2 transpose(mat2x4 m);
mat3x4 transpose(mat4x3 m);
mat4x3 transpose(mat3x4 m);

@ glsl>=150 es>=300
float determinant(mat2 m);
float determinant(mat3 m);
float determinant(mat4 m);
mat2 inverse(mat2 m);
mat3 inverse(mat3 m);
mat4 inverse(mat4 m);

// ---- 8.7 vector relational functions
@
bvec lessThan(vec x, vec y);
bvec lessThan(ivec x, ivec y);
bvec lessThanEqual(vec x, vec y);
bvec lessThanEqual(ivec x, ivec y);
bvec greaterThan(vec x, vec y);
bvec greaterThan(ivec x, ivec y);
bvec greaterThanEqual(vec x, vec y);
bvec greaterThanEqual(ivec x, ivec y);
bvec equal(vec x, vec y);
bvec equal(ivec x, ivec y);
bvec equal(bvec x, bvec y);
bvec notEqual(vec x, vec y);
bvec notEqual(ivec x, ivec y);
bvec notEqual(bvec x, bvec y);
bool any(bvec x);
bool all(bvec x);
bvec not(bvec x);

@ glsl>=130 es>=300 ext:GL_EXT_gpu_shader4
bvec lessThan(uvec x, uvec y);
bvec lessThanEqual(uvec x, uvec y);
bvec greaterThan(uvec x, uvec y);
bvec greaterThanEqual(uvec x, uvec y);
bvec equal(uvec x, uvec y);
bvec notEqual(uvec x, uvec y);

// ---- 8.8 integer functions
@ glsl>=400 es>=310 ext:GL_ARB_gpu_shader5
genUType uaddCarry(genUType x, genUType y, out genUType carry);
genUType usubBorrow(genUType x, genUType y, out genUType borrow);
void umulExtended(genUType x, genUType y, out genUType msb, out genUType lsb);
void imulExtended(genIType x, genIType y, out genIType msb, out genIType lsb);
genIType bitfieldExtract(genIType value, int offset, int bits);
genUType bitfieldExtract(genUType value, int offset, int bits);
genIType bitfieldInsert(genIType base, genIType insert, int offset, int bits);
genUType bitfieldInsert(genUType base, genUType insert, int offset, int bits);
genIType bitfieldReverse(genIType value);
genUType bitfieldReverse(genUType value);
genIType bitCount(genIType value);
genIType bitCount(genUType value);
genIType findLSB(genIType value);
genIType findLSB(genUType value);
genIType findMSB(genIType value);
genIType findMSB(genUType value);

// ---- 8.11 atomic counter functions
@ glsl>=420 es>=310 ext:GL_ARB_shader_atomic_counters
uint atomicCounterIncrement(atomic_uint c);
uint atomicCounterDecrement(atomic_uint c);
uint atomicCounter(atomic_uint c);

@ glsl>=460
uint atomicCounterAdd(atomic_uint c, uint data);
uint atomicCounterSubtract(atomic_uint c, uint data);
uint atomicCounterMin(atomic_uint c, uint data);
uint atomicCounterMax(atomic_uint c, uint data);
uint atomicCounterAnd(atomic_uint c, uint data);
uint atomicCounterOr(atomic_uint c, uint data);
uint atomicCounterXor(atomic_uint c, uint data);
uint atomicCounterExchange(atomic_uint c, uint data);
uint atomicCounterCompSwap(atomic_uint c, uint compare, uint data);

@ ext:GL_ARB_shader_atomic_counter_ops
uint atomicCounterAddARB(atomic_uint c, uint data);
uint atomicCounterSubtractARB(atomic_uint c, uint data);
uint atomicCounterMinARB(atomic_uint c, uint data);
uint atomicCounterMaxARB(atomic_uint c, uint data);
uint atomicCounterAndARB(atomic_uint c, uint data);
uint atomicCounterOrARB(atomic_uint c, uint data);
uint atomicCounterXorARB(atomic_uint c, uint data);
uint atomicCounterExchangeARB(atomic_uint c, uint data);
uint atomicCounterCompSwapARB(atomic_uint c, uint compare, uint data);

// ---- 8.12 atomic memory functions
@ glsl>=430 es>=310 ext:GL_ARB_shader_storage_buffer_object
iuint atomicAdd(inout iuint mem, iuint data);
iuint atomicMin(inout iuint mem, iuint data);
iuint atomicMax(inout iuint mem, iuint data);
iuint atomicAnd(inout iuint mem, iuint data);
iuint atomicOr(inout iuint mem, iuint data);
iuint atomicXor(inout iuint mem, iuint data);
iuint atomicExchange(inout iuint mem, iuint data);
iuint atomicCompSwap(inout iuint mem, iuint compare, iuint data);

// ---- 8.14 fragment processing functions
@ glsl>=110 es>=300 ext:GL_OES_standard_derivatives frag
genType dFdx(genType p);
genType dFdy(genType p);
genType fwidth(genType p);

@ glsl>=450 ext:GL_ARB_derivative_control frag
genType dFdxFine(genType p);
genType dFdyFine(genType p);
genType fwidthFine(genType p);
genType dFdxCoarse(genType p);
genType dFdyCoarse(genType p);
genType fwidthCoarse(genType p);

@ glsl>=400 es>=320 ext:GL_ARB_gpu_shader5 ext:GL_OES_shader_multisample_interpolation frag
genType interpolateAtCentroid(genType interpolant);
genType interpolateAtSample(genType interpolant, int sampleIndex);
genType interpolateAtOffset(genType interpolant, vec2 offset);

// derivatives in compute shaders
@ ext:GL_NV_compute_shader_derivatives ext:GL_KHR_compute_shader_derivatives comp
genType dFdx(genType p);
genType dFdy(genType p);
genType fwidth(genType p);
genType dFdxFine(genType p);
genType dFdyFine(genType p);
genType fwidthFine(genType p);
genType dFdxCoarse(genType p);
genType dFdyCoarse(genType p);
genType fwidthCoarse(genType p);

// ---- 8.15 noise functions
@ glsl>=110
float noise1(genType x);
vec2 noise2(genType x);
vec3 noise3(genType x);
vec4 noise4(genType x);

// ---- 8.16 geometry shader functions
@ glsl>=150 es>=320 ext:GL_EXT_geometry_shader ext:GL_OES_geometry_shader ext:GL_ARB_geometry_shader4 ext:GL_EXT_geometry_shader4 geom
void EmitVertex();
void EndPrimitive();

@ glsl>=400 ext:GL_ARB_gpu_shader5 geom
void EmitStreamVertex(int stream);
void EndStreamPrimitive(int stream);

// ---- 8.17 shader invocation control functions
@ glsl>=400 es>=320 ext:GL_ARB_tessellation_shader ext:GL_EXT_tessellation_shader ext:GL_OES_tessellation_shader tesc
void barrier();

@ glsl>=430 es>=310 ext:GL_ARB_compute_shader comp
void barrier();

// ---- 8.18 shader memory control functions
@ glsl>=420 es>=310 ext:GL_ARB_shader_image_load_store
void memoryBarrier();

@ glsl>=430 es>=310 ext:GL_ARB_compute_shader
void memoryBarrierAtomicCounter();
void memoryBarrierBuffer();
void memoryBarrierImage();

@ glsl>=430 es>=310 ext:GL_ARB_compute_shader comp
void memoryBarrierShared();
void groupMemoryBarrier();

// ---- 8.19 shader invocation group functions
@ glsl>=460
bool anyInvocation(bool value);
bool allInvocations(bool value);
bool allInvocationsEqual(bool value);

@ ext:GL_ARB_shader_group_vote
bool anyInvocationARB(bool value);
bool allInvocationsARB(bool value);
bool allInvocationsEqualARB(bool value);
