package si.um.feri.thesmartshopper.shop.article

import java.util.*

class Article (private var name: String, private var price: Float, private var discount: Float){
    val id = UUID.randomUUID().toString().replace("-", "")

    override fun toString(): String {
        var p = price * (1 - discount)
        return "$name ($$p)"
    }

    fun getName(): String {
        return name
    }

    fun getPrice():  Float {
        return price * (1 - discount)

    }fun getDiscount(): Float {
        return discount
    }

}