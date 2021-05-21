package com.poi.camppus.models

data class tbl_asssigments(
    var id:String = "",
    var title:String = "",
    var description:String = "",
    var valor:String = "",
    var puntos:String = "0",
    var users: List<String> = emptyList(),
    var team:String = "",
    var trabajo:String = ""

)