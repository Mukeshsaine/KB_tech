package com.example.kbtech.Roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AnswerDao {
    @Insert
    suspend fun insertAnswer(answer: User)

    @Query("SELECT * FROM answers")
    suspend fun getAllAnswers(): List<User>

    @Delete
    suspend fun deleteAnswer(answer: User)

    @Query("DELETE FROM answers")
    suspend fun deleteAllAnswers()
}
