package repositories

import models.Message

class MessagesRepository : Repository<Message> {
    private val list: MutableList<Message> = emptyList<Message>().toMutableList()
    private var lastId = 0L

    override fun add(item: Message): Message {
        val newItem = item.copy(id = ++lastId, isDeleted = false)
        list.add(newItem)
        return newItem
    }

    override fun remove(id: Long) {
        val item = list.first { it.id == id }
        update(item.copy(isDeleted = true))
    }

    override fun update(item: Message) {
        val msg = list.first { i -> i.id == item.id }
        val index = list.indexOf(msg)
        list.remove(msg)
        list.add(index, item)
    }

    override fun get(): List<Message> = list.filter { it.isDeleted.not() }

    override fun getById(id: Long) = list.first { it.id == id && it.isDeleted.not() }
}