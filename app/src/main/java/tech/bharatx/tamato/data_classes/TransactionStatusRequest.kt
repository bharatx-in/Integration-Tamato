package tech.bharatx.tamato.data_classes

import kotlinx.serialization.Serializable

@Serializable
data class TransactionStatusRequest(
    val merchantTransactionId: String
)
