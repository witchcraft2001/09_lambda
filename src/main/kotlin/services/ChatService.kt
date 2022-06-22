package services

import extensions.*
import models.Chat
import models.ChatWithMessage
import models.Message
import repositories.ChatRepository
import repositories.MessagesRepository

class ChatService(
    private val messagesRepo: MessagesRepository,
    private val chatRepo: ChatRepository
) {
    fun sendMessage(from: Long, to: Long, message: String): Message {
        val chat = chatRepo.getChatForUsers(from, to) ?: addChat(from, to)
        return messagesRepo.add(
            Message(
                authorId = from,
                chatId = chat.id,
                datetime = System.currentTimeMillis(),
                message = message
            )
        )
    }

    fun getChats(userid: Long): List<ChatWithMessage> {
        return chatRepo.getChatsForUserId(userid)
            .map { chat ->
                val lastMessage = messagesRepo.getLastMessageByChatId(chat.id)
                ChatWithMessage(
                    id = chat.id,
                    from = chat.from,
                    to = chat.to,
                    message = lastMessage?.message ?: "нет сообщений",
                    datetime = lastMessage?.datetime
                )
            }
    }

    fun getMessages(userId: Long, chatId: Long, count: Int): List<Message> {
        val messages = messagesRepo.getMessagesByChatId(chatId)
        return messages.take(count)
            .map { msg ->
                if (msg.authorId != userId) messagesRepo.update(msg.copy(isUnread = false))
                msg
            }
    }

    fun getMessages(userId: Long, chatId: Long, msgId: Long, count: Int): List<Message> {
        val messages = messagesRepo.getMessagesByChatId(chatId)
        return messages.dropLastWhile { msg -> msg.id != msgId }.take(count)
            .map { msg ->
                if (msg.authorId != userId) messagesRepo.update(msg.copy(isUnread = false))
                msg
            }
    }

    fun getUnreadChatsCount(userId: Long): Int =
        chatRepo.get().filter { chat -> chat.to == userId && !chat.isDeleted }
            .map { chat -> messagesRepo.hasUnreadMessageOfChat(chatId = chat.id) }.count { it }

    fun removeMessage(id: Long) {
        val message = messagesRepo.getById(id)
        val chat = chatRepo.getById(message.chatId)
        if (message.isDeleted) {
            return
        }
        messagesRepo.remove(id)
        if (!messagesRepo.getMessagesByChatId(chat.id).any()) {
            chatRepo.remove(chat.id)
        }
    }

    fun removeChat(id: Long) {
        messagesRepo.getMessagesByChatId(id)
            .forEach { message -> removeMessage(message.id) }
    }

    fun editMessage(id: Long, message: String) {
        val msg = messagesRepo.getById(id)
        messagesRepo.update(msg.copy(message = message))
    }

    private fun addChat(from: Long, to: Long) = chatRepo.add(Chat(from = from, to = to))
}
