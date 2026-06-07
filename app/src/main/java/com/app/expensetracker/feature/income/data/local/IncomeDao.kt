package com.app.expensetracker.feature.income.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.app.expensetracker.feature.income.data.local.entity.IncomeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IncomeDao {

    @Upsert
    suspend fun upsertAll(income: List<IncomeEntity>)

    @Upsert
    suspend fun upsert(income: IncomeEntity)

    @Query("SELECT * FROM income WHERE year = :year AND month = :month ORDER BY dateMillis DESC")
    fun getByMonth(year: Int, month: Int): Flow<List<IncomeEntity>>

    @Query("SELECT * FROM income ORDER BY dateMillis DESC LIMIT :limit")
    fun getRecent(limit: Int = 5): Flow<List<IncomeEntity>>

    @Query("SELECT * FROM income WHERE id = :id")
    fun getById(id: String): Flow<IncomeEntity?>

    @Query("DELETE FROM income WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("SELECT SUM(amount) FROM income WHERE year = :year AND month = :month")
    fun getTotalByMonth(year: Int, month: Int): Flow<Double?>
}
