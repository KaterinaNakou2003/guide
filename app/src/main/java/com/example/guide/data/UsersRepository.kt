package com.example.guide.data

import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete, and retrieve of [User] from a given data source.
 */
interface UsersRepository {
    /**
     * Retrieve all the users from the the given data source.
     */
    fun getAllUsersStream(): Flow<List<User>>

    /**
     * Retrieve an user from the given data source that matches with the [id].
     */
    fun getUserStream(id: Int): Flow<User?>

    /**
     * Check user's credentials
     */
    fun getUserByUsernameAndPassword(username: String, password: String): Flow<User?>

    /**
     * Retrieve an user_id from [username].
     */
    fun getUserIdFromUsername(username: String): Flow<Int?>

    /**
     * Insert user in the data source
     */
    suspend fun insertUser(user: User)

    /**
     * Delete user from the data source
     */
    suspend fun deleteUser(user: User)

    /**
     * Update user in the data source
     */
    suspend fun updateUser(user: User)

}
