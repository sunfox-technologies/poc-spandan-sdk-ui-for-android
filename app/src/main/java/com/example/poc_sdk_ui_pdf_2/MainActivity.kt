package com.example.poc_sdk_ui_pdf_2

import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.text.method.ScrollingMovementMethod
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.poc_sdk_ui_pdf_2.databinding.ActivityMainBinding
import `in`.sunfox.healthcare.commons.android.spandan_sdk.SpandanSDK
import `in`.sunfox.healthcare.commons.android.spandan_sdk.enums.EcgTestType
import `in`.sunfox.healthcare.commons.android.spandan_sdk.listener.PDFReportGenerationCallback
import `in`.sunfox.healthcare.commons.android.spandan_sdk.retrofit_helper.PatientData
import `in`.sunfox.healthcare.commons.android.spandan_sdk.retrofit_helper.ReportGenerationResult
import `in`.sunfox.healthcare.commons.android.spandan_sdk_ui.helper.SpandanSDKUI
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var spandanSDK: SpandanSDK
    val TAG = "MainActivity.TAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        /**
         * Step-4 : get Spandan SDK instance
         * **/
        spandanSDK = SpandanSDK.getInstance()

        binding.leadTwelveBtn.setOnClickListener {
            binding.testResultTv.text = ""
            launchSpandanUi()
        }

        binding.testResultTv.movementMethod = ScrollingMovementMethod()

    }

    /**
     * launchSpandanUI is used to launch the Spandan SDK UI
     * **/
    private fun launchSpandanUi() {
        /**
         * Step-5 : add ecgTestType
         * **/
        val ecgTestType = EcgTestType.TWELVE_LEAD

        /**
         * Step-6 : launch Spandan UI by calling createTestAndStartWithUI method
         * **/
        SpandanSDKUI.createTestAndStartWithUI(
            activity = application,
            ecgTestType = ecgTestType,
            spandanSDK = spandanSDK,
            patientData = PatientData(
                age = "0",
                firstName = "firstName",
                lastName = "lastName",
                height = "100",
                weight = "52",
                gender = "Male",
                patientId = "PTID-103"
            ),
            pdfReportGenerationCallback = object : PDFReportGenerationCallback {
                override fun onReportGenerationFailed(errorMsg: String) {
                    Log.e(TAG, "onReportGenerationFailed: $errorMsg")
                }

                override fun onReportGenerationSuccess(reportGenerationResult: ReportGenerationResult) {
                    /**
                     * Step-7 : If report get, then save this report in your local storage using reportGenerationResult.pdfReportStream
                     * **/
                    val destFile = File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)!!.absolutePath + File.separator + "exports/test_file.pdf")
                    val file = destFile
                    val outputStream = FileOutputStream(file)
                    val inputStream = reportGenerationResult.pdfReportStream!!
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        inputStream.transferTo(outputStream)
                    } else {
                        copyStreamToFile(inputStream, file)
                    }

                    runOnUiThread {
                        binding.testResultTv.text = reportGenerationResult.characteristics.toString()
                    }
                }


            })
    }

    fun copyStreamToFile(inputStream: InputStream, outputFile: File) {
        inputStream.use { input ->
            val outputStream = FileOutputStream(outputFile)
            outputStream.use { output ->
                val buffer = ByteArray(4 * 1024) // buffer size
                while (true) {
                    val byteCount = input.read(buffer)
                    if (byteCount < 0) break
                    output.write(buffer, 0, byteCount)
                }
                output.flush()
            }
        }
    }

    override fun onDestroy() {
        spandanSDK.unbind()
        super.onDestroy()
    }
}