package tech.bharatx.tamato

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import tech.bharatx.tamato.databinding.ActivityPaymentCompleteBinding

class PaymentCompleteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPaymentCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CommandCenter.pizzas.forEach {
            CommandCenter.pizzaOrder[it.name] = 0
        }

        binding.headerRoot.phoneNumber.text = CommandCenter.phoneNumber
        binding.headerRoot.close.visibility = View.VISIBLE
        binding.headerRoot.close.setOnClickListener {
            finish()
        }
        binding.orderCompleteMessage.text = getString(R.string.order_complete_message).format(
            CommandCenter.orderId, CommandCenter.phoneNumber
        )
    }
}