package tech.bharatx.tamato

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.preference.PreferenceManager
import tech.bharatx.tamato.data_classes.Constants
import tech.bharatx.tamato.databinding.ActivityMainBinding
import tech.bharatx.tamato.databinding.ItemPizzaBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean(Constants.AUTHENTICATED.name, false)
        ) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        CommandCenter.phoneNumber = PreferenceManager.getDefaultSharedPreferences(this)
            .getString(Constants.PHONE_NUMBER.name, "") ?: ""
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.headerRoot.phoneNumber.text = CommandCenter.phoneNumber
        binding.headerRoot.logout.setOnClickListener {
            PreferenceManager.getDefaultSharedPreferences(this)
                .edit()
                .putBoolean(Constants.AUTHENTICATED.name, false)
                .putString(Constants.PHONE_NUMBER.name, "")
                .apply()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        val initialCount = CommandCenter.pizzaOrder.count { it.value > 0 }
        binding.checkoutButton.visibility = if (initialCount > 0) View.VISIBLE else View.GONE
        CommandCenter.pizzas.forEach {
            val itemPizzaBinding = ItemPizzaBinding.inflate(layoutInflater, binding.pizzas, true)
            itemPizzaBinding.pizzaName.text = it.name
            itemPizzaBinding.price.text = getString(R.string.price_format).format(it.price / 100f)
            itemPizzaBinding.pizzaDescription.text = it.description
            itemPizzaBinding.pizzaIngredients.text = HtmlCompat.fromHtml(
                "<b>Ingredients:</b> " + it.ingredients.joinToString(),
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
            itemPizzaBinding.count.text = "${CommandCenter.pizzaOrder[it.name] ?: 0}"
            itemPizzaBinding.minus.isEnabled = (CommandCenter.pizzaOrder[it.name] ?: 0) > 0
            itemPizzaBinding.plus.isEnabled = (CommandCenter.pizzaOrder[it.name] ?: 0) < 3
            itemPizzaBinding.minus.setOnClickListener { _ ->
                itemPizzaBinding.minus.isEnabled = true
                itemPizzaBinding.plus.isEnabled = true
                ((CommandCenter.pizzaOrder[it.name] ?: 0) - 1).coerceAtLeast(0).coerceAtMost(3)
                    .also { pizzaCount ->
                        CommandCenter.pizzaOrder[it.name] = pizzaCount
                        itemPizzaBinding.count.text = "$pizzaCount"
                        if (pizzaCount == 0) {
                            itemPizzaBinding.minus.isEnabled = false
                            binding.checkoutButton.visibility = View.GONE
                        } else {
                            binding.checkoutButton.visibility = View.VISIBLE
                        }
                    }
            }
            itemPizzaBinding.plus.setOnClickListener { _ ->
                itemPizzaBinding.minus.isEnabled = true
                itemPizzaBinding.plus.isEnabled = true
                binding.checkoutButton.visibility = View.VISIBLE
                ((CommandCenter.pizzaOrder[it.name] ?: 0) + 1).coerceAtLeast(0).coerceAtMost(3)
                    .also { pizzaCount ->
                        CommandCenter.pizzaOrder[it.name] = pizzaCount
                        itemPizzaBinding.count.text = "$pizzaCount"
                        if (pizzaCount == 3) {
                            itemPizzaBinding.plus.isEnabled = false
                        }
                    }
            }
        }

        binding.checkoutButton.setOnClickListener {
            startActivityForResult(Intent(this, CheckoutActivity::class.java), 0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()

    }
}