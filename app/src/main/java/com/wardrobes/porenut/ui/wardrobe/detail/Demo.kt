package com.wardrobes.porenut.ui.wardrobe.detail

object Demo {

    private const val POSTERS_PATH = "https://raw.githubusercontent.com/stfalcon-studio/StfalconImageViewer/master/images/posters"

    val posters = listOf(
        Poster("$POSTERS_PATH/Vincent.jpg"),
        Poster("$POSTERS_PATH/Jules.jpg"),
        Poster("$POSTERS_PATH/Korben.jpg"),
        Poster("$POSTERS_PATH/Toretto.jpg"),
        Poster("$POSTERS_PATH/Marty.jpg"),
        Poster("$POSTERS_PATH/Driver.jpg"),
        Poster("$POSTERS_PATH/Frank.jpg"),
        Poster("$POSTERS_PATH/Max.jpg"),
        Poster("$POSTERS_PATH/Daniel.jpg")
    )
}

data class Poster(val url: String)