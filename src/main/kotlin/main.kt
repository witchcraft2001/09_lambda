import repositories.ChatRepository
import repositories.MessagesRepository
import services.ChatService

fun main() {
    val messageRepo = MessagesRepository()
    val chatRepo = ChatRepository()
    val service = ChatService(messageRepo, chatRepo)

    service.sendMessage(1, 2, "Hello!")

    val msg = service.sendMessage(1, 2, "How areyou")
    service.editMessage(msg.id, "How are you?")

    service.sendMessage(1, 3, "Hi there!")
    val msg1 = service.sendMessage(1, 3, "Where are you?")
    val msg2 = service.sendMessage(1, 3, "Are you at home?")
    service.removeMessage(msg1.id)
    service.removeMessage(msg2.id)

    service.sendMessage(3, 2, "Hey! Have you already done homework?")
    val msg3 = service.sendMessage(3, 2, "Can you help me?")

    println("Все чаты для пользователя 1:\n" + service.getChats(1).joinToString("\n"))
    println("Все чаты для пользователя 2:\n" + service.getChats(2).joinToString("\n"))

    println("Непрочитанных чатов для пользователя 2: ${service.getUnreadChatsCount(2)}")

    println("Читаем чат ${msg3.chatId} пользователя 2" + service.getMessages(2 , msg3.chatId, 5).joinToString("\n"))

    println("Непрочитанных чатов для пользователя 2: ${service.getUnreadChatsCount(2)}")
    service.removeChat(msg3.chatId)
    println("Все чаты для пользователя 2:\n" + service.getChats(2).joinToString("\n"))
}
