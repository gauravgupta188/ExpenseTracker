package com.app.expensetracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.expensetracker.data.local.dao.UserDao
import com.app.expensetracker.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}