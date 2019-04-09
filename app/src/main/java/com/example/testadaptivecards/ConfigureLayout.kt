package com.example.testadaptivecards

import java.io.Serializable
import java.util.ArrayList

class ConfigureLayout internal constructor(
        var numberType: String?,
        var urlImage: String?
) : Serializable {

    var cards: ArrayList<Cards>? = null
    var suggestions: ArrayList<Cards>? = null
    var dataExtraCardsList : ArrayList<DataExtraCards>? = null
}
