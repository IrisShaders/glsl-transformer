// Builtin definitions for the type analysis, see BuiltinRegistry for the format.
// Lines starting with @ begin a section and list the conditions under which the
// declarations of the section are available.
// ---- 64 bit integer functions
@ ext:GL_ARB_gpu_shader_int64 ext:GL_EXT_shader_explicit_arithmetic_types ext:GL_EXT_shader_explicit_arithmetic_types_int64 ext:GL_AMD_gpu_shader_int64 ext:GL_NV_gpu_shader5
genI64Type abs(genI64Type x);
genI64Type sign(genI64Type x);
genI64Type min(genI64Type x, genI64Type y);
genI64Type min(genI64Type x, int64_t y);
genI64Type max(genI64Type x, genI64Type y);
genI64Type max(genI64Type x, int64_t y);
genI64Type clamp(genI64Type x, genI64Type minVal, genI64Type maxVal);
genI64Type clamp(genI64Type x, int64_t minVal, int64_t maxVal);
genI64Type mix(genI64Type x, genI64Type y, genBType a);
genU64Type min(genU64Type x, genU64Type y);
genU64Type min(genU64Type x, uint64_t y);
genU64Type max(genU64Type x, genU64Type y);
genU64Type max(genU64Type x, uint64_t y);
genU64Type clamp(genU64Type x, genU64Type minVal, genU64Type maxVal);
genU64Type clamp(genU64Type x, uint64_t minVal, uint64_t maxVal);
genU64Type mix(genU64Type x, genU64Type y, genBType a);
bvec lessThan(i64vec x, i64vec y);
bvec lessThanEqual(i64vec x, i64vec y);
bvec greaterThan(i64vec x, i64vec y);
bvec greaterThanEqual(i64vec x, i64vec y);
bvec equal(i64vec x, i64vec y);
bvec notEqual(i64vec x, i64vec y);
bvec lessThan(u64vec x, u64vec y);
bvec lessThanEqual(u64vec x, u64vec y);
bvec greaterThan(u64vec x, u64vec y);
bvec greaterThanEqual(u64vec x, u64vec y);
bvec equal(u64vec x, u64vec y);
bvec notEqual(u64vec x, u64vec y);

genI64Type doubleBitsToInt64(genDType value);
genU64Type doubleBitsToUint64(genDType value);
genDType int64BitsToDouble(genI64Type value);
genDType uint64BitsToDouble(genU64Type value);
int64_t packInt2x32(ivec2 v);
uint64_t packUint2x32(uvec2 v);
ivec2 unpackInt2x32(int64_t v);
uvec2 unpackUint2x32(uint64_t v);
genIType bitCount(genI64Type value);
genIType bitCount(genU64Type value);
genIType findLSB(genI64Type value);
genIType findLSB(genU64Type value);
genIType findMSB(genI64Type value);
genIType findMSB(genU64Type value);

// ---- 16 bit integer functions
@ ext:GL_EXT_shader_explicit_arithmetic_types ext:GL_EXT_shader_explicit_arithmetic_types_int16 ext:GL_AMD_gpu_shader_int16 ext:GL_NV_gpu_shader5
genI16Type abs(genI16Type x);
genI16Type sign(genI16Type x);
genI16Type min(genI16Type x, genI16Type y);
genI16Type min(genI16Type x, int16_t y);
genI16Type max(genI16Type x, genI16Type y);
genI16Type max(genI16Type x, int16_t y);
genI16Type clamp(genI16Type x, genI16Type minVal, genI16Type maxVal);
genI16Type clamp(genI16Type x, int16_t minVal, int16_t maxVal);
genI16Type mix(genI16Type x, genI16Type y, genBType a);
genU16Type min(genU16Type x, genU16Type y);
genU16Type min(genU16Type x, uint16_t y);
genU16Type max(genU16Type x, genU16Type y);
genU16Type max(genU16Type x, uint16_t y);
genU16Type clamp(genU16Type x, genU16Type minVal, genU16Type maxVal);
genU16Type clamp(genU16Type x, uint16_t minVal, uint16_t maxVal);
genU16Type mix(genU16Type x, genU16Type y, genBType a);
bvec lessThan(i16vec x, i16vec y);
bvec lessThanEqual(i16vec x, i16vec y);
bvec greaterThan(i16vec x, i16vec y);
bvec greaterThanEqual(i16vec x, i16vec y);
bvec equal(i16vec x, i16vec y);
bvec notEqual(i16vec x, i16vec y);
bvec lessThan(u16vec x, u16vec y);
bvec lessThanEqual(u16vec x, u16vec y);
bvec greaterThan(u16vec x, u16vec y);
bvec greaterThanEqual(u16vec x, u16vec y);
bvec equal(u16vec x, u16vec y);
bvec notEqual(u16vec x, u16vec y);

genI16Type float16BitsToInt16(genF16Type value);
genU16Type float16BitsToUint16(genF16Type value);
genF16Type int16BitsToFloat16(genI16Type value);
genF16Type uint16BitsToFloat16(genU16Type value);
int packInt2x16(i16vec2 v);
uint packUint2x16(u16vec2 v);
int64_t packInt4x16(i16vec4 v);
uint64_t packUint4x16(u16vec4 v);
i16vec2 unpackInt2x16(int v);
u16vec2 unpackUint2x16(uint v);
i16vec4 unpackInt4x16(int64_t v);
u16vec4 unpackUint4x16(uint64_t v);

// ---- 8 bit integer functions
@ ext:GL_EXT_shader_explicit_arithmetic_types ext:GL_EXT_shader_explicit_arithmetic_types_int8 ext:GL_NV_gpu_shader5
genI8Type abs(genI8Type x);
genI8Type sign(genI8Type x);
genI8Type min(genI8Type x, genI8Type y);
genI8Type min(genI8Type x, int8_t y);
genI8Type max(genI8Type x, genI8Type y);
genI8Type max(genI8Type x, int8_t y);
genI8Type clamp(genI8Type x, genI8Type minVal, genI8Type maxVal);
genI8Type clamp(genI8Type x, int8_t minVal, int8_t maxVal);
genI8Type mix(genI8Type x, genI8Type y, genBType a);
genU8Type min(genU8Type x, genU8Type y);
genU8Type min(genU8Type x, uint8_t y);
genU8Type max(genU8Type x, genU8Type y);
genU8Type max(genU8Type x, uint8_t y);
genU8Type clamp(genU8Type x, genU8Type minVal, genU8Type maxVal);
genU8Type clamp(genU8Type x, uint8_t minVal, uint8_t maxVal);
genU8Type mix(genU8Type x, genU8Type y, genBType a);
bvec lessThan(i8vec x, i8vec y);
bvec lessThanEqual(i8vec x, i8vec y);
bvec greaterThan(i8vec x, i8vec y);
bvec greaterThanEqual(i8vec x, i8vec y);
bvec equal(i8vec x, i8vec y);
bvec notEqual(i8vec x, i8vec y);
bvec lessThan(u8vec x, u8vec y);
bvec lessThanEqual(u8vec x, u8vec y);
bvec greaterThan(u8vec x, u8vec y);
bvec greaterThanEqual(u8vec x, u8vec y);
bvec equal(u8vec x, u8vec y);
bvec notEqual(u8vec x, u8vec y);

