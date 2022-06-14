package models

data class Message(
    val id: Long = 0,
    val authorId: Long,
    val chatId: Long,
    val datetime: Long,
    val message: String,
    val isUnread: Boolean = true,
    val isDeleted: Boolean = false
)