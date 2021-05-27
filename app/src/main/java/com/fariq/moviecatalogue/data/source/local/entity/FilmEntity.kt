package com.fariq.moviecatalogue.data.source.local.entity

data class FilmEntity (
        var id : String,
        var title : String? = "",
        var image : String? = "",
        var desc : String? = "",
        var genre : String? = "",
        var year : Int? = 0
)
