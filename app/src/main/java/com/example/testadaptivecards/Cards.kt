package com.example.testadaptivecards

import java.io.Serializable

class Cards internal constructor(
        var nameCard: String?,
        var nameStyle: String?
) : Serializable{
}
