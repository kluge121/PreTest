package com.kakaopay.kakaopaypretest

import android.app.Application
import com.kakaopay.kakaopaypretest.di.searchImageModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module


class PreTestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin()
    }


    private fun startKoin() {
        startKoin {
            androidContext(this@PreTestApplication)
            modules(getKoinModules())
        }
    }

    fun getKoinModules(): List<Module> = listOf(searchImageModule)
}