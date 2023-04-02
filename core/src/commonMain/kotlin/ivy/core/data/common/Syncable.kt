package ivy.core.data.common

import java.time.LocalDateTime
import java.util.*

/**
 * Indicates that an object has a unique identifier.
 */
interface Identifiable {
    /**
     * The unique identifier of this object.
     */
    val id: UUID
}

/**
 * Indicates that an object can be marked as deleted.
 */
interface Deletable {
    /**
     * A "tombstone" flag. Whether this object is marked as deleted or not.
     */
    val deleted: Boolean
}

/**
 * Indicates that an object can be synchronized with a remote data source.
 *
 * This interface extends both [Identifiable] and [Deletable], and adds a [lastUpdated] property
 * to indicate when the object was last updated on the remote data source.
 */
interface Syncable : Identifiable, Deletable {
    /**
     * The timestamp of the last update of this object.
     */
    val lastUpdated: LocalDateTime
}
