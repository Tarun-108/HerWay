package com.taruns.herway.models

data class UserModel(
    var name: String,
    var age: Int,
    var phone: String,
    var email: String,
    var pin: Int,
    var eContacts: Array<ContactModel>
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserModel

        if (name != other.name) return false
        if (age != other.age) return false
        if (phone != other.phone) return false
        if (email != other.email) return false
        if (pin != other.pin) return false
        if (!eContacts.contentEquals(other.eContacts)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + age
        result = 31 * result + phone.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + pin
        result = 31 * result + eContacts.contentHashCode()
        return result
    }
}