int16_t pack16(i8vec2 v);
uint16_t pack16(u8vec2 v);
int pack32(i8vec4 v);
uint pack32(u8vec4 v);
i8vec2 unpack8(int16_t v);
u8vec2 unpack8(uint16_t v);
i8vec4 unpack8(int v);
u8vec4 unpack8(uint v);

// ---- half precision floating point functions
@ ext:GL_EXT_shader_explicit_arithmetic_types ext:GL_EXT_shader_explicit_arithmetic_types_float16 ext:GL_AMD_gpu_shader_half_float ext:GL_NV_gpu_shader5
genF16Type radians(genF16Type x);
genF16Type degrees(genF16Type x);
genF16Type sin(genF16Type x);
genF16Type cos(genF16Type x);
genF16Type tan(genF16Type x);
genF16Type asin(genF16Type x);
genF16Type acos(genF16Type x);
genF16Type atan(genF16Type x);
genF16Type sinh(genF16Type x);
genF16Type cosh(genF16Type x);
genF16Type tanh(genF16Type x);
genF16Type asinh(genF16Type x);
genF16Type acosh(genF16Type x);
genF16Type atanh(genF16Type x);
genF16Type exp(genF16Type x);
genF16Type log(genF16Type x);
genF16Type exp2(genF16Type x);
genF16Type log2(genF16Type x);
genF16Type sqrt(genF16Type x);
genF16Type inversesqrt(genF16Type x);
genF16Type abs(genF16Type x);
genF16Type sign(genF16Type x);
genF16Type floor(genF16Type x);
genF16Type trunc(genF16Type x);
genF16Type round(genF16Type x);
genF16Type roundEven(genF16Type x);
genF16Type ceil(genF16Type x);
genF16Type fract(genF16Type x);
genF16Type normalize(genF16Type x);
genF16Type atan(genF16Type y, genF16Type x);
genF16Type pow(genF16Type x, genF16Type y);
genF16Type mod(genF16Type x, float16_t y);
genF16Type mod(genF16Type x, genF16Type y);
genF16Type modf(genF16Type x, out genF16Type i);
genF16Type min(genF16Type x, genF16Type y);
genF16Type min(genF16Type x, float16_t y);
genF16Type max(genF16Type x, genF16Type y);
genF16Type max(genF16Type x, float16_t y);
genF16Type clamp(genF16Type x, genF16Type minVal, genF16Type maxVal);
genF16Type clamp(genF16Type x, float16_t minVal, float16_t maxVal);
genF16Type mix(genF16Type x, genF16Type y, genF16Type a);
genF16Type mix(genF16Type x, genF16Type y, float16_t a);
genF16Type mix(genF16Type x, genF16Type y, genBType a);
genF16Type step(genF16Type edge, genF16Type x);
genF16Type step(float16_t edge, genF16Type x);
genF16Type smoothstep(genF16Type edge0, genF16Type edge1, genF16Type x);
genF16Type smoothstep(float16_t edge0, float16_t edge1, genF16Type x);
genBType isnan(genF16Type x);
genBType isinf(genF16Type x);
genF16Type fma(genF16Type a, genF16Type b, genF16Type c);
genF16Type frexp(genF16Type x, out genIType exp);
genF16Type ldexp(genF16Type x, genIType exp);
genF16Type frexp(genF16Type x, out genI16Type exp);
genF16Type ldexp(genF16Type x, genI16Type exp);
float16_t length(genF16Type x);
float16_t distance(genF16Type p0, genF16Type p1);
float16_t dot(genF16Type x, genF16Type y);
f16vec3 cross(f16vec3 x, f16vec3 y);
genF16Type faceforward(genF16Type N, genF16Type I, genF16Type Nref);
genF16Type reflect(genF16Type I, genF16Type N);
genF16Type refract(genF16Type I, genF16Type N, float16_t eta);
f16mat matrixCompMult(f16mat x, f16mat y);
f16mat2 outerProduct(f16vec2 c, f16vec2 r);
f16mat3 outerProduct(f16vec3 c, f16vec3 r);
f16mat4 outerProduct(f16vec4 c, f16vec4 r);
f16mat2x3 outerProduct(f16vec3 c, f16vec2 r);
f16mat3x2 outerProduct(f16vec2 c, f16vec3 r);
f16mat2x4 outerProduct(f16vec4 c, f16vec2 r);
f16mat4x2 outerProduct(f16vec2 c, f16vec4 r);
f16mat3x4 outerProduct(f16vec4 c, f16vec3 r);
f16mat4x3 outerProduct(f16vec3 c, f16vec4 r);
f16mat2 transpose(f16mat2 m);
f16mat3 transpose(f16mat3 m);
f16mat4 transpose(f16mat4 m);
f16mat2x3 transpose(f16mat3x2 m);
f16mat3x2 transpose(f16mat2x3 m);
f16mat2x4 transpose(f16mat4x2 m);
f16mat4x2 transpose(f16mat2x4 m);
f16mat3x4 transpose(f16mat4x3 m);
f16mat4x3 transpose(f16mat3x4 m);
float16_t determinant(f16mat2 m);
float16_t determinant(f16mat3 m);
float16_t determinant(f16mat4 m);
f16mat2 inverse(f16mat2 m);
f16mat3 inverse(f16mat3 m);
f16mat4 inverse(f16mat4 m);
bvec lessThan(f16vec x, f16vec y);
bvec lessThanEqual(f16vec x, f16vec y);
bvec greaterThan(f16vec x, f16vec y);
bvec greaterThanEqual(f16vec x, f16vec y);
bvec equal(f16vec x, f16vec y);
bvec notEqual(f16vec x, f16vec y);
uint packFloat2x16(f16vec2 v);
f16vec2 unpackFloat2x16(uint v);

