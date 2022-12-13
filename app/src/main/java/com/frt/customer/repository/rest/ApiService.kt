package com.frt.customer.repository.rest

import com.bsk.parentt.util.Constants
import com.bsk.parentt.util.SharedPreference
import com.google.gson.GsonBuilder
import com.frt.customer.repository.rest.responses.ErrorApiResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit

/**
 * All the API services are here.
 */

public interface ApiService {


    companion object {
        lateinit var retrofit: Retrofit
        lateinit var retrofitXml: Retrofit
        private val timeout = 30

        fun createRetrofit(appPreference: SharedPreference): Retrofit {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(createOkHttpClient(appPreference))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            return retrofit
        }

        fun createRetrofitXml(appPreference: SharedPreference): Retrofit {
            retrofitXml = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(createOkHttpClient(appPreference))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build()
            return retrofitXml
        }

        private fun createOkHttpClient(appPreference: SharedPreference): OkHttpClient {
            val builder = OkHttpClient.Builder()
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logging)


            builder.readTimeout(timeout.toLong(), TimeUnit.SECONDS)
            builder.writeTimeout(timeout.toLong(), TimeUnit.SECONDS)
            builder.connectTimeout(timeout.toLong(), TimeUnit.SECONDS)

            builder.addInterceptor { chain ->
                val originalRequest = chain.request()
                val originalUrl = originalRequest.url
                val url = originalUrl.newBuilder()
                    //.addQueryParameter("username", "demo")
                    .build()
                val requestBuilder = originalRequest.newBuilder()
                    .url(url)
                chain.proceed(requestBuilder.build())
            }
            return builder.build()
        }

        fun getError(response: Response<*>, errorMessage: String): ErrorApiResponse {
            var converter = retrofit.responseBodyConverter<ErrorApiResponse>(
                ErrorApiResponse::class.java,
                arrayOfNulls<Annotation>(0)
            )
            val error: ErrorApiResponse

            try {
                error = converter.convert(response.errorBody())!!
            } catch (e: Exception) {
                return ErrorApiResponse(
                    ErrorApiResponse.Error(1, "", "", ""),
                    ErrorApiResponse.Error(209, "", "" + errorMessage, ""),
                    ""
                )
            }
            return error
        }
    }

}

