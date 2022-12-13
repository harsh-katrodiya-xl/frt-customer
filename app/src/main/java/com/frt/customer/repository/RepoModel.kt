package com.frt.customer.repository

import android.content.Context
import com.bsk.parentt.util.SharedPreference
import com.frt.customer.repository.rest.ApiService
import com.frt.customer.repository.rest.ApiService.Companion.createRetrofit
import com.frt.customer.repository.rest.ApiService.Companion.createRetrofitXml
import org.koin.core.KoinComponent

class RepoModel(context: Context) : KoinComponent {

    val appPreference = SharedPreference(context)
    var api = createRetrofit(appPreference).create(ApiService::class.java)
    var api_xml = createRetrofitXml(appPreference).create(ApiService::class.java)

}