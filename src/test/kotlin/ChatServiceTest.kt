import org.junit.Test
import repositories.ChatRepository
import repositories.MessagesRepository
import services.ChatService

class ChatServiceTest {
    @Test
    fun sendMessageShouldCreateChat() {
        //Arrange
        val chatRepository = ChatRepository()
        val service = ChatService(MessagesRepository(), chatRepository)
        //Act
        service.sendMessage(1, 2, "Test")
        //Assert
        assert(chatRepository.get().any())
    }

    @Test
    fun sendMessageShouldAddMessageToRepository() {
        //Arrange
        val chatRepository = ChatRepository()
        val messagesRepository = MessagesRepository()
        val service = ChatService(messagesRepository, chatRepository)
        //Act
        service.sendMessage(1, 2, "Test")
        //Assert
        assert(messagesRepository.get().any())
    }

    @Test(expected = NoSuchElementException::class)
    fun editDeletedMessageShouldThrow() {
        //Arrange
        val service = ChatService(MessagesRepository(), ChatRepository())
        //Act
        val msg = service.sendMessage(1, 2, "Test")
        service.removeMessage(msg.id)
        service.editMessage(msg.id, "")
        //Assert
    }

    @Test
    fun removeLastMessageShouldRemoveChat() {
        //Arrange
        val chatRepository = ChatRepository()
        val messagesRepository = MessagesRepository()
        val service = ChatService(messagesRepository, chatRepository)
        //Act
        val msg = service.sendMessage(1, 2, "Test")
        service.removeMessage(msg.id)
        //Assert
        assert(!chatRepository.get().any())
    }

    @Test
    fun removeChatShouldRemoveAllMessages() {
        //Arrange
        val chatRepository = ChatRepository()
        val messagesRepository = MessagesRepository()
        val service = ChatService(messagesRepository, chatRepository)
        //Act
        val msg = service.sendMessage(1, 2, "Test")
        service.removeChat(msg.chatId)
        //Assert
        assert(!messagesRepository.get().any())
    }


}