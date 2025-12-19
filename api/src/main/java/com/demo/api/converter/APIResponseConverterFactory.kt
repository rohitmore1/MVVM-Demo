package com.demo.api.converter

import com.google.gson.GsonBuilder
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import timber.log.Timber
import java.lang.reflect.Type

class APIResponseConverterFactory : Converter.Factory() {

    private val xmlFactory: Converter.Factory =
        SimpleXmlConverterFactory.create()

    private val jsonFactory: GsonConverterFactory =
        GsonConverterFactory.create(
            GsonBuilder()
                .setLenient()
                .create()
        )

    // ---------------- REQUEST BODY ----------------

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        return jsonFactory.requestBodyConverter(
            type,
            parameterAnnotations,
            methodAnnotations,
            retrofit
        )
    }

    // ---------------- RESPONSE BODY ----------------

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody?, out Any>? {

        return if (isXmlRequest(annotations)) {
            xmlFactory.responseBodyConverter(type, annotations, retrofit)
        } else {
            val delegate =
                jsonFactory.responseBodyConverter(type, annotations, retrofit)

            Converter<ResponseBody, Any> { body ->
                try {
                    delegate?.convert(body)
                } catch (e: Exception) {
                    Timber.e(e, "JSON Parsing Exception")
                    throw e   // ✅ DO NOT return null
                }
            }
        }
    }

    // ---------------- HELPERS ----------------

    private fun isXmlRequest(annotations: Array<Annotation>): Boolean {
        return annotations.any { it is Xml }
    }
}