package com.example.guide.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import androidx.room.Delete
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * from users WHERE user_id = :id")
    fun getUser(id: Int): Flow<User>

    @Query("SELECT user_id from users WHERE username = :username")
    fun getUserIdFromUsername(username: String): Flow<Int>

    @Query("SELECT * from users ORDER BY username ASC")
    fun getAllUsers(): Flow<List<User>>

    @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
    fun getUserByUsernameAndPassword(username: String, password: String): Flow<User?>
}
