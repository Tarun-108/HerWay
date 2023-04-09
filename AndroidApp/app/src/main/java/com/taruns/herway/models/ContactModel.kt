package com.taruns.herway.models

import java.io.Serializable

data class ContactModel(
    var name: String?="",
    var email: String?="",
    var phone: String?=""
): Serializable