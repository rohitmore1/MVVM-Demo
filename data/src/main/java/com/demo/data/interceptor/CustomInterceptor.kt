/*
package com.demo.data.interceptor

import okhttp3.*
import okhttp3.internal.http.hasBody
import okhttp3.internal.http.promisesBody
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import okio.BufferedSource
import okio.GzipSource
import timber.log.Timber
import java.io.EOFException
import java.io.IOException
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

class CustomInterceptor(
    private val logger: HttpLoggingInterceptor.Logger =
        HttpLoggingInterceptor.Logger.DEFAULT
) : Interceptor {

    companion object {
        private val UTF8: Charset = Charset.forName("UTF-8")
    }

    private var maxSizeForLogInByte: Long = 2_000_000

    @Volatile
    private var level: HttpLoggingInterceptor.Level =
        HttpLoggingInterceptor.Level.NONE

    fun setLevel(level: HttpLoggingInterceptor.Level?): CustomInterceptor {
        this.level = level ?: HttpLoggingInterceptor.Level.NONE
        return this
    }

    fun getLevel(): HttpLoggingInterceptor.Level = level

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val currentLevel = level
        val request = chain.request()

        if (currentLevel == HttpLoggingInterceptor.Level.NONE) {
            return chain.proceed(request)
        }

        val logBody = currentLevel == HttpLoggingInterceptor.Level.BODY
        val logHeaders = logBody || currentLevel == HttpLoggingInterceptor.Level.HEADERS

        val requestBody = request.body
        val hasRequestBody = requestBody != null

        val connection = chain.connection()
        var requestStartMessage = buildString {
            append("--> ${request.method} ${request.url}")
            if (connection != null) append(" ${connection.protocol()}")
            if (!logHeaders && hasRequestBody) {
                append(" (${requestBody!!.contentLength()}-byte body)")
            }
        }

        Timber.d(requestStartMessage)

        if (logHeaders) {
            if (hasRequestBody) {
                requestBody?.contentType()?.let {
                    Timber.d("Content-Type: $it")
                }
                if (requestBody?.contentLength() != -1L) {
                    Timber.d("Content-Length: ${requestBody?.contentLength()}")
                }
            }

            val headers = request.headers
            for (i in 0 until headers.size) {
                val name = headers.name(i)
                if (!name.equals("Content-Type", true)
                    && !name.equals("Content-Length", true)
                ) {
                    Timber.d("$name: ${headers.value(i)}")
                }
            }

            if (!logBody || !hasRequestBody) {
                Timber.d("--> END ${request.method}")
            } else if (bodyHasUnknownEncoding(request.headers)) {
                Timber.d("--> END ${request.method} (encoded body omitted)")
            } else {
                val buffer = Buffer()
                requestBody!!.writeTo(buffer)

                var charset = UTF8
                requestBody.contentType()?.charset(UTF8)?.let {
                    charset = it
                }

                Timber.d("")
                if (isPlaintext(buffer)) {
                    if (buffer.size < maxSizeForLogInByte) {
                        Timber.d(buffer.readString(charset))
                    }
                    Timber.d(
                        "--> END ${request.method} (${requestBody.contentLength()}-byte body)"
                    )
                } else {
                    Timber.d(
                        "--> END ${request.method} (binary ${requestBody.contentLength()}-byte body omitted)"
                    )
                }
            }
        }

        val startNs = System.nanoTime()
        val response: Response = try {
            chain.proceed(request)
        } catch (e: Exception) {
            Timber.d("<-- HTTP FAILED: $e")
            throw e
        }

        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)
        val responseBody = response.body!!
        val contentLength = responseBody.contentLength()
        val bodySize = if (contentLength != -1L) "$contentLength-byte" else "unknown-length"

        Timber.d(
            "<-- ${response.code}${if (response.message.isNotEmpty()) " ${response.message}" else ""} " +
                    "${response.request.url} (${tookMs}ms${if (!logHeaders) ", $bodySize body" else ""})"
        )

        if (logHeaders) {
            val headers = response.headers
            for (i in 0 until headers.size) {
                Timber.d("${headers.name(i)}: ${headers.value(i)}")
            }

            if (!logBody || !response.promisesBody()) {
                Timber.d("<-- END HTTP")
            } else if (bodyHasUnknownEncoding(headers)) {
                Timber.d("<-- END HTTP (encoded body omitted)")
            } else {
                val source: BufferedSource = responseBody.source()
                source.request(Long.MAX_VALUE)

                var buffer = source.buffer
                var gzippedLength: Long? = null

                if ("gzip".equals(headers["Content-Encoding"], true)) {
                    gzippedLength = buffer.size
                    GzipSource(buffer.clone()).use { gzipSource ->
                        buffer = Buffer()
                        buffer.writeAll(gzipSource)
                    }
                }

                var charset = UTF8
                responseBody.contentType()?.charset(UTF8)?.let {
                    charset = it
                }

                if (!isPlaintext(buffer)) {
                    Timber.d("")
                    Timber.d("<-- END HTTP (binary ${buffer.size}-byte body omitted)")
                    return response
                }

                if (contentLength != 0L) {
                    Timber.d("")
                    if (buffer.clone().size < maxSizeForLogInByte) {
                        Timber.d(buffer.clone().readString(charset))
                    }
                }

                if (gzippedLength != null) {
                    Timber.d(
                        "<-- END HTTP (${buffer.size}-byte, $gzippedLength-gzipped-byte body)"
                    )
                } else {
                    Timber.d("<-- END HTTP (${buffer.size}-byte body)")
                }
            }
        }

        return response
    }

    private fun bodyHasUnknownEncoding(headers: Headers): Boolean {
        val contentEncoding = headers["Content-Encoding"]
        return contentEncoding != null &&
                !contentEncoding.equals("identity", true) &&
                !contentEncoding.equals("gzip", true)
    }

    private fun isPlaintext(buffer: Buffer): Boolean {
        return try {
            val prefix = Buffer()
            val byteCount = minOf(buffer.size, 64)
            buffer.copyTo(prefix, 0, byteCount)
            repeat(16) {
                if (prefix.exhausted()) return true
                val codePoint = prefix.readUtf8CodePoint()
                if (Character.isISOControl(codePoint) &&
                    !Character.isWhitespace(codePoint)
                ) {
                    return false
                }
            }
            true
        } catch (e: EOFException) {
            false
        } catch (e: Exception) {
            false
        }
    }
}
*/

