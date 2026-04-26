package com.app.expensetracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.app.expensetracker.data.local.dao.UserDao
import com.app.expensetracker.data.local.entity.UserEntity
import com.app.expensetracker.feature.expense.data.local.ExpenseDao
import com.app.expensetracker.feature.expense.data.local.entity.ExpenseEntity

@Database(
    entities = [
        UserEntity::class,
        ExpenseEntity::class      // ← NEW
    ],
    version = 2,                  // ← bumped from 1
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun expenseDao(): ExpenseDao  // ← NEW

    companion object {
        /**
         * Migration from v1 (UserEntity only) → v2 (+ expenses table).
         * Keeps existing user data intact.
         */
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS expenses (
                        id          TEXT    NOT NULL PRIMARY KEY,
                        title       TEXT    NOT NULL,
                        amount      REAL    NOT NULL,
                        category    TEXT    NOT NULL,
                        note        TEXT,
                        paymentMode TEXT    NOT NULL,
                        dateMillis  INTEGER NOT NULL,
                        month       INTEGER NOT NULL,
                        year        INTEGER NOT NULL,
                        syncedAt    INTEGER NOT NULL
                    )
                    """.trimIndent()
                )
            }
        }
    }
}
