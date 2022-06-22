package extensions

import repositories.MessagesRepository

fun MessagesRepository.getUnreadMessagesByChatId(chatId: Long) =
    get().filter { msg -> msg.chatId == chatId && msg.isUnread }

fun MessagesRepository.getMessagesByChatId(chatId: Long) =
    get().filter { msg -> msg.chatId == chatId }

fun MessagesRepository.hasUnreadMessageOfChat(chatId: Long) =
    getUnreadMessagesByChatId(chatId).any()

fun MessagesRepository.getLastMessageByChatId(chatId: Long) =
    get().sortedByDescending { it.id }.firstOrNull { it.chatId == chatId }