package com.demo.data.interceptor

import okhttp3.*
import okhttp3.internal.http.promisesBody
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import okio.BufferedSource
import okio.GzipSource
import timber.log.Timber
import java.io.EOFException
import java.io.IOException
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

class CustomInterceptor(
    private val logger: HttpLoggingInterceptor.Logger =
        HttpLoggingInterceptor.Logger.DEFAULT
) : Interceptor {

    companion object {
        private val UTF8: Charset = Charset.forName("UTF-8")
        private const val MAX_LOG_SIZE = 2_000_000L
    }

    @Volatile
    private var level: HttpLoggingInterceptor.Level =
        HttpLoggingInterceptor.Level.NONE

    fun setLevel(level: HttpLoggingInterceptor.Level?): CustomInterceptor = apply {
        this.level = level ?: HttpLoggingInterceptor.Level.NONE
    }

    fun getLevel(): HttpLoggingInterceptor.Level = level

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (level == HttpLoggingInterceptor.Level.NONE) {
            return chain.proceed(chain.request())
        }

        val request = chain.request()
        logRequest(chain, request)

        val startNs = System.nanoTime()
        val response = try {
            chain.proceed(request)
        } catch (e: Exception) {
            Timber.e(e, "<-- HTTP FAILED")
            throw e
        }

        logResponse(response, startNs)
        return response
    }

    /* -------------------- REQUEST -------------------- */

    private fun logRequest(chain: Interceptor.Chain, request: Request) {
        val logBody = level == HttpLoggingInterceptor.Level.BODY
        val logHeaders = logBody || level == HttpLoggingInterceptor.Level.HEADERS
        val requestBody = request.body

        val startLine = buildString {
            append("--> ${request.method} ${request.url}")
            chain.connection()?.protocol()?.let { append(" $it") }
            if (!logHeaders && requestBody != null) {
                append(" (${requestBody.contentLength()}-byte body)")
            }
        }
        Timber.d(startLine)

        if (!logHeaders) return

        logRequestHeaders(request, requestBody)

        if (!logBody || requestBody == null) {
            Timber.d("--> END ${request.method}")
            return
        }

        if (bodyHasUnknownEncoding(request.headers)) {
            Timber.d("--> END ${request.method} (encoded body omitted)")
            return
        }

        logRequestBody(request, requestBody)
    }

    private fun logRequestHeaders(request: Request, body: RequestBody?) {
        body?.contentType()?.let { Timber.d("Content-Type: $it") }
        if (body?.contentLength() != -1L) {
            Timber.d("Content-Length: ${body?.contentLength()}")
        }
        val headers = request.headers
        for (i in 0 until headers.size) {
            val name = headers.name(i)
            val value = headers.value(i)

            if (!name.equals("Content-Type", true)
                && !name.equals("Content-Length", true)
            ) {
                Timber.d("$name: $value")
            }
        }
    }

    private fun logRequestBody(request: Request, body: RequestBody) {
        val buffer = Buffer()
        body.writeTo(buffer)

        val charset = body.contentType()?.charset(UTF8) ?: UTF8
        Timber.d("")

        if (!isPlaintext(buffer)) {
            Timber.d(
                "--> END ${request.method} (binary ${body.contentLength()}-byte body omitted)"
            )
            return
        }

        if (buffer.size < MAX_LOG_SIZE) {
            Timber.d(buffer.readString(charset))
        }

        Timber.d(
            "--> END ${request.method} (${body.contentLength()}-byte body)"
        )
    }

    /* -------------------- RESPONSE -------------------- */

    private fun logResponse(response: Response, startNs: Long) {
        val logBody = level == HttpLoggingInterceptor.Level.BODY
        val logHeaders = logBody || level == HttpLoggingInterceptor.Level.HEADERS
        val responseBody = response.body ?: return

        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)
        val contentLength = responseBody.contentLength()
        val bodySize = if (contentLength != -1L) "$contentLength-byte" else "unknown-length"

        Timber.d(
            "<-- ${response.code}${response.message.takeIf { it.isNotEmpty() }?.let { " $it" } ?: ""} " +
                    "${response.request.url} (${tookMs}ms${if (!logHeaders) ", $bodySize body" else ""})"
        )

        if (!logHeaders) return

        val headers = response.headers
        for (i in 0 until headers.size) {
            Timber.d("${headers.name(i)}: ${headers.value(i)}")
        }

        if (!logBody || !response.promisesBody()) {
            Timber.d("<-- END HTTP")
            return
        }

        if (bodyHasUnknownEncoding(response.headers)) {
            Timber.d("<-- END HTTP (encoded body omitted)")
            return
        }

        logResponseBody(response, responseBody)
    }

    private fun logResponseBody(response: Response, body: ResponseBody) {
        val source: BufferedSource = body.source()
        source.request(Long.MAX_VALUE)

        var buffer = source.buffer
        var gzippedLength: Long? = null

        if ("gzip".equals(response.headers["Content-Encoding"], true)) {
            gzippedLength = buffer.size
            GzipSource(buffer.clone()).use {
                buffer = Buffer()
                buffer.writeAll(it)
            }
        }

        val charset = body.contentType()?.charset(UTF8) ?: UTF8

        if (!isPlaintext(buffer)) {
            Timber.d("")
            Timber.d("<-- END HTTP (binary ${buffer.size}-byte body omitted)")
            return
        }

        if (buffer.size > 0 && buffer.size < MAX_LOG_SIZE) {
            Timber.d("")
            Timber.d(buffer.clone().readString(charset))
        }

        if (gzippedLength != null) {
            Timber.d("<-- END HTTP (${buffer.size}-byte, $gzippedLength-gzipped-byte body)")
        } else {
            Timber.d("<-- END HTTP (${buffer.size}-byte body)")
        }
    }

    /* -------------------- HELPERS -------------------- */

    private fun bodyHasUnknownEncoding(headers: Headers): Boolean {
        val encoding = headers["Content-Encoding"]
        return encoding != null &&
                !encoding.equals("identity", true) &&
                !encoding.equals("gzip", true)
    }

    private fun isPlaintext(buffer: Buffer): Boolean {
        return try {
            val prefix = Buffer()
            val byteCount = minOf(buffer.size, 64)
            buffer.copyTo(prefix, 0, byteCount)

            repeat(16) {
                if (prefix.exhausted()) return true
                val codePoint = prefix.readUtf8CodePoint()
                if (Character.isISOControl(codePoint)
                    && !Character.isWhitespace(codePoint)
                ) {
                    return false
                }
            }
            true
        } catch (_: EOFException) {
            false
        }
    }
}