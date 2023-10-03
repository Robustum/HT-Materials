package hiiragi283.material.api.shape

data class HiiragiShapeType(val name: String) {

    val shapes: Set<HiiragiShape>
        get() = shapesInternal
    private val shapesInternal: MutableSet<HiiragiShape> = mutableSetOf()

    constructor(name: String, shapes: Collection<HiiragiShape>) : this(name) {
        shapesInternal.addAll(shapes)
    }

    //    Copy    //

    fun child(name: String, vararg shape: HiiragiShape = arrayOf()) =
        HiiragiShapeType(name, this.shapes).also { shapeType: HiiragiShapeType ->
            shapeType.shapesInternal.addAll(shape)
        }

    override fun equals(other: Any?): Boolean = when (other) {
        null -> false
        !is HiiragiShapeType -> false
        else -> other.name == this.name
    }

    override fun hashCode(): Int = name.hashCode()

}