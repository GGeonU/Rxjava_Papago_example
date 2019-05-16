package com.example.dell.papago_retrofit_kotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var retrofitService: RetrofitService
    private val baseUrl = "https://openapi.naver.com/"
    private lateinit var lang: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = ArrayAdapter.createFromResource(this, R.array.lang, R.layout.support_simple_spinner_dropdown_item)
        val spinner = findViewById<Spinner>(R.id.spinner)

        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                if (parent != null) {
                    lang = parent.getItemAtPosition(0).toString()
                }
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (parent != null) {
                    lang = parent.getItemAtPosition(position).toString()
                    Log.d("aa", lang)
                }
            }
        }

        trans_button.setOnClickListener {
            translatedText()   // flatMap 예제
        }
        zipObservable() // zip 예제

    }

    private fun getRetrofitService(): RetrofitService {    // retrofitservice 객체
        retrofitService = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(RetrofitService::class.java)
        return retrofitService
    }

    private fun translatedText() {

        val text = request_text.text.toString()
        val sense = getRetrofitService().senseLanguage(text)   // API A -> 언어감지 API,
        sense.subscribeOn(Schedulers.io())                                             // 비동기 처리
                .observeOn(AndroidSchedulers.mainThread())                             // 결과를 받는 Thread
                .subscribe({
                    Log.d("sense", it.langCode)
                }, {
                    Log.d("sense error", it.message)
                })

        val trans = sense.flatMap { s -> getRetrofitService().translatedRequest(s.langCode, lang, text) }  // API B -> 언어 번역 API
        trans.subscribeOn(Schedulers.io())                                            // 비동기 처리
                .subscribe({
                    response_text.text = "source text = $text \ntranslation text = ${it.message.result.translatedText}"     // 번역 결과
                }, {
                    Log.d("trans error", it.message)
                })
    }

    private fun zipObservable() {

        val observableA = getRetrofitService().translatedRequest("en", "ko", "eternal")
        val observableB = getRetrofitService().translatedRequest("en", "ko", "happiness")

        val afterZip = Observable.zip(observableA, observableB, BiFunction<ResponseItem, ResponseItem, String>    // Observable A와 B의 문자열을 연결하는 zip
        { t1, t2 -> t1.message.result.translatedText +" "+ t2.message.result.translatedText })

        afterZip.subscribeOn(Schedulers.io())
                .subscribe ({
                    previous.text = "Observable A: ${observableA.blockingFirst().message.result.translatedText}  Observable B: ${observableB.blockingFirst().message.result.translatedText}"
                    after.text = "after zip: $it"
                },{
                    Log.d("zip error", it.message)
                })
    }

}
