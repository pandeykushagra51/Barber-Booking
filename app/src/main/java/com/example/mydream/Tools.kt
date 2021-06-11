package com.example.mydream

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.util.*
class Tools {
    companion object {

        fun ImageViewToByte(img: ImageView): ByteArray?  {
            var bmp: Bitmap? = null
            var bos: ByteArrayOutputStream? = null
            var bt: ByteArray? = null
            try {
                bmp = (img.drawable as BitmapDrawable).bitmap
                bos = ByteArrayOutputStream()
                bmp!!.compress(Bitmap.CompressFormat.JPEG, 100, bos)
                bt = bos.toByteArray()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return bt
        }

        suspend fun byteToBitmap(image: ByteArray): Bitmap = withContext(Dispatchers.Default){
            return@withContext BitmapFactory.decodeByteArray(image, 0, image.size)
        }

        fun RemoveDuplicate(list: MutableList<String?>, str1: String?): MutableList<String?> {
            val s: Set<String?> = LinkedHashSet(list)
            list.clear()
            list.addAll(s)
            return list
        }
    }
}