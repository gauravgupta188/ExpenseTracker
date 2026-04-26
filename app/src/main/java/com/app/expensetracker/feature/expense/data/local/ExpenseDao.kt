package com.app.expensetracker.feature.expense.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.app.expensetracker.feature.expense.data.local.entity.ExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    /**
     * Insert or update a list of expenses from Firestore.
     * UPSERT = INSERT OR REPLACE — safe to call on every Firestore snapshot.
     */
    @Upsert
    suspend fun upsertAll(expenses: List<ExpenseEntity>)

    /**
     * Insert or update a single expense (used after add/update operations).
     */
    @Upsert
    suspend fun upsert(expense: ExpenseEntity)

    /**
     * Primary read — returns a live Flow of expenses for a given month.
     * Room emits a new list every time the underlying table changes.
     */
    @Query("SELECT * FROM expenses WHERE year = :year AND month = :month ORDER BY dateMillis DESC")
    fun getByMonth(year: Int, month: Int): Flow<List<ExpenseEntity>>

    /**
     * Returns the N most recent expenses across all months.
     * Used on the Dashboard "Recent" section.
     */
    @Query("SELECT * FROM expenses ORDER BY dateMillis DESC LIMIT :limit")
    fun getRecent(limit: Int = 5): Flow<List<ExpenseEntity>>

    /**
     * Fetch a single expense by its Firestore document ID.
     */
    @Query("SELECT * FROM expenses WHERE id = :id")
    fun getById(id: String): Flow<ExpenseEntity?>

    /**
     * Hard delete — called after a Firestore delete succeeds.
     */
    @Query("DELETE FROM expenses WHERE id = :id")
    suspend fun deleteById(id: String)

    /**
     * Replace all cached expenses for a month.
     * Called when a Firestore snapshot arrives for that month.
     */
    @Query("DELETE FROM expenses WHERE year = :year AND month = :month")
    suspend fun deleteByMonth(year: Int, month: Int)
}