@ ext:GL_EXT_shader_explicit_arithmetic_types ext:GL_EXT_shader_explicit_arithmetic_types_float16 ext:GL_AMD_gpu_shader_half_float ext:GL_NV_gpu_shader5 frag
genF16Type dFdx(genF16Type p);
genF16Type dFdy(genF16Type p);
genF16Type fwidth(genF16Type p);
genF16Type dFdxFine(genF16Type p);
genF16Type dFdyFine(genF16Type p);
genF16Type fwidthFine(genF16Type p);
genF16Type dFdxCoarse(genF16Type p);
genF16Type dFdyCoarse(genF16Type p);
genF16Type fwidthCoarse(genF16Type p);
genF16Type interpolateAtCentroid(genF16Type interpolant);
genF16Type interpolateAtSample(genF16Type interpolant, int sampleIndex);
genF16Type interpolateAtOffset(genF16Type interpolant, f16vec2 offset);

// ---- GL_EXT_debug_printf
@ ext:GL_EXT_debug_printf variadic
void debugPrintfEXT(string format);

// ---- GL_EXT_demote_to_helper_invocation
@ ext:GL_EXT_demote_to_helper_invocation frag
bool helperInvocationEXT();

// ---- GL_EXT_ray_tracing
@ ext:GL_EXT_ray_tracing
const uint gl_RayFlagsNoneEXT;
const uint gl_RayFlagsOpaqueEXT;
const uint gl_RayFlagsNoOpaqueEXT;
const uint gl_RayFlagsTerminateOnFirstHitEXT;
const uint gl_RayFlagsSkipClosestHitShaderEXT;
const uint gl_RayFlagsCullBackFacingTrianglesEXT;
const uint gl_RayFlagsCullFrontFacingTrianglesEXT;
const uint gl_RayFlagsCullOpaqueEXT;
const uint gl_RayFlagsCullNoOpaqueEXT;
const uint gl_HitKindFrontFacingTriangleEXT;
const uint gl_HitKindBackFacingTriangleEXT;

@ ext:GL_EXT_ray_tracing rgen rint rahit rchit rmiss rcall
in uvec3 gl_LaunchIDEXT;
in uvec3 gl_LaunchSizeEXT;

@ ext:GL_EXT_ray_tracing rint rahit rchit
in int gl_PrimitiveID;
in int gl_InstanceID;
in int gl_InstanceCustomIndexEXT;
in int gl_GeometryIndexEXT;
in vec3 gl_ObjectRayOriginEXT;
in vec3 gl_ObjectRayDirectionEXT;
in mat4x3 gl_ObjectToWorldEXT;
in mat3x4 gl_ObjectToWorld3x4EXT;
in mat4x3 gl_WorldToObjectEXT;
in mat3x4 gl_WorldToObject3x4EXT;

@ ext:GL_EXT_ray_tracing rint rahit rchit rmiss
in vec3 gl_WorldRayOriginEXT;
in vec3 gl_WorldRayDirectionEXT;
in float gl_RayTminEXT;
in float gl_RayTmaxEXT;
in uint gl_IncomingRayFlagsEXT;

@ ext:GL_EXT_ray_tracing rahit rchit
in float gl_HitTEXT;
in uint gl_HitKindEXT;

@ ext:GL_EXT_ray_tracing rgen rchit rmiss
void traceRayEXT(accelerationStructureEXT topLevel, uint rayFlags, uint cullMask, uint sbtRecordOffset,
    uint sbtRecordStride, uint missIndex, vec3 origin, float Tmin, vec3 direction, float Tmax, int payload);

@ ext:GL_EXT_ray_tracing rint
bool reportIntersectionEXT(float hitT, uint hitKind);

@ ext:GL_EXT_ray_tracing rgen rchit rmiss rcall
void executeCallableEXT(uint sbtRecordIndex, int callable);

// ---- 64 bit atomic memory functions
@ ext:GL_ARB_gpu_shader_int64 ext:GL_EXT_shader_explicit_arithmetic_types ext:GL_EXT_shader_explicit_arithmetic_types_int64 ext:GL_AMD_gpu_shader_int64 ext:GL_NV_gpu_shader5
iuint64 atomicAdd(inout iuint64 mem, iuint64 data);
iuint64 atomicMin(inout iuint64 mem, iuint64 data);
iuint64 atomicMax(inout iuint64 mem, iuint64 data);
iuint64 atomicAnd(inout iuint64 mem, iuint64 data);
iuint64 atomicOr(inout iuint64 mem, iuint64 data);
iuint64 atomicXor(inout iuint64 mem, iuint64 data);
iuint64 atomicExchange(inout iuint64 mem, iuint64 data);
iuint64 atomicCompSwap(inout iuint64 mem, iuint64 compare, iuint64 data);

// ---- GL_KHR_memory_scope_semantics
@ ext:GL_KHR_memory_scope_semantics
const int gl_ScopeDevice;
const int gl_ScopeWorkgroup;
const int gl_ScopeSubgroup;
const int gl_ScopeInvocation;
const int gl_ScopeQueueFamily;
const int gl_SemanticsRelaxed;
const int gl_SemanticsAcquire;
const int gl_SemanticsRelease;
const int gl_SemanticsAcquireRelease;
const int gl_SemanticsMakeAvailable;
const int gl_SemanticsMakeVisible;
const int gl_SemanticsVolatile;
const int gl_StorageSemanticsNone;
const int gl_StorageSemanticsBuffer;
const int gl_StorageSemanticsShared;
const int gl_StorageSemanticsImage;
const int gl_StorageSemanticsOutput;
iuint atomicAdd(inout iuint mem, iuint data, int scope, int storage, int semantics);
iuint atomicMin(inout iuint mem, iuint data, int scope, int storage, int semantics);
iuint atomicMax(inout iuint mem, iuint data, int scope, int storage, int semantics);
iuint atomicAnd(inout iuint mem, iuint data, int scope, int storage, int semantics);
iuint atomicOr(inout iuint mem, iuint data, int scope, int storage, int semantics);
iuint atomicXor(inout iuint mem, iuint data, int scope, int storage, int semantics);
iuint atomicExchange(inout iuint mem, iuint data, int scope, int storage, int semantics);
iuint atomicCompSwap(inout iuint mem, iuint compare, iuint data, int scope, int storageEqual, int semanticsEqual, int storageUnequal, int semanticsUnequal);
iuint atomicLoad(iuint mem, int scope, int storage, int semantics);
void atomicStore(inout iuint mem, iuint data, int scope, int storage, int semantics);
iuint64 atomicAdd(inout iuint64 mem, iuint64 data, int scope, int storage, int semantics);
iuint64 atomicMin(inout iuint64 mem, iuint64 data, int scope, int storage, int semantics);
iuint64 atomicMax(inout iuint64 mem, iuint64 data, int scope, int storage, int semantics);
iuint64 atomicAnd(inout iuint64 mem, iuint64 data, int scope, int storage, int semantics);
iuint64 atomicOr(inout iuint64 mem, iuint64 data, int scope, int storage, int semantics);
iuint64 atomicXor(inout iuint64 mem, iuint64 data, int scope, int storage, int semantics);
iuint64 atomicExchange(inout iuint64 mem, iuint64 data, int scope, int storage, int semantics);
iuint64 atomicCompSwap(inout iuint64 mem, iuint64 compare, iuint64 data, int scope, int storageEqual, int semanticsEqual, int storageUnequal, int semanticsUnequal);
iuint64 atomicLoad(iuint64 mem, int scope, int storage, int semantics);
void atomicStore(inout iuint64 mem, iuint64 data, int scope, int storage, int semantics);

