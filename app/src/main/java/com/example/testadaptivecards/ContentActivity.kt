package com.example.testadaptivecards

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_content.*
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_fiveth.*
import android.os.Handler
import android.view.View

class ContentActivity : AppCompatActivity() {

    private lateinit var relative : RelativeLayout
    private var layoutSelected : Int = 0
    private lateinit var settingsLayout : ConfigureLayout
    private var contRotateSg : Int = 0
    private var cont : Int = 0
    private lateinit var pairList :ArrayList<Pair<String,String>>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)
        getExtras()
        relative =  findViewById(R.id.layout_container)
        setupClicks()
        layooutInflate()
        checkAndExecute()
    }

    private fun checkAndExecute(){
        getCards()
        loadImage()
        if (settingsLayout.suggestions != null)
            loadSuggestions()
        if (settingsLayout.numberType.equals("3"))
            setFindTotal()
    }

    @SuppressLint("SetTextI18n")
    private fun setFindTotal(){
        text_total.text = settingsLayout.cards!!.size.toString() + " alojamientos encontrados en Menorca"
    }

    private fun setupClicks(){
        image_back.setOnClickListener { finish() }
    }

    private fun getExtras(){
        val i = intent
        settingsLayout = i.getSerializableExtra("configuration") as ConfigureLayout
        when (settingsLayout.numberType) {
            "1" -> layoutSelected = R.layout.layout_first
            "2" -> layoutSelected = R.layout.layout_second
            "3" -> layoutSelected = R.layout.layout_third
            "4" -> layoutSelected = R.layout.layout_fourth
            "5" -> layoutSelected = R.layout.layout_fiveth
        }
    }

    private fun getCards(){
        val cards = CardsProcessor(this)
        val pairList = ArrayList<Pair<String,String>>()
        for (data in settingsLayout.cards!!) {
            val pair = Pair("cardSamples/"+data.nameCard+".json","styles/"+data.nameStyle+".json")
            pairList.add(pair)
        }

        cards.EXAMPLE_JSON = (pairList.toList())
        val mycards = cards.getCards()

        if (mycards.size >1) {
            changeImageByDataDefaultCard()
            for (num in 0 until mycards.size) {
                setupOnClick(num, mycards)
                adaptative_cards.addView(mycards[num])
            }
        } else {
            mycards.forEach {
                adaptative_cards.addView(it)
            }
        }
    }

    private fun setupOnClick(num : Int, mycards: List<View>){
        mycards[num].setOnFocusChangeListener { _, hasFocus ->
            run {
                if (hasFocus) selectCard(num)
            }
        }
        mycards[num].setOnClickListener {}
    }

    private fun changeImageByDataDefaultCard(){
        settingsLayout.urlImage = settingsLayout.dataExtraCardsList!![0].urlImage
    }

    private fun selectCard(position: Int){
        for (data in settingsLayout.cards!!){
            if (data.nameStyle.equals("apartamentos_style_selected")) {
                data.nameStyle = "apartamentos_style"
                break
            }
        }
        settingsLayout.cards!![position].nameStyle = "apartamentos_style_selected"
        settingsLayout.urlImage = settingsLayout.dataExtraCardsList!![position].urlImage
        loadImage()
        adaptative_cards.removeAllViews()
        getCards()
    }

    private fun loadImage(){
        Picasso.with(this)
                .load(settingsLayout.urlImage)
                .into(image_view_background)
    }

    private fun loadSuggestions(){
        if (settingsLayout.suggestions!!.size <= 3) {
            val cards = CardsProcessor(this)
            pairList = ArrayList()

            for (data in settingsLayout.suggestions!!) {
                val pair = Pair("suggestionsCards/" + data.nameCard + ".json", "styles/" + data.nameStyle + ".json")
                pairList.add(pair)
            }

            cards.EXAMPLE_JSON = (pairList.toList())
            val mycards = cards.getCards()

            mycards.forEach {
                adaptative_cards_suggestions.addView(it)
            }
        }else{
            rotateSuggestions()
        }
    }

    private fun rotateSuggestions() {
        adaptative_cards_suggestions.removeAllViews()
        pairList = ArrayList()

        for (num in contRotateSg until settingsLayout.suggestions!!.size){
            if (cont != 3) {
                val pair = Pair("suggestionsCards/" + settingsLayout.suggestions!![num].nameCard + ".json", "styles/" + settingsLayout.suggestions!![num].nameStyle + ".json")
                pairList.add(pair)
                cont++
            }
        }
        contRotateSg += cont
        if ( contRotateSg == settingsLayout.suggestions!!.size){
            contRotateSg=0
        }
        cont = 0
        paintSuggestions(pairList)
        handlerRotateSuggestions()
    }

    private fun paintSuggestions(pair :ArrayList<Pair<String,String>>){
         val cards = CardsProcessor(this)
         cards.EXAMPLE_JSON = (pair.toList())
         val mycards = cards.getCards()

         mycards.forEach {
             adaptative_cards_suggestions.addView(it)
         }
    }

    private fun layooutInflate(){
        val contenedor = findViewById<RelativeLayout>(R.id.layout_container)
        val inflater = LayoutInflater.from(this)
        inflater.inflate(layoutSelected, contenedor, true)
    }

    private fun handlerRotateSuggestions(){
        val handler = Handler()
        handler.postDelayed({ rotateSuggestions()}, 3000)
    }
}