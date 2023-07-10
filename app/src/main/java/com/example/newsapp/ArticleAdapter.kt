import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.squareup.picasso.Picasso

class ArticleAdapter(private val articles: List<Article>) :
    RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val authorTextView: TextView = itemView.findViewById(R.id.authorTextView)
        val publishedAtTextView: TextView = itemView.findViewById(R.id.publishedAtTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles[position]
        holder.titleTextView.text = article.title
        holder.descriptionTextView.text = article.description
        holder.authorTextView.text = article.author
        holder.publishedAtTextView.text = article.publishedAt
        Picasso.get().load(article.urlToImage).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return articles.size
    }
}