// ---- packing functions of the explicit arithmetic types
@ ext:GL_EXT_shader_explicit_arithmetic_types ext:GL_EXT_shader_explicit_arithmetic_types_int8 ext:GL_EXT_shader_explicit_arithmetic_types_int16 ext:GL_EXT_shader_explicit_arithmetic_types_int32 ext:GL_EXT_shader_explicit_arithmetic_types_int64 ext:GL_NV_gpu_shader5
int pack32(i16vec2 v);
uint pack32(u16vec2 v);
int64_t pack64(i16vec4 v);
uint64_t pack64(u16vec4 v);
int64_t pack64(ivec2 v);
uint64_t pack64(uvec2 v);
i16vec2 unpack16(int v);
u16vec2 unpack16(uint v);
i16vec4 unpack16(int64_t v);
u16vec4 unpack16(uint64_t v);
ivec2 unpack32(int64_t v);
uvec2 unpack32(uint64_t v);

// ---- GL_KHR_shader_subgroup
// every subgroup extension requires the basic one
@ ext:GL_KHR_shader_subgroup_basic ext:GL_KHR_shader_subgroup_vote ext:GL_KHR_shader_subgroup_ballot ext:GL_KHR_shader_subgroup_shuffle ext:GL_KHR_shader_subgroup_shuffle_relative ext:GL_KHR_shader_subgroup_arithmetic ext:GL_KHR_shader_subgroup_clustered ext:GL_KHR_shader_subgroup_quad
in uint gl_SubgroupSize;
in uint gl_SubgroupInvocationID;
in uvec4 gl_SubgroupEqMask;
in uvec4 gl_SubgroupGeMask;
in uvec4 gl_SubgroupGtMask;
in uvec4 gl_SubgroupLeMask;
in uvec4 gl_SubgroupLtMask;
void subgroupBarrier();
void subgroupMemoryBarrier();
void subgroupMemoryBarrierBuffer();
void subgroupMemoryBarrierImage();
bool subgroupElect();

@ ext:GL_KHR_shader_subgroup_basic ext:GL_KHR_shader_subgroup_vote ext:GL_KHR_shader_subgroup_ballot ext:GL_KHR_shader_subgroup_shuffle ext:GL_KHR_shader_subgroup_shuffle_relative ext:GL_KHR_shader_subgroup_arithmetic ext:GL_KHR_shader_subgroup_clustered ext:GL_KHR_shader_subgroup_quad comp task mesh
in uint gl_NumSubgroups;
in uint gl_SubgroupID;
void subgroupMemoryBarrierShared();

@ ext:GL_KHR_shader_subgroup_vote
bool subgroupAll(bool value);
bool subgroupAny(bool value);

@ ext:GL_KHR_shader_subgroup_ballot
uvec4 subgroupBallot(bool value);
bool subgroupInverseBallot(uvec4 value);
bool subgroupBallotBitExtract(uvec4 value, uint index);
uint subgroupBallotBitCount(uvec4 value);
uint subgroupBallotInclusiveBitCount(uvec4 value);
uint subgroupBallotExclusiveBitCount(uvec4 value);
uint subgroupBallotFindLSB(uvec4 value);
uint subgroupBallotFindMSB(uvec4 value);

@ ext:GL_KHR_shader_subgroup_vote
bool subgroupAllEqual(genType value);
bool subgroupAllEqual(genDType value);
bool subgroupAllEqual(genIType value);
bool subgroupAllEqual(genUType value);
bool subgroupAllEqual(genBType value);

@ ext:GL_KHR_shader_subgroup_ballot
genType subgroupBroadcast(genType value, uint id);
genType subgroupBroadcastFirst(genType value);
genDType subgroupBroadcast(genDType value, uint id);
genDType subgroupBroadcastFirst(genDType value);
genIType subgroupBroadcast(genIType value, uint id);
genIType subgroupBroadcastFirst(genIType value);
genUType subgroupBroadcast(genUType value, uint id);
genUType subgroupBroadcastFirst(genUType value);
genBType subgroupBroadcast(genBType value, uint id);
genBType subgroupBroadcastFirst(genBType value);

@ ext:GL_KHR_shader_subgroup_shuffle
genType subgroupShuffle(genType value, uint id);
genType subgroupShuffleXor(genType value, uint mask);
genDType subgroupShuffle(genDType value, uint id);
genDType subgroupShuffleXor(genDType value, uint mask);
genIType subgroupShuffle(genIType value, uint id);
genIType subgroupShuffleXor(genIType value, uint mask);
genUType subgroupShuffle(genUType value, uint id);
genUType subgroupShuffleXor(genUType value, uint mask);
genBType subgroupShuffle(genBType value, uint id);
genBType subgroupShuffleXor(genBType value, uint mask);

@ ext:GL_KHR_shader_subgroup_shuffle_relative
genType subgroupShuffleUp(genType value, uint delta);
genType subgroupShuffleDown(genType value, uint delta);
genDType subgroupShuffleUp(genDType value, uint delta);
genDType subgroupShuffleDown(genDType value, uint delta);
genIType subgroupShuffleUp(genIType value, uint delta);
genIType subgroupShuffleDown(genIType value, uint delta);
genUType subgroupShuffleUp(genUType value, uint delta);
genUType subgroupShuffleDown(genUType value, uint delta);
genBType subgroupShuffleUp(genBType value, uint delta);
genBType subgroupShuffleDown(genBType value, uint delta);

