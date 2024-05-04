package com.fredy.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fredy.myapplication.ui.theme.MyApplicationTheme
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class MainActivity: ComponentActivity() {
    private val client = OkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                GPTApp()
            }
        }
    }
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun GPTApp() {
        var question by remember { mutableStateOf("") }
        var response by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            TextField(
                value = question,
                onValueChange = { question = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                label = { Text("Question:") },
                keyboardActions = KeyboardActions(
                    onSend = {
                        getResponse(question) { result ->
                            response = result
                        }
                    }
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Send
                ),
            )

            Button(
                onClick = {
                    getResponse(question) { result ->
                        response = result
                    }
                },
                modifier = Modifier.align(
                    Alignment.End)
            ) {
                Text(text = "Submit")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Question:")
            Text(text = question, style = TextStyle(fontSize = 18.sp))

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Response:")
            Text(text = response, style = TextStyle(fontSize = 18.sp))
        }
    }

    private fun getResponse(question: String, callback: (String) -> Unit) {
        val apiKey = "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"
        val url = "https://api.openai.com/v1/engines/text-davinci-003/completions"

        val requestBody = """
            {
            "prompt": "$question",
            "max_tokens": 500,
            "temperature": 0
            }
        """.trimIndent()
        val request = Request.Builder()
            .url(url)
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "Bearer $apiKey")
            .post(requestBody.toRequestBody("application/json".toMediaTypeOrNull()))
            .build()
        Log.e("error", "getResponse: "+request)
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("error", "API failed", e)
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                if (body != null) {
                    Log.v("error", body)
                    val jsonObject = JSONObject(body)
                    val jsonArray: JSONArray = jsonObject.getJSONArray("choices")
                    val textResult = jsonArray.getJSONObject(0).getString("text")
                    callback(textResult)
                } else {
                    Log.v("error", "empty")
                }

            }

        })
    }
}