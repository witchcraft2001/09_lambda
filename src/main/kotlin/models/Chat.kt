package models

data class Chat(
    val id: Long = 0,
    val from: Long,
    val to: Long,
    val isDeleted: Boolean = false
)
