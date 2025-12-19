package com.demo.data.database.repository

import com.demo.domain.model.User
import javax.inject.Inject

interface LocalDataSource {
    suspend fun addUserInRoomDatabase(user: User?): User?
    suspend fun getLoggedInUserFromDatabase(): User?
}

class LocalDataSourceImpl @Inject constructor(

) : LocalDataSource {
    override suspend fun addUserInRoomDatabase(user: User?): User? {
        // todo Need to implement
        return null
    }

    override suspend fun getLoggedInUserFromDatabase(): User? {
        // todo Need to implement
        return null
    }

}