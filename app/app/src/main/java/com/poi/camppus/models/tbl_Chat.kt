package com.poi.camppus.models

data class tbl_Chat(
    var id: String = "",
    var name: String = "",
    var users: List<String> = emptyList()
)