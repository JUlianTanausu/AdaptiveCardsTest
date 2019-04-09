package com.example.testadaptivecards

import android.content.Context
import java.io.IOException
import java.nio.charset.Charset

object FileHelper {

    fun loadFileFromAsset(context: Context, fileName: String): String {
        val json: String
        try {
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, Charset.forName("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            return "{}"
        }

        return json
    }
}