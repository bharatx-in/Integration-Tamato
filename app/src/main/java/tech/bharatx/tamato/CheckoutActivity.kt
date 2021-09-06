package tech.bharatx.tamato

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import tech.bharatx.tamato.databinding.ActivityCheckoutBinding


class CheckoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.headerRoot.phoneNumber.text = CommandCenter.phoneNumber
        binding.headerRoot.close.visibility = View.VISIBLE
        binding.headerRoot.close.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }

        binding.orders.text = CommandCenter.pizzaOrder.entries.filter { it.value > 0 }
            .joinToString(separator = "\n\n") { entry ->
                "x${entry.value} of ${entry.key} for ${
                    getString(R.string.price_format)
                        .format(entry.value * (CommandCenter.pizzas.firstOrNull { it.name == entry.key }?.price ?: 0) / 100f)
                }"
            }

        val totalPrice = CommandCenter.pizzaOrder.entries.filter { it.value > 0 }.map { entry ->
            entry.value * (CommandCenter.pizzas.firstOrNull { it.name == entry.key }?.price ?: 0)
        }.sum()
        binding.total.text = getString(R.string.price_format).format(totalPrice / 100f)

        binding.payNow.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Payment Option")
                .setItems(
                    arrayOf("Cash on Delivery")
                ) { _, which ->
                    when (which) {
                        0 -> {
                            startActivity(
                                Intent(
                                    this@CheckoutActivity,
                                    PaymentCompleteActivity::class.java
                                )
                            )
                            setResult(RESULT_OK)
                            finish()
                        }
                    }
                }
                .show()
        }
    }
}