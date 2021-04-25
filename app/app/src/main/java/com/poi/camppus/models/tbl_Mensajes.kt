package com.poi.camppus.models

import com.google.firebase.database.Exclude

class tbl_Mensajes(
    var id: String = "",
    var contenido: String = "",
    var de: String = "",
    var para: String = "",
    val timeStamp: Any? = null
) {
    @Exclude
    var esMio: Boolean = false
}



