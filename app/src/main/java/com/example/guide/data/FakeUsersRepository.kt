package com.example.guide.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeUsersRepository : UsersRepository {
    private val fakeUsers = mutableListOf<User>(
        User(username = "testUser", password = "password123", email = "2rge3"),
        User(username = "exampleUser", password = "123456", email = "fe23")
    )

    override fun getUserIdFromUsername(username: String): Flow<Int?> = flow {
        TODO("Not yet implemented")
    }

    override suspend fun insertUser(user: User) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(user: User) {
        TODO("Not yet implemented")
    }

    override fun getAllUsersStream(): Flow<List<User>> {
        TODO("Not yet implemented")
    }

    override fun getUserStream(userId: Int): Flow<User?> = flow {
        TODO("Not yet implemented")
    }

    override fun getUserByUsernameAndPassword(username: String, password: String): Flow<User?> {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(user: User) {
        TODO("Not yet implemented")
    }
}
