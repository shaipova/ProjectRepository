package com.example.ui_core

import com.example.core_api.dto.ColorItem

object ColorUtils {
    fun getColorResByName(colorName: String?): Int {
        return when (colorName) {
            "violet_basic" -> R.color.violet_basic
            "pink_doll" -> R.color.pink_doll
            "violet_dark" -> R.color.violet_dark
            "red_basic" -> R.color.red_basic
            "red_dark" -> R.color.red_dark
            "orange_basic" -> R.color.orange_basic
            "ocher" -> R.color.ocher
            "green_apple" -> R.color.green_apple
            "green_grass" -> R.color.green_grass
            "green_ocean" -> R.color.green_ocean
            "blue_basic" -> R.color.blue_basic
            "navy" -> R.color.navy
            "pink_tulip" -> R.color.pink_tulip
            "purple_light" -> R.color.purple_light
            "pink_peach" -> R.color.pink_peach
            "orange_light" -> R.color.orange_light
            "yellow" -> R.color.yellow
            "cyan" -> R.color.cyan
            "green_mermaid" -> R.color.green_mermaid
            "green_soap" -> R.color.green_soap
            "blue_grey" -> R.color.blue_grey
            "green_sage" -> R.color.green_sage
            "grey" -> R.color.grey
            "blue_jeans" -> R.color.blue_jeans
            else -> R.color.violet_basic
        }
    }

    fun getColorItemList(): List<ColorItem> = listOf(
        ColorItem(false, "violet_basic"),
        ColorItem(false, "pink_doll"),
        ColorItem(false, "violet_dark"),
        ColorItem(false, "red_basic"),
        ColorItem(false, "red_dark"),
        ColorItem(false, "orange_basic"),
        ColorItem(false, "ocher"),
        ColorItem(false, "green_apple"),
        ColorItem(false, "green_grass"),
        ColorItem(false, "green_ocean"),
        ColorItem(false, "blue_basic"),
        ColorItem(false, "navy"),
        ColorItem(false, "pink_tulip"),
        ColorItem(false, "purple_light"),
        ColorItem(false, "pink_peach"),
        ColorItem(false, "orange_light"),
        ColorItem(false, "yellow"),
        ColorItem(false, "cyan"),
        ColorItem(false, "green_mermaid"),
        ColorItem(false, "green_soap"),
        ColorItem(false, "blue_grey"),
        ColorItem(false, "green_sage"),
        ColorItem(false, "grey"),
        ColorItem(false, "blue_jeans")
    )
}