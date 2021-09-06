package tech.bharatx.tamato.data_classes

data class Pizza(
    val name: String,
    val ingredients: ArrayList<String>,
    val description: String,
    val veg: Boolean,
    val price: Int
)
