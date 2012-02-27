class array;

enum EMobileTexCoordsSource
{
	EMobileTexCoordsSource1,
	MTCS_TexCoords0,
	MTCS_TexCoords1
};

enum EMobileAmbientOcclusionSource
{
	EMobileAmbientOcclusionSource1,
	MAOS_Disabled
};

enum EPixelFormat
{
	PF_A8R8G8B8
};

enum EMobileSpecularMask
{
	MSM_Constant
};

enum EMobileEmissiveColorSource
{
	MECS_EmissiveTexture
};

enum EMobileValueSource
{
	MVS_Constant
};

enum EMobileEnvironmentBlendMode
{
	MEBM_Add
};

enum EMobileTextureBlendFactorSource
{
	MTBFS_VertexColor
};

enum EMobileTextureTransformTarget
{
	MTTT_BaseTexture
};

enum EBlendMode
{
	BLEND_Opaque
};

enum EMaterialLightingModel
{};

enum EMaterialTessellationMode
{
	MTM_NoTessellation
};

enum EParticleScreenAlignment
{};

enum ECameraAnimPlaySpace
{
	CAPS_CameraLocal
};

struct Map
{};

struct Vect
{
	var float x, y, z;
};

var int Length;
var bool Empty;

function Add(int Count);
function Insert(int Index, int Count);
function Remove(int Index, int Count);
function AddItem(); // param: Item
function RemoveItem(); // param: Item
function InsertItem(int Index); // param: Item
function Find(); // param: Value
function Find(string PropertyName); // param: PropertyName, param: Value
function Sort(); // param: SortDelegate