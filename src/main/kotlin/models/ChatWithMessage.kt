package models

data class ChatWithMessage(
    val id: Long,
    val from: Long,
    val to: Long,
    val message: String,
    val datetime: Long?
)
