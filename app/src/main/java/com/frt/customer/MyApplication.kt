package com.frt.customer

import android.app.Application
import android.content.Context
import com.frt.customer.repository.RepoModel
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

open class MyApplication : Application() {

    var _appContext: Context? = null

    override fun onCreate() {
        super.onCreate()
        _appContext = this

        val myModules = module {
            single { RepoModel(this@MyApplication) }
        }

        startKoin {
            androidLogger()
            modules(myModules)
        }
        //  Global.ACCESS_TOKEN = sessionManager.getUser() != null ? sessionManager.getUser().getData().getToken() : "";
        //   Global.USER_ID = sessionManager.getUser() != null ? sessionManager.getUser().getData().getUserId() : "";
    }

}