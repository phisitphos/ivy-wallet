package ivy.core.logic.data.common

/**
 * An object that can be archived.
 */
interface Archiveable {
    /**
     * Indicates whether this object is archived or not.
     *
     * @return `true` if the object is archived, `false` otherwise.
     */
    val archived: Boolean
}
