package com.example.guide.data

import androidx.room.Database
import android.content.Context
import androidx.room.RoomDatabase
import androidx.room.Room

/**
 * Database class with a singleton Instance object.
 */
@Database(entities = [User::class, Favorite::class], version = 1, exportSchema = false)
abstract class GuideDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun favoriteDao(): FavoriteDao

    companion object {

        @Volatile
        private var Instance: GuideDatabase? = null

        fun getDatabase(context: Context): GuideDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, GuideDatabase::class.java, "guide_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }


}
