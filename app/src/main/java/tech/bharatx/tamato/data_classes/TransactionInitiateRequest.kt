package tech.bharatx.tamato.data_classes

import kotlinx.serialization.Serializable

@Serializable
data class TransactionInitiateRequest(
    val merchantTransactionId: String,
    val userPhoneNumber: String,
    val userId: String,
    val amount: Int
)
