package repositories

import exceptions.RepositoryException
import models.Chat

class ChatRepository : Repository<Chat> {
    private val list: MutableList<Chat> = emptyList<Chat>().toMutableList()
    private var lastId = 0L

    override fun add(item: Chat): Chat {
        if (list.any { chat ->
                (chat.to == item.to && chat.from == item.from ||
                        chat.to == item.from && chat.from == item.to) &&
                        !chat.isDeleted
            }
        ) {
            throw RepositoryException("Чат с указанными контактами уже существует")
        }
        val newItem = item.copy(id = ++lastId, isDeleted = false)
        list.add(newItem)
        return newItem
    }

    override fun remove(id: Long) {
        val item = list.first { it.id == id }
        update(item.copy(isDeleted = true))
    }

    override fun update(item: Chat) {
        val msg = list.first { i -> i.id == item.id }
        val index = list.indexOf(msg)
        list.remove(msg)
        list.add(index, item)
    }

    override fun get(): List<Chat> = list.filter { it.isDeleted.not() }

    override fun getById(id: Long) = list.first { it.id == id && it.isDeleted.not() }
}