package com.taruns.herway.models

import java.io.Serializable

data class UserModel(
    var name: String? = null,
    var age: Int? = null,
    var phone: String? = null,
    var email: String? = null,
    var pin: String? = null,
    var eContacts: ArrayList<ContactModel>? = ArrayList()
): Serializable {

    fun isNUll(): Boolean{
        return (name == null || age == null || phone == null || email == null || pin == null )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserModel

        if (name != other.name) return false
        if (age != other.age) return false
        if (phone != other.phone) return false
        if (email != other.email) return false
        if (pin != other.pin) return false
        if (eContacts?.equals(other.eContacts) == false) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + age!!
        result = 31 * result + phone.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + pin.hashCode()
        result = 31 * result + eContacts.hashCode()
        return result
    }
}
