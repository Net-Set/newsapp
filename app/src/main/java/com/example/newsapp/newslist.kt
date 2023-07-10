package com.example.newsapp
//
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//
//class newslist : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_newslist)
//    }
//}
import Article
import ArticleAdapter
import Source
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class newslist : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newslist)

        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchDataFromAPI()
    }

    private fun fetchDataFromAPI() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(" https://newsapi.org/v2/everything?q=IT&from=2023-07-09&sortBy=publishedAt&apiKey=a6dcb214eb204e61ab5cea48eefcc62d")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val jsonData = response.body?.string()
                val articles = parseJsonData(jsonData)
                runOnUiThread {
                    articleAdapter = ArticleAdapter(articles)
                    recyclerView.adapter = articleAdapter
                }
            }
        })
    }

    private fun parseJsonData(jsonData: String?): List<Article> {
        val articles = mutableListOf<Article>()
        val jsonObject = JSONObject(jsonData)
        val articlesArray: JSONArray = jsonObject.getJSONArray("articles")

        for (i in 0 until articlesArray.length()) {
            val articleObject = articlesArray.getJSONObject(i)
            val sourceObject = articleObject.optJSONObject("source")
            val source = Source(
                sourceObject?.optString("id"),
                sourceObject?.optString("name")
            )
            val author = articleObject.optString("author")
            val title = articleObject.optString("title")
            val description = articleObject.optString("description")
            val url = articleObject.optString("url")
            val urlToImage = articleObject.optString("urlToImage")
            val publishedAt = articleObject.optString("publishedAt")
            val content = articleObject.optString("content")

            val article = Article(source, author, title, description, url, urlToImage, publishedAt, content)
            articles.add(article)
        }

        return articles
    }
}
