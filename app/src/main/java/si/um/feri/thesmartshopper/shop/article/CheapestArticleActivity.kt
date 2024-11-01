package si.um.feri.thesmartshopper.shop.article

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import si.um.feri.thesmartshopper.R
import si.um.feri.thesmartshopper.recycler.cheapestArticle

class CheapestArticleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheapest_article)

        val articleName : TextView = findViewById(R.id.article_name)
        val articlePrice : TextView = findViewById(R.id.article_price)
        val articleDiscount : TextView = findViewById(R.id.article_discount)

        articleName.text = cheapestArticle.getName()
        articlePrice.text = "$" + (cheapestArticle.getPrice() * (1 - cheapestArticle.getDiscount())).toString()
        articleDiscount.text = cheapestArticle.getDiscount().toString() + "%"
    }
}