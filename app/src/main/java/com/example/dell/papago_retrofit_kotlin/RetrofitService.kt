package com.example.dell.papago_retrofit_kotlin

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.*

interface RetrofitService {

    @Headers(
            "X-Naver-Client-Id: diCcTHVEt06BFk50h71U",
            "X-Naver-Client-Secret: 7ssLW7XyHr"
    )
    @FormUrlEncoded
    @POST("v1/papago/detectLangs")
    fun senseLanguage(@Field("query") query: String)
            : Observable<LangCode>

    @Headers(
            "X-Naver-Client-Id: diCcTHVEt06BFk50h71U",
            "X-Naver-Client-Secret: 7ssLW7XyHr"
    )
    @FormUrlEncoded
    @POST("v1/papago/n2mt")
    fun translatedRequest(@Field("source") source: String, @Field("target") target: String, @Field("text") text: String)
            : Observable<ResponseItem>




}