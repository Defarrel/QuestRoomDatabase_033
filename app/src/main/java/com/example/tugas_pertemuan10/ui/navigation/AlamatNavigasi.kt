package com.example.tugas_pertemuan10.ui.navigation

interface AlamatNavigasi {
    val route: String

    object DestinationInsert : AlamatNavigasi{
        override val route = "home"
    }

    object DestinationDetail : AlamatNavigasi{
        override val route =  "detail"
        const val NIM = "nim"
        val routeWithArgs = "$route/{$NIM}"
    }

    object DestinationUpdate : AlamatNavigasi{
        override val route =  "update"
        const val NIM = "nim"
        val routeWithArgs = "$route/{$NIM}"
    }
}