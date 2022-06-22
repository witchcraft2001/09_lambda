import exceptions.RepositoryException
import models.Chat
import org.junit.Test
import repositories.ChatRepository

class ChatRepositoryTest {
    @Test
    fun addChatCreatesRecord() {
        //Arrange
        val repository = ChatRepository()
        //Act
        repository.add(Chat(from = 1, to = 2))
        //Assert
        assert(repository.get().any())
    }

    @Test(expected = RepositoryException::class)
    fun addChatWithExistsContactsShouldThrow() {
        //Arrange
        val repository = ChatRepository()
        //Act
        repository.add(Chat(from = 1, to = 2))
        repository.add(Chat(from = 2, to = 1))
        //Assert
    }

    @Test(expected = NoSuchElementException::class)
    fun removeChatShouldMarkRecordAsDeleted() {
        //Arrange
        val repository = ChatRepository()
        //Act
        val chat = repository.add(Chat(from = 1, to = 2))
        repository.remove(chat.id)
        val chat1 = repository.getById(chat.id)
        //Assert
    }
}