package com.example.testadaptivecards

import android.content.Context
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

class ReadJson internal constructor(private val context: Context, private val nameFile: String) {

    lateinit var configureLayout: ConfigureLayout

    @Throws(JSONException::class)
    private fun readJson(json: String) {
        val jObj = JSONObject(json)
        readSettingsLayout(jObj)
        readCards(jObj)
        readSuggestions(jObj)
        readDataCards(jObj)
    }

    @Throws(JSONException::class)
    private fun readSettingsLayout(jObj: JSONObject){
        val subObj = jObj.getJSONObject("layout")
        configureLayout = ConfigureLayout(
                subObj.getString("number"),
                subObj.getString("urlImage")
        )
    }

    @Throws(JSONException::class)
    private fun readCards(jObj: JSONObject){
        val jArr = jObj.getJSONArray("cards")
        val cardList = ArrayList<Cards>()

        for(num in 0 until jArr.length()) {
            val obj = jArr.getJSONObject(num)
            val card = Cards(obj.getString("file"), obj.getString("style"))
            cardList.add(card)
        }
        configureLayout.cards = cardList
    }


    @Throws(JSONException::class)
    private fun readSuggestions(jObj: JSONObject){
        if (jObj.has("suggestions")){
            val jArr = jObj.getJSONArray("suggestions")
            val cardList = ArrayList<Cards>()

            for(num in 0 until jArr.length()) {
                val obj = jArr.getJSONObject(num)
                val card = Cards(obj.getString("file"), obj.getString("style"))
                cardList.add(card)
            }
            configureLayout.suggestions = cardList
        }
    }


    @Throws(JSONException::class)
    private fun readDataCards(jObj: JSONObject){
        if (jObj.has("dataCards")){
            val jArr = jObj.getJSONArray("dataCards")
            val data = ArrayList<DataExtraCards>()

            for(num in 0 until jArr.length()) {
                val obj = jArr.getJSONObject(num)
                val card = DataExtraCards(obj.getString("id"), obj.getString("urlImage"))
                data.add(card)
            }
            configureLayout.dataExtraCardsList = data
        }
    }


    @Throws(JSONException::class)
    fun readFileFromAssets() {
        val outputStream = ByteArrayOutputStream()
        var ctr: Int

        val inputStream: InputStream?
        try {
            inputStream = context.assets.open(nameFile)

            try {
                ctr = inputStream!!.read()
                while (ctr != -1) {
                    outputStream.write(ctr)
                    ctr = inputStream.read()
                }
                inputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }

        readJson(outputStream.toString())
    }
}