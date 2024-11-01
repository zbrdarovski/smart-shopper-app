package si.um.feri.thesmartshopper.shop

import si.um.feri.thesmartshopper.shop.article.Article
import java.util.*

class Shop (private var name : String, private var distance: Float, private var address : String, private var articles: MutableList<Article>){
    val id = UUID.randomUUID().toString().replace("-", "")

    override fun toString(): String {
        return "$name $address ($distance km)"
    }

    fun addArticle(a : Article){
        articles.add(a)
    }

    fun getName() : String {
        return name
    }

    fun getAddress() : String {
        return address
    }

    fun getArticles() : MutableList<Article> {
        return articles
    }

    fun getDistance() : Float {
        return distance
    }
}