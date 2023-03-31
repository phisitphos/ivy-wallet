package ivy.common.demo

import arrow.optics.optics

@optics
data class Address(
    val country: String,
    val city: String,
) {
    companion object // for Arrow optics
}

@optics
data class Person(
    val name: String,
    val address: Address
) {
    companion object // for Arrow optics
}

fun test() {
    val p1 = Person(
        name = "Person 1",
        address = Address(
            country = "Bulgaria",
            city = "Sofia"
        )
    )

    Person.address.city.modify(p1) { "City: $it" }
}