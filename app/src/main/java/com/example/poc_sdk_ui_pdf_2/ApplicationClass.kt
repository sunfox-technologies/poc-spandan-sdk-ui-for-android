package com.example.poc_sdk_ui_pdf_2

import android.app.Application
import `in`.sunfox.healthcare.commons.android.spandan_sdk.SpandanSDK

class ApplicationClass : Application() {

    override fun onCreate() {
        super.onCreate()

        /**
         * Step-3 : initialize Spandan SDK in Application Class
         * **/
        SpandanSDK.initializeOffline(
            this,
            "< authentication_token provided by Sunfox Technologies Team>"
        )
    }
}