package com.poi.camppus.models

import com.google.firebase.database.Exclude
import java.util.*

data class tbl_Mensajes (
        var message: String = "",
        var from: String = "",
        var dob: Date = Date(),
        var encriptado: Boolean? = false
)



