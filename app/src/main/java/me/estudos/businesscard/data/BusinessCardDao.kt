package me.estudos.businesscard.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BusinessCardDao {

    @Query("SELECT * from BusinessCard")
    fun getAll(): LiveData<List<BusinessCard>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(businessCard: BusinessCard)

    @Delete
    suspend fun delete(businessCard: BusinessCard)
}