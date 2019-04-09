package com.example.testadaptivecards

import android.content.Context
import android.util.Log
import android.view.View
import com.example.testadaptivecards.FileHelper.loadFileFromAsset
import io.adaptivecards.objectmodel.ActionType
import io.adaptivecards.objectmodel.AdaptiveCard
import io.adaptivecards.objectmodel.BaseActionElement
import io.adaptivecards.objectmodel.BaseCardElement
import io.adaptivecards.objectmodel.HostConfig
import io.adaptivecards.renderer.AdaptiveCardRenderer
import io.adaptivecards.renderer.RenderedAdaptiveCard
import io.adaptivecards.renderer.actionhandler.ICardActionHandler

class CardsProcessor(private val context: Context) {

    private val TAG = "CardsProcessor"

     var EXAMPLE_JSON = listOf(
            "cardSamples/card-SelectSTB.json" to "cardSamples/styles-SelectSTB.json",
            "cardSamples/MH-card-SelectSTB.json" to "cardSamples/MH-styles-SelectSTB.json",
            "cardSamples/GVP-VideoCard-Novum2.json" to "cardSamples/styles-SelectSTB.json"
    )

    fun getCards(): List<View> {
        val renderer = AdaptiveCardRenderer.getInstance()
        return EXAMPLE_JSON.map { example ->

            val hostConfig = example.second?.let {
                HostConfig.DeserializeFromString(loadFileFromAsset(context, it))
            } ?: HostConfig()

            val parseResult =
                AdaptiveCard.DeserializeFromString(
                    loadFileFromAsset(context, example.first),
                    AdaptiveCardRenderer.VERSION
                )
            val adaptiveCard = parseResult.GetAdaptiveCard()
            val renderedCard = renderer.render(context, null, adaptiveCard, createCardHandler(), hostConfig)
            renderedCard.view
        }
    }

    private fun createCardHandler(): ICardActionHandler? = object : ICardActionHandler {
        override fun onAction(actionElement: BaseActionElement?, renderedAdaptiveCard: RenderedAdaptiveCard?) {
            when (actionElement?.GetElementType()) {
                ActionType.OpenUrl -> Log.d(TAG, "[onAction.OpenUrl]")
                ActionType.ShowCard -> Log.d(TAG, "[onAction.ShowCard]")
                ActionType.Submit -> Log.d(TAG, "[onAction.Submit]")
                ActionType.Custom -> Log.d(TAG, "[onAction.Custom]")
                ActionType.Unsupported,
                null -> Log.w(TAG, "[onAction] unexpected actiontype ${actionElement?.GetElementType()}")
            }
        }

        override fun onMediaPlay(p0: BaseCardElement?, p1: RenderedAdaptiveCard?) {
        }

        override fun onMediaStop(p0: BaseCardElement?, p1: RenderedAdaptiveCard?) {
        }

    }
}