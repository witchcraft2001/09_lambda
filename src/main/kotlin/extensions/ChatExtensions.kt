package extensions

import repositories.ChatRepository

fun ChatRepository.getChatsForUserId(userId: Long) =
    get().filter { it.to == userId || it.from == userId }

fun ChatRepository.getChatForUsers(first: Long, second: Long) =
    get().find { it.to == first && it.from == second || it.to == second && it.from == first }