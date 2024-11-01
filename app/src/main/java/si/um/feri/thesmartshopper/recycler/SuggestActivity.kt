package si.um.feri.thesmartshopper.recycler

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.github.serpro69.kfaker.faker
import org.apache.commons.io.FileUtils
import si.um.feri.thesmartshopper.R
import si.um.feri.thesmartshopper.shop.Shop
import si.um.feri.thesmartshopper.shop.article.Article
import timber.log.Timber
import java.io.File
import java.io.IOException
import kotlin.random.Random

lateinit var shops: MutableList<Shop>

lateinit var nearestShop: Shop
lateinit var cheapestShop: Shop
lateinit var cheapestArticle: Article

class SuggestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suggest)

        shops = mutableListOf()
        val gson: Gson = GsonBuilder().setPrettyPrinting().create()
        val file = File("/data/data/si.um.feri.thesmartshopper/files", "shops.json")

        val faker = faker { }

        for (i in 0..5) {
            val articleList = mutableListOf<Article>()
            for (j in 0..5) {
                val a = Article(faker.food.fruits(), Random.nextFloat(), Random.nextFloat())
                articleList.add(a)
            }
            val s = Shop(faker.funnyName.name(), Random.nextFloat(), faker.address.streetAddress(), articleList)
            shops.add(s)
        }

        try {
            FileUtils.writeStringToFile(file, gson.toJson(shops))
            Timber.d("Save to file.")
        } catch (e: IOException) {
            Timber.d("Can't save " + file.path)
        }

        shops = try {
            Timber.d("My file data:${FileUtils.readFileToString(file)}")
            gson.fromJson(
                FileUtils.readFileToString(file),
                object : TypeToken<List<Shop>>() {}.type
            )
        } catch (e: IOException) {
            Timber.d("No file init data.")
            mutableListOf()
        }

        nearestShop = shops[0]

        for (i in 1..5) {
            if (nearestShop.getDistance() > shops[i].getDistance())
                nearestShop = shops[i]
        }

        val cheapestShops = mutableListOf<Float>()

        for (i in 0..5) {
            cheapestShops.add(0f)
        }

        for (i in 0..5) {
            for (j in 0..5) {
                cheapestShops[i] += shops[i].getArticles()[j].getPrice()
            }
        }

        cheapestShop = shops[0]

        val tempCheapestShop = cheapestShops[0]

        for (i in 1..5) {
            if(tempCheapestShop > cheapestShops[i]){
                cheapestShop = shops[i]
            }
        }

        cheapestArticle = shops[0].getArticles()[0]
        var tempShop = shops[0]

        for (i in 0..5) {
            for (j in 0..5) {
                if(cheapestArticle.getPrice() > shops[i].getArticles()[j].getPrice()) {
                    cheapestArticle = shops[i].getArticles()[j]
                    tempShop = shops[i]
                }
            }
        }

        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        recyclerview.layoutManager = LinearLayoutManager(this)

        val data = ArrayList<ItemsViewModel>()

        data.add(
            ItemsViewModel(
                R.drawable.ic_bananas,
                getString(R.string.nearest_shop, nearestShop.toString())
            )
        )

        data.add(
            ItemsViewModel(
                R.drawable.ic_bananas,
                getString(R.string.cheapest_shop, cheapestShop.toString())
            )
        )

        data.add(
            ItemsViewModel(
                R.drawable.ic_bananas,
                getString(R.string.cheapest_article, cheapestArticle.toString() + "\n" + tempShop.toString())
            )
        )

        val adapter = CustomAdapter(data)

        recyclerview.adapter = adapter
    }
}