@ ext:GL_KHR_shader_subgroup_arithmetic
genType subgroupAdd(genType value);
genDType subgroupAdd(genDType value);
genIType subgroupAdd(genIType value);
genUType subgroupAdd(genUType value);
genType subgroupMul(genType value);
genDType subgroupMul(genDType value);
genIType subgroupMul(genIType value);
genUType subgroupMul(genUType value);
genType subgroupMin(genType value);
genDType subgroupMin(genDType value);
genIType subgroupMin(genIType value);
genUType subgroupMin(genUType value);
genType subgroupMax(genType value);
genDType subgroupMax(genDType value);
genIType subgroupMax(genIType value);
genUType subgroupMax(genUType value);
genIType subgroupAnd(genIType value);
genUType subgroupAnd(genUType value);
genBType subgroupAnd(genBType value);
genIType subgroupOr(genIType value);
genUType subgroupOr(genUType value);
genBType subgroupOr(genBType value);
genIType subgroupXor(genIType value);
genUType subgroupXor(genUType value);
genBType subgroupXor(genBType value);
genType subgroupInclusiveAdd(genType value);
genDType subgroupInclusiveAdd(genDType value);
genIType subgroupInclusiveAdd(genIType value);
genUType subgroupInclusiveAdd(genUType value);
genType subgroupInclusiveMul(genType value);
genDType subgroupInclusiveMul(genDType value);
genIType subgroupInclusiveMul(genIType value);
genUType subgroupInclusiveMul(genUType value);
genType subgroupInclusiveMin(genType value);
genDType subgroupInclusiveMin(genDType value);
genIType subgroupInclusiveMin(genIType value);
genUType subgroupInclusiveMin(genUType value);
genType subgroupInclusiveMax(genType value);
genDType subgroupInclusiveMax(genDType value);
genIType subgroupInclusiveMax(genIType value);
genUType subgroupInclusiveMax(genUType value);
genIType subgroupInclusiveAnd(genIType value);
genUType subgroupInclusiveAnd(genUType value);
genBType subgroupInclusiveAnd(genBType value);
genIType subgroupInclusiveOr(genIType value);
genUType subgroupInclusiveOr(genUType value);
genBType subgroupInclusiveOr(genBType value);
genIType subgroupInclusiveXor(genIType value);
genUType subgroupInclusiveXor(genUType value);
genBType subgroupInclusiveXor(genBType value);
genType subgroupExclusiveAdd(genType value);
genDType subgroupExclusiveAdd(genDType value);
genIType subgroupExclusiveAdd(genIType value);
genUType subgroupExclusiveAdd(genUType value);
genType subgroupExclusiveMul(genType value);
genDType subgroupExclusiveMul(genDType value);
genIType subgroupExclusiveMul(genIType value);
genUType subgroupExclusiveMul(genUType value);
genType subgroupExclusiveMin(genType value);
genDType subgroupExclusiveMin(genDType value);
genIType subgroupExclusiveMin(genIType value);
genUType subgroupExclusiveMin(genUType value);
genType subgroupExclusiveMax(genType value);
genDType subgroupExclusiveMax(genDType value);
genIType subgroupExclusiveMax(genIType value);
genUType subgroupExclusiveMax(genUType value);
genIType subgroupExclusiveAnd(genIType value);
genUType subgroupExclusiveAnd(genUType value);
genBType subgroupExclusiveAnd(genBType value);
genIType subgroupExclusiveOr(genIType value);
genUType subgroupExclusiveOr(genUType value);
genBType subgroupExclusiveOr(genBType value);
genIType subgroupExclusiveXor(genIType value);
genUType subgroupExclusiveXor(genUType value);
genBType subgroupExclusiveXor(genBType value);

@ ext:GL_KHR_shader_subgroup_clustered
genType subgroupClusteredAdd(genType value, uint clusterSize);
genDType subgroupClusteredAdd(genDType value, uint clusterSize);
genIType subgroupClusteredAdd(genIType value, uint clusterSize);
genUType subgroupClusteredAdd(genUType value, uint clusterSize);
genType subgroupClusteredMul(genType value, uint clusterSize);
genDType subgroupClusteredMul(genDType value, uint clusterSize);
genIType subgroupClusteredMul(genIType value, uint clusterSize);
genUType subgroupClusteredMul(genUType value, uint clusterSize);
genType subgroupClusteredMin(genType value, uint clusterSize);
genDType subgroupClusteredMin(genDType value, uint clusterSize);
genIType subgroupClusteredMin(genIType value, uint clusterSize);
genUType subgroupClusteredMin(genUType value, uint clusterSize);
genType subgroupClusteredMax(genType value, uint clusterSize);
genDType subgroupClusteredMax(genDType value, uint clusterSize);
genIType subgroupClusteredMax(genIType value, uint clusterSize);
genUType subgroupClusteredMax(genUType value, uint clusterSize);
genIType subgroupClusteredAnd(genIType value, uint clusterSize);
genUType subgroupClusteredAnd(genUType value, uint clusterSize);
genBType subgroupClusteredAnd(genBType value, uint clusterSize);
genIType subgroupClusteredOr(genIType value, uint clusterSize);
genUType subgroupClusteredOr(genUType value, uint clusterSize);
genBType subgroupClusteredOr(genBType value, uint clusterSize);
genIType subgroupClusteredXor(genIType value, uint clusterSize);
genUType subgroupClusteredXor(genUType value, uint clusterSize);
genBType subgroupClusteredXor(genBType value, uint clusterSize);

@ ext:GL_KHR_shader_subgroup_quad
genType subgroupQuadBroadcast(genType value, uint id);
genDType subgroupQuadBroadcast(genDType value, uint id);
genIType subgroupQuadBroadcast(genIType value, uint id);
genUType subgroupQuadBroadcast(genUType value, uint id);
genBType subgroupQuadBroadcast(genBType value, uint id);
genType subgroupQuadSwapHorizontal(genType value);
genDType subgroupQuadSwapHorizontal(genDType value);
genIType subgroupQuadSwapHorizontal(genIType value);
genUType subgroupQuadSwapHorizontal(genUType value);
genBType subgroupQuadSwapHorizontal(genBType value);
genType subgroupQuadSwapVertical(genType value);
genDType subgroupQuadSwapVertical(genDType value);
genIType subgroupQuadSwapVertical(genIType value);
genUType subgroupQuadSwapVertical(genUType value);
genBType subgroupQuadSwapVertical(genBType value);
genType subgroupQuadSwapDiagonal(genType value);
genDType subgroupQuadSwapDiagonal(genDType value);
genIType subgroupQuadSwapDiagonal(genIType value);
genUType subgroupQuadSwapDiagonal(genUType value);
genBType subgroupQuadSwapDiagonal(genBType value);

// ---- GL_EXT_shader_subgroup_extended_types, gated only on the extended types extensions

