package tech.bharatx.tamato

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.preference.PreferenceManager
import tech.bharatx.tamato.data_classes.Constants
import tech.bharatx.tamato.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.headerRoot.logout.visibility = View.GONE
        binding.phoneNumberInput.error = null
        binding.phoneNumberInput.isErrorEnabled = true
        binding.phoneNumberInput.editText?.doOnTextChanged { text, _, _, _ ->
            binding.phoneNumberInput.error = null
            if (text?.length == 10) {
                binding.buttonPhoneNumLogin.performClick()
            }
        }

        binding.buttonPhoneNumLogin.setOnClickListener {
            val phone = binding.phoneNumberInput.editText?.text?.toString() ?: ""
            if (Regex("\\d{10}").matches(phone)) {
                PreferenceManager.getDefaultSharedPreferences(this@LoginActivity)
                    .edit()
                    .putBoolean(Constants.AUTHENTICATED.name, true)
                    .putString(Constants.PHONE_NUMBER.name, "+91${phone}")
                    .apply()
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            } else {
                binding.phoneNumberInput.error = "Please enter your valid phone number"
            }
        }
    }
}