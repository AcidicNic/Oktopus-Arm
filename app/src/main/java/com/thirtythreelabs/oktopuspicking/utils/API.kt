package com.thirtythreelabs.oktopuspicking.utils

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.net.URI

object API {
    /**
     * Makes an API request to the server.
     *
     * @param params JSONObject The parameters to be sent to the server.
     * @param endpoint API Endpoint to be called, should be a key for Config.API_ENDPOINTS.
     * @return JSONObject? The response from the server, or null if the request was unsuccessful.
     */
    suspend fun handlePostRequest(params: Map<String, Any>, endpoint: String): JSONObject? = withContext(
        Dispatchers.IO) {

        val logTag = "POST ($endpoint)"
        Log.d(logTag, "Params: $params")

        try {
            // Check if the endpoint is valid
            if (Config.API_ENDPOINTS.containsKey(endpoint).not()) {
                Log.e(logTag, "Invalid endpoint: $endpoint")
                return@withContext null
            }

            val url = URI(Config.URL).resolve(Config.API_ENDPOINTS[endpoint]).toString()
            Log.d(logTag, "URI: $url")

            val client = OkHttpClient()
            val reqJson = JSONObject()

            for ((k, v) in params) {
                reqJson.put(k, v)
            }

            val reqStr = reqJson.toString()

            Log.d(logTag, "Request Body: $reqStr")

            val reqBody = reqStr.toRequestBody("application/json; charset=utf-8".toMediaType())

            val request = Request.Builder()
                .url(url)
                .post(reqBody)
                .build()

            Log.d(logTag, "Request: $request")

            client.newCall(request).execute().use { response ->
                Log.d(logTag, "Response: $response")
                if (response.isSuccessful) {
                    Log.d(logTag, "POST request successful")
                    try {
                        val resBody = response.body?.string()
                        val resJson = resBody?.let { JSONObject(it) }

                        Log.d(logTag, "Response body JSON: $resJson")

                        if (resJson?.getString("Error").equals("N")) {
                            return@withContext resJson
                        } else {
                            Log.e(logTag, "Server returned an error: ${resJson?.getString("ErrorMessage")}")
                        }
                    } catch (e: Exception) {
                        Log.e(logTag, "Exception while parsing JSON: $e")
                    }
                } else {
                    Log.e(logTag, "Error: POST request unsuccessful")
                }
            }

        } catch (e: Exception) {
            Log.e(logTag, "Exception while making request: $e")
        }

        Log.d(logTag, "Returning null")
        return@withContext null
    }
}
