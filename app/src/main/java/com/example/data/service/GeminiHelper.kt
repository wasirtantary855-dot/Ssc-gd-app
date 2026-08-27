package com.example.data.service

import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

object GeminiHelper {

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    suspend fun askGeminiTutor(userQuery: String, contextSubject: String? = null): String = withContext(Dispatchers.IO) {
        val apiKey = try {
            BuildConfig::class.java.getField("GEMINI_API_KEY").get(null) as? String ?: ""
        } catch (e: Exception) {
            ""
        }

        if (apiKey.isBlank()) {
            return@withContext generateOfflineTutorResponse(userQuery, contextSubject)
        }

        try {
            val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=$apiKey"
            val systemPrompt = """
                You are SSC GD Constable Exam AI Tutor (SSC GD परीक्षा गुरु). 
                Answer student questions in simple, easy-to-understand Hindi with English terms in brackets.
                Be encouraging, direct, and give clear steps, formulas, rules, or short tricks.
                Context Subject: ${contextSubject ?: "SSC GD Constable Exam Preparation"}
            """.trimIndent()

            val jsonBody = JSONObject().apply {
                put("contents", JSONArray().apply {
                    put(JSONObject().apply {
                        put("role", "user")
                        put("parts", JSONArray().apply {
                            put(JSONObject().put("text", "$systemPrompt\n\nStudent Question: $userQuery"))
                        })
                    })
                })
            }

            val request = Request.Builder()
                .url(url)
                .post(jsonBody.toString().toRequestBody("application/json".toMediaType()))
                .build()

            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val responseStr = response.body?.string() ?: ""
                val jsonObj = JSONObject(responseStr)
                val text = jsonObj.getJSONArray("candidates")
                    .getJSONObject(0)
                    .getJSONObject("content")
                    .getJSONArray("parts")
                    .getJSONObject(0)
                    .getString("text")
                return@withContext text
            } else {
                return@withContext generateOfflineTutorResponse(userQuery, contextSubject)
            }
        } catch (e: Exception) {
            return@withContext generateOfflineTutorResponse(userQuery, contextSubject)
        }
    }

    private fun generateOfflineTutorResponse(query: String, subject: String?): String {
        val q = query.lowercase()
        return when {
            q.contains("pattern") || q.contains("पैटर्न") -> """
                🎯 SSC GD परीक्षा पैटर्न (CBT Exam Pattern):
                • कुल प्रश्न: 80 | कुल अंक: 160 | कुल समय: 60 मिनट।
                • विषय वितरण:
                  1. रीजनिंग: 20 प्रश्न (40 अंक)
                  2. सामान्य ज्ञान (GK): 20 प्रश्न (40 अंक)
                  3. प्रारंभिक गणित: 20 प्रश्न (40 अंक)
                  4. हिंदी / अंग्रेजी: 20 प्रश्न (40 अंक)
                • 0.25 अंक का नकारात्मक अंकन (Negative Marking) है।
            """.trimIndent()

            q.contains("math") || q.contains("गणित") || q.contains("percentage") || q.contains("प्रतिशत") -> """
                💡 गणित (Mathematics) गुरु टिप:
                • प्रतिशतता निकालने का आसान सूत्र: प्रतिशत = (मान / कुल मान) × 100
                • प्रतिशत से भिन्न: 25% = 25/100 = 1/4 | 50% = 1/2 | 20% = 1/5
                • 1 से 30 तक के वर्ग (Squares) और 1 से 15 तक के घन (Cubes) याद रखें। इससे परीक्षा में गणना बहुत तेज़ हो जाएगी।
            """.trimIndent()

            q.contains("reasoning") || q.contains("रीजनिंग") || q.contains("ejoty") -> """
                🧩 रीजनिंग (Reasoning) शार्ट ट्रिक:
                • वर्णमाला स्थान के लिए EJOTY नियम याद रखें: E=5, J=10, O=15, T=20, Y=25।
                • अक्षरों के विपरीत जोड़े: Azad(A-Z), Boy(B-Y), Cox(C-X), Dew(D-W), High School(H-S), Indian Railway(I-R)।
            """.trimIndent()

            else -> """
                📘 SSC GD Constable तैयारी मार्गदर्शन:
                आपकी जिज्ञासा: "$query"

                1. प्रतिदिन 2 घंटे गणित और रीजनिंग का अभ्यास करें।
                2. सामान्य ज्ञान और समसामयिकी (Current Affairs) के मुख्य बिंदुओं का रोज रिवीजन करें।
                3. मॉक टेस्ट दें और अपनी गति (Speed) तथा शुद्धता (Accuracy) में सुधार करें।
                
                आगे की सहायता के लिए पुस्तक के संबंधित अध्याय को पढ़ें।
            """.trimIndent()
        }
    }
}