@ ext:GL_EXT_shader_subgroup_extended_types_int8
bool subgroupAllEqual(genI8Type value);
bool subgroupAllEqual(genU8Type value);

@ ext:GL_EXT_shader_subgroup_extended_types_int8
genI8Type subgroupBroadcast(genI8Type value, uint id);
genI8Type subgroupBroadcastFirst(genI8Type value);
genU8Type subgroupBroadcast(genU8Type value, uint id);
genU8Type subgroupBroadcastFirst(genU8Type value);

@ ext:GL_EXT_shader_subgroup_extended_types_int8
genI8Type subgroupShuffle(genI8Type value, uint id);
genI8Type subgroupShuffleXor(genI8Type value, uint mask);
genU8Type subgroupShuffle(genU8Type value, uint id);
genU8Type subgroupShuffleXor(genU8Type value, uint mask);

@ ext:GL_EXT_shader_subgroup_extended_types_int8
genI8Type subgroupShuffleUp(genI8Type value, uint delta);
genI8Type subgroupShuffleDown(genI8Type value, uint delta);
genU8Type subgroupShuffleUp(genU8Type value, uint delta);
genU8Type subgroupShuffleDown(genU8Type value, uint delta);

@ ext:GL_EXT_shader_subgroup_extended_types_int8
genI8Type subgroupAdd(genI8Type value);
genU8Type subgroupAdd(genU8Type value);
genI8Type subgroupMul(genI8Type value);
genU8Type subgroupMul(genU8Type value);
genI8Type subgroupMin(genI8Type value);
genU8Type subgroupMin(genU8Type value);
genI8Type subgroupMax(genI8Type value);
genU8Type subgroupMax(genU8Type value);
genI8Type subgroupAnd(genI8Type value);
genU8Type subgroupAnd(genU8Type value);
genI8Type subgroupOr(genI8Type value);
genU8Type subgroupOr(genU8Type value);
genI8Type subgroupXor(genI8Type value);
genU8Type subgroupXor(genU8Type value);
genI8Type subgroupInclusiveAdd(genI8Type value);
genU8Type subgroupInclusiveAdd(genU8Type value);
genI8Type subgroupInclusiveMul(genI8Type value);
genU8Type subgroupInclusiveMul(genU8Type value);
genI8Type subgroupInclusiveMin(genI8Type value);
genU8Type subgroupInclusiveMin(genU8Type value);
genI8Type subgroupInclusiveMax(genI8Type value);
genU8Type subgroupInclusiveMax(genU8Type value);
genI8Type subgroupInclusiveAnd(genI8Type value);
genU8Type subgroupInclusiveAnd(genU8Type value);
genI8Type subgroupInclusiveOr(genI8Type value);
genU8Type subgroupInclusiveOr(genU8Type value);
genI8Type subgroupInclusiveXor(genI8Type value);
genU8Type subgroupInclusiveXor(genU8Type value);
genI8Type subgroupExclusiveAdd(genI8Type value);
genU8Type subgroupExclusiveAdd(genU8Type value);
genI8Type subgroupExclusiveMul(genI8Type value);
genU8Type subgroupExclusiveMul(genU8Type value);
genI8Type subgroupExclusiveMin(genI8Type value);
genU8Type subgroupExclusiveMin(genU8Type value);
genI8Type subgroupExclusiveMax(genI8Type value);
genU8Type subgroupExclusiveMax(genU8Type value);
genI8Type subgroupExclusiveAnd(genI8Type value);
genU8Type subgroupExclusiveAnd(genU8Type value);
genI8Type subgroupExclusiveOr(genI8Type value);
genU8Type subgroupExclusiveOr(genU8Type value);
genI8Type subgroupExclusiveXor(genI8Type value);
genU8Type subgroupExclusiveXor(genU8Type value);

@ ext:GL_EXT_shader_subgroup_extended_types_int8
genI8Type subgroupClusteredAdd(genI8Type value, uint clusterSize);
genU8Type subgroupClusteredAdd(genU8Type value, uint clusterSize);
genI8Type subgroupClusteredMul(genI8Type value, uint clusterSize);
genU8Type subgroupClusteredMul(genU8Type value, uint clusterSize);
genI8Type subgroupClusteredMin(genI8Type value, uint clusterSize);
genU8Type subgroupClusteredMin(genU8Type value, uint clusterSize);
genI8Type subgroupClusteredMax(genI8Type value, uint clusterSize);
genU8Type subgroupClusteredMax(genU8Type value, uint clusterSize);
genI8Type subgroupClusteredAnd(genI8Type value, uint clusterSize);
genU8Type subgroupClusteredAnd(genU8Type value, uint clusterSize);
genI8Type subgroupClusteredOr(genI8Type value, uint clusterSize);
genU8Type subgroupClusteredOr(genU8Type value, uint clusterSize);
genI8Type subgroupClusteredXor(genI8Type value, uint clusterSize);
genU8Type subgroupClusteredXor(genU8Type value, uint clusterSize);

@ ext:GL_EXT_shader_subgroup_extended_types_int8
genI8Type subgroupQuadBroadcast(genI8Type value, uint id);
genU8Type subgroupQuadBroadcast(genU8Type value, uint id);
genI8Type subgroupQuadSwapHorizontal(genI8Type value);
genU8Type subgroupQuadSwapHorizontal(genU8Type value);
genI8Type subgroupQuadSwapVertical(genI8Type value);
genU8Type subgroupQuadSwapVertical(genU8Type value);
genI8Type subgroupQuadSwapDiagonal(genI8Type value);
genU8Type subgroupQuadSwapDiagonal(genU8Type value);

@ ext:GL_EXT_shader_subgroup_extended_types_int16
bool subgroupAllEqual(genI16Type value);
bool subgroupAllEqual(genU16Type value);

@ ext:GL_EXT_shader_subgroup_extended_types_int16
genI16Type subgroupBroadcast(genI16Type value, uint id);
genI16Type subgroupBroadcastFirst(genI16Type value);
genU16Type subgroupBroadcast(genU16Type value, uint id);
genU16Type subgroupBroadcastFirst(genU16Type value);

@ ext:GL_EXT_shader_subgroup_extended_types_int16
genI16Type subgroupShuffle(genI16Type value, uint id);
genI16Type subgroupShuffleXor(genI16Type value, uint mask);
genU16Type subgroupShuffle(genU16Type value, uint id);
genU16Type subgroupShuffleXor(genU16Type value, uint mask);

@ ext:GL_EXT_shader_subgroup_extended_types_int16
genI16Type subgroupShuffleUp(genI16Type value, uint delta);
genI16Type subgroupShuffleDown(genI16Type value, uint delta);
genU16Type subgroupShuffleUp(genU16Type value, uint delta);
genU16Type subgroupShuffleDown(genU16Type value, uint delta);

@ ext:GL_EXT_shader_subgroup_extended_types_int16
genI16Type subgroupAdd(genI16Type value);
genU16Type subgroupAdd(genU16Type value);
genI16Type subgroupMul(genI16Type value);
genU16Type subgroupMul(genU16Type value);
genI16Type subgroupMin(genI16Type value);
genU16Type subgroupMin(genU16Type value);
genI16Type subgroupMax(genI16Type value);
genU16Type subgroupMax(genU16Type value);
genI16Type subgroupAnd(genI16Type value);
genU16Type subgroupAnd(genU16Type value);
genI16Type subgroupOr(genI16Type value);
genU16Type subgroupOr(genU16Type value);
genI16Type subgroupXor(genI16Type value);
genU16Type subgroupXor(genU16Type value);
genI16Type subgroupInclusiveAdd(genI16Type value);
genU16Type subgroupInclusiveAdd(genU16Type value);
genI16Type subgroupInclusiveMul(genI16Type value);
genU16Type subgroupInclusiveMul(genU16Type value);
genI16Type subgroupInclusiveMin(genI16Type value);
genU16Type subgroupInclusiveMin(genU16Type value);
genI16Type subgroupInclusiveMax(genI16Type value);
genU16Type subgroupInclusiveMax(genU16Type value);
genI16Type subgroupInclusiveAnd(genI16Type value);
genU16Type subgroupInclusiveAnd(genU16Type value);
genI16Type subgroupInclusiveOr(genI16Type value);
genU16Type subgroupInclusiveOr(genU16Type value);
genI16Type subgroupInclusiveXor(genI16Type value);
genU16Type subgroupInclusiveXor(genU16Type value);
genI16Type subgroupExclusiveAdd(genI16Type value);
genU16Type subgroupExclusiveAdd(genU16Type value);
genI16Type subgroupExclusiveMul(genI16Type value);
genU16Type subgroupExclusiveMul(genU16Type value);
genI16Type subgroupExclusiveMin(genI16Type value);
genU16Type subgroupExclusiveMin(genU16Type value);
genI16Type subgroupExclusiveMax(genI16Type value);
genU16Type subgroupExclusiveMax(genU16Type value);
genI16Type subgroupExclusiveAnd(genI16Type value);
genU16Type subgroupExclusiveAnd(genU16Type value);
genI16Type subgroupExclusiveOr(genI16Type value);
genU16Type subgroupExclusiveOr(genU16Type value);
genI16Type subgroupExclusiveXor(genI16Type value);
genU16Type subgroupExclusiveXor(genU16Type value);

@ ext:GL_EXT_shader_subgroup_extended_types_int16
genI16Type subgroupClusteredAdd(genI16Type value, uint clusterSize);
genU16Type subgroupClusteredAdd(genU16Type value, uint clusterSize);
genI16Type subgroupClusteredMul(genI16Type value, uint clusterSize);
genU16Type subgroupClusteredMul(genU16Type value, uint clusterSize);
genI16Type subgroupClusteredMin(genI16Type value, uint clusterSize);
genU16Type subgroupClusteredMin(genU16Type value, uint clusterSize);
genI16Type subgroupClusteredMax(genI16Type value, uint clusterSize);
genU16Type subgroupClusteredMax(genU16Type value, uint clusterSize);
genI16Type subgroupClusteredAnd(genI16Type value, uint clusterSize);
genU16Type subgroupClusteredAnd(genU16Type value, uint clusterSize);
genI16Type subgroupClusteredOr(genI16Type value, uint clusterSize);
genU16Type subgroupClusteredOr(genU16Type value, uint clusterSize);
genI16Type subgroupClusteredXor(genI16Type value, uint clusterSize);
genU16Type subgroupClusteredXor(genU16Type value, uint clusterSize);

@ ext:GL_EXT_shader_subgroup_extended_types_int16
genI16Type subgroupQuadBroadcast(genI16Type value, uint id);
genU16Type subgroupQuadBroadcast(genU16Type value, uint id);
genI16Type subgroupQuadSwapHorizontal(genI16Type value);
genU16Type subgroupQuadSwapHorizontal(genU16Type value);
genI16Type subgroupQuadSwapVertical(genI16Type value);
genU16Type subgroupQuadSwapVertical(genU16Type value);
genI16Type subgroupQuadSwapDiagonal(genI16Type value);
genU16Type subgroupQuadSwapDiagonal(genU16Type value);

@ ext:GL_EXT_shader_subgroup_extended_types_int64
bool subgroupAllEqual(genI64Type value);
bool subgroupAllEqual(genU64Type value);

@ ext:GL_EXT_shader_subgroup_extended_types_int64
genI64Type subgroupBroadcast(genI64Type value, uint id);
genI64Type subgroupBroadcastFirst(genI64Type value);
genU64Type subgroupBroadcast(genU64Type value, uint id);
genU64Type subgroupBroadcastFirst(genU64Type value);

@ ext:GL_EXT_shader_subgroup_extended_types_int64
genI64Type subgroupShuffle(genI64Type value, uint id);
genI64Type subgroupShuffleXor(genI64Type value, uint mask);
genU64Type subgroupShuffle(genU64Type value, uint id);
genU64Type subgroupShuffleXor(genU64Type value, uint mask);

@ ext:GL_EXT_shader_subgroup_extended_types_int64
genI64Type subgroupShuffleUp(genI64Type value, uint delta);
genI64Type subgroupShuffleDown(genI64Type value, uint delta);
genU64Type subgroupShuffleUp(genU64Type value, uint delta);
genU64Type subgroupShuffleDown(genU64Type value, uint delta);

