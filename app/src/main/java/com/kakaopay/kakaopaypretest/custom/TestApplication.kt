package com.kakaopay.kakaopaypretest.custom

import android.app.Application
import com.kakaopay.kakaopaypretest.koin.searchModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import  org.koin.core.context.*
import org.koin.core.logger.Level
import org.koin.core.module.Module
import org.koin.dsl.module

class TestApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        startKoin()
    }


    private fun startKoin() {

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@TestApplication)
            modules(getAppModules())
        }

    }


    private fun getAppModules(): List<Module> {
        return listOf(searchModule)
    }

}
