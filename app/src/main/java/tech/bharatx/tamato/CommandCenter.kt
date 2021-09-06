package tech.bharatx.tamato

import tech.bharatx.tamato.data_classes.Pizza

object CommandCenter {
    var phoneNumber = ""
    val pizzas = arrayListOf(
        Pizza(
            "Margherita",
            arrayListOf("Tomatoes", "Mozzarella Cheese", "Basil"),
            "A perfectly charred sourdough pizza with a crispy & chewy crust, freshly baked on order. Topped with a flavourful 100% Italian tomato & basil sauce.",
            true,
            21000
        ),
        Pizza(
            "Double Cheese Margherita",
            arrayListOf("Tomatoes", "Mozzarella Cheese", "More Mozzarella Cheese", "Basil"),
            "The ever-popular Margherita - loaded with extra cheese... lots of it!",
            true,
            29000
        ),
        Pizza(
            "Cheese & Corn",
            arrayListOf("Mozzarella Cheese", "Corn"),
            "Oozing cheese with corn kernels embedded in it!",
            true,
            29000
        ),
        Pizza(
            "Farm House",
            arrayListOf("Tomatoes", "Mushroom", "Cheese", "Green Capsicum"),
            "A pizza that goes ballistic on veggies! Check out this mouth watering overload of crunchy, crisp capsicum, succulent mushrooms and fresh tomatoes",
            true,
            34000
        ),
        Pizza(
            "Mexican Green Wave",
            arrayListOf("Tomatoes", "Jalapeno", "Onions", "Green Capsicum"),
            "A pizza loaded with crunchy onions, crisp capsicum, juicy tomatoes and jalapeno with a liberal sprinkling of exotic Mexican herbs.",
            true,
            34000
        ),
        Pizza(
            "Exotica",
            arrayListOf("Baby Corn", "Black Olives", "Green Capsicum", "Jalapeno", "Red Capsicum"),
            "A pizza that decidedly staggers under an overload of golden corn, exotic black olives, crunchy onions, crisp capsicum.",
            true,
            39000
        )
    )
    val pizzaOrder = hashMapOf<String, Int>().also { map ->
        pizzas.forEach {
            map[it.name] = 0
        }
    }
    private const val orderIdLength = 10
    private val orderIdAllowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    val orderId = (1..orderIdLength)
        .map { orderIdAllowedChars.random() }
        .joinToString("")
}