@ ext:GL_EXT_shader_subgroup_extended_types_int64
genI64Type subgroupAdd(genI64Type value);
genU64Type subgroupAdd(genU64Type value);
genI64Type subgroupMul(genI64Type value);
genU64Type subgroupMul(genU64Type value);
genI64Type subgroupMin(genI64Type value);
genU64Type subgroupMin(genU64Type value);
genI64Type subgroupMax(genI64Type value);
genU64Type subgroupMax(genU64Type value);
genI64Type subgroupAnd(genI64Type value);
genU64Type subgroupAnd(genU64Type value);
genI64Type subgroupOr(genI64Type value);
genU64Type subgroupOr(genU64Type value);
genI64Type subgroupXor(genI64Type value);
genU64Type subgroupXor(genU64Type value);
genI64Type subgroupInclusiveAdd(genI64Type value);
genU64Type subgroupInclusiveAdd(genU64Type value);
genI64Type subgroupInclusiveMul(genI64Type value);
genU64Type subgroupInclusiveMul(genU64Type value);
genI64Type subgroupInclusiveMin(genI64Type value);
genU64Type subgroupInclusiveMin(genU64Type value);
genI64Type subgroupInclusiveMax(genI64Type value);
genU64Type subgroupInclusiveMax(genU64Type value);
genI64Type subgroupInclusiveAnd(genI64Type value);
genU64Type subgroupInclusiveAnd(genU64Type value);
genI64Type subgroupInclusiveOr(genI64Type value);
genU64Type subgroupInclusiveOr(genU64Type value);
genI64Type subgroupInclusiveXor(genI64Type value);
genU64Type subgroupInclusiveXor(genU64Type value);
genI64Type subgroupExclusiveAdd(genI64Type value);
genU64Type subgroupExclusiveAdd(genU64Type value);
genI64Type subgroupExclusiveMul(genI64Type value);
genU64Type subgroupExclusiveMul(genU64Type value);
genI64Type subgroupExclusiveMin(genI64Type value);
genU64Type subgroupExclusiveMin(genU64Type value);
genI64Type subgroupExclusiveMax(genI64Type value);
genU64Type subgroupExclusiveMax(genU64Type value);
genI64Type subgroupExclusiveAnd(genI64Type value);
genU64Type subgroupExclusiveAnd(genU64Type value);
genI64Type subgroupExclusiveOr(genI64Type value);
genU64Type subgroupExclusiveOr(genU64Type value);
genI64Type subgroupExclusiveXor(genI64Type value);
genU64Type subgroupExclusiveXor(genU64Type value);

@ ext:GL_EXT_shader_subgroup_extended_types_int64
genI64Type subgroupClusteredAdd(genI64Type value, uint clusterSize);
genU64Type subgroupClusteredAdd(genU64Type value, uint clusterSize);
genI64Type subgroupClusteredMul(genI64Type value, uint clusterSize);
genU64Type subgroupClusteredMul(genU64Type value, uint clusterSize);
genI64Type subgroupClusteredMin(genI64Type value, uint clusterSize);
genU64Type subgroupClusteredMin(genU64Type value, uint clusterSize);
genI64Type subgroupClusteredMax(genI64Type value, uint clusterSize);
genU64Type subgroupClusteredMax(genU64Type value, uint clusterSize);
genI64Type subgroupClusteredAnd(genI64Type value, uint clusterSize);
genU64Type subgroupClusteredAnd(genU64Type value, uint clusterSize);
genI64Type subgroupClusteredOr(genI64Type value, uint clusterSize);
genU64Type subgroupClusteredOr(genU64Type value, uint clusterSize);
genI64Type subgroupClusteredXor(genI64Type value, uint clusterSize);
genU64Type subgroupClusteredXor(genU64Type value, uint clusterSize);

@ ext:GL_EXT_shader_subgroup_extended_types_int64
genI64Type subgroupQuadBroadcast(genI64Type value, uint id);
genU64Type subgroupQuadBroadcast(genU64Type value, uint id);
genI64Type subgroupQuadSwapHorizontal(genI64Type value);
genU64Type subgroupQuadSwapHorizontal(genU64Type value);
genI64Type subgroupQuadSwapVertical(genI64Type value);
genU64Type subgroupQuadSwapVertical(genU64Type value);
genI64Type subgroupQuadSwapDiagonal(genI64Type value);
genU64Type subgroupQuadSwapDiagonal(genU64Type value);

@ ext:GL_EXT_shader_subgroup_extended_types_float16
bool subgroupAllEqual(genF16Type value);

@ ext:GL_EXT_shader_subgroup_extended_types_float16
genF16Type subgroupBroadcast(genF16Type value, uint id);
genF16Type subgroupBroadcastFirst(genF16Type value);

@ ext:GL_EXT_shader_subgroup_extended_types_float16
genF16Type subgroupShuffle(genF16Type value, uint id);
genF16Type subgroupShuffleXor(genF16Type value, uint mask);

@ ext:GL_EXT_shader_subgroup_extended_types_float16
genF16Type subgroupShuffleUp(genF16Type value, uint delta);
genF16Type subgroupShuffleDown(genF16Type value, uint delta);

@ ext:GL_EXT_shader_subgroup_extended_types_float16
genF16Type subgroupAdd(genF16Type value);
genF16Type subgroupMul(genF16Type value);
genF16Type subgroupMin(genF16Type value);
genF16Type subgroupMax(genF16Type value);
genF16Type subgroupInclusiveAdd(genF16Type value);
genF16Type subgroupInclusiveMul(genF16Type value);
genF16Type subgroupInclusiveMin(genF16Type value);
genF16Type subgroupInclusiveMax(genF16Type value);
genF16Type subgroupExclusiveAdd(genF16Type value);
genF16Type subgroupExclusiveMul(genF16Type value);
genF16Type subgroupExclusiveMin(genF16Type value);
genF16Type subgroupExclusiveMax(genF16Type value);

@ ext:GL_EXT_shader_subgroup_extended_types_float16
genF16Type subgroupClusteredAdd(genF16Type value, uint clusterSize);
genF16Type subgroupClusteredMul(genF16Type value, uint clusterSize);
genF16Type subgroupClusteredMin(genF16Type value, uint clusterSize);
genF16Type subgroupClusteredMax(genF16Type value, uint clusterSize);

@ ext:GL_EXT_shader_subgroup_extended_types_float16
genF16Type subgroupQuadBroadcast(genF16Type value, uint id);
genF16Type subgroupQuadSwapHorizontal(genF16Type value);
genF16Type subgroupQuadSwapVertical(genF16Type value);
genF16Type subgroupQuadSwapDiagonal(genF16Type value);

// ---- derivatives of doubles
@ ext:GL_EXT_shader_explicit_arithmetic_types ext:GL_EXT_shader_explicit_arithmetic_types_float64 frag
genDType dFdx(genDType p);
genDType dFdy(genDType p);
genDType fwidth(genDType p);
genDType dFdxFine(genDType p);
genDType dFdyFine(genDType p);
genDType fwidthFine(genDType p);
genDType dFdxCoarse(genDType p);
genDType dFdyCoarse(genDType p);
genDType fwidthCoarse(genDType p);
genDType interpolateAtCentroid(genDType interpolant);
genDType interpolateAtSample(genDType interpolant, int sampleIndex);
genDType interpolateAtOffset(genDType interpolant, dvec2 offset);
