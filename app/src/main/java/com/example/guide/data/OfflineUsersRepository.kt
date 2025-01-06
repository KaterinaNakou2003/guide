package com.example.guide.data

import kotlinx.coroutines.flow.Flow

class OfflineUsersRepository(private val userDao: UserDao) : UsersRepository {
    override fun getAllUsersStream(): Flow<List<User>> = userDao.getAllUsers()

    override fun getUserStream(id: Int): Flow<User?> = userDao.getUser(id)

    override fun getUserIdFromUsername(username: String): Flow<Int?> = userDao.getUserIdFromUsername(username)

    override fun getUserByUsernameAndPassword(username: String, password: String): Flow<User?> = userDao.getUserByUsernameAndPassword(username, password)

    override suspend fun insertUser(user: User) = userDao.insert(user)

    override suspend fun deleteUser(user: User) = userDao.delete(user)

    override suspend fun updateUser(user: User) = userDao.update(user)
}
