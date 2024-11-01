package si.um.feri.thesmartshopper.shop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import si.um.feri.thesmartshopper.R
import si.um.feri.thesmartshopper.recycler.cheapestArticle
import si.um.feri.thesmartshopper.recycler.cheapestShop

class CheapestShopActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheapest_shop)

        val shopCheapestName : TextView = findViewById(R.id.cheapest_shop_name)
        val shopCheapestAddress : TextView = findViewById(R.id.cheapest_shop_address)
        val shopCheapestDistance : TextView = findViewById(R.id.cheapest_shop_distance)

        shopCheapestName.text = cheapestShop.getName()
        shopCheapestAddress.text = cheapestShop.getAddress()
        shopCheapestDistance.text = cheapestShop.getDistance().toString()
    }
}