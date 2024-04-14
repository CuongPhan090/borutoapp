package com.cp.borutoapp.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.palette.graphics.Palette
import androidx.palette.graphics.Palette.Swatch
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult

object PaletteGenerator {

    suspend fun convertImageUrlToBitMap(imageUrl: String, context: Context): Bitmap? {
        val imageLoader = ImageLoader(context = context)
        val imageRequest = ImageRequest.Builder(context = context)
            .data(imageUrl)
            .allowHardware(false)
            .build()
        val imageResult = imageLoader.execute(imageRequest)

        return if (imageResult is SuccessResult) {
            (imageResult.drawable as? BitmapDrawable)?.bitmap
        } else {
            null
        }
    }

    fun extractColorsFromBitmap(bitmap: Bitmap): Map<String, String> {
        return mapOf(
            "vibrant" to parseVibrantSwatch((Palette.from(bitmap).generate().vibrantSwatch)),
            "darkVibrant" to parseVibrantSwatch(Palette.from(bitmap).generate().darkVibrantSwatch),
            "onDarkVibrant" to parseBodyTextColor(Palette.from(bitmap).generate().darkVibrantSwatch?.bodyTextColor)
        )
    }

    private fun parseVibrantSwatch(color: Swatch?): String {
        return if (color != null) {
            val parseColor = Integer.toHexString(color.rgb)
            "#$parseColor"
        } else {
            "#000000"
        }
    }

    private fun parseBodyTextColor(color: Int?): String {
        return if (color != null) {
            val parseColor = Integer.toHexString(color)
            "#$parseColor"
        } else {
            "#FFFFFF"
        }
    }
}