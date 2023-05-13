package ivy.core.persistence.data

sealed interface ItemChange<in T> {
    data class Deletion<T>(val deleted: T) : ItemChange<T>
    data class Creation<T>(val created: T) : ItemChange<T>
    data class Update<T>(val before: T, val after: T) : ItemChange<T>
}
