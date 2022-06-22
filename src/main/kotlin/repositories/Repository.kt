package repositories

interface Repository<T> {
    fun add(item: T) : T
    fun remove(id: Long)
    fun update(item: T)
    fun get(): List<T>
    fun getById(id: Long): T
}