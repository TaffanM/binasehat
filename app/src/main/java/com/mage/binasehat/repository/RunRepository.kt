package com.mage.binasehat.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.mage.binasehat.data.local.db.dao.RunDao
import com.mage.binasehat.data.model.Run
import com.mage.binasehat.data.remote.api.UserApiService
import com.mage.binasehat.data.remote.model.RunSubmissionRequest
import com.mage.binasehat.data.remote.response.RunningResponse
import com.mage.binasehat.data.util.RunSortOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RunRepository @Inject constructor(
    private val runDao: RunDao,
    private val userApiService: UserApiService,
    private val userRepository: UserRepository
) {
    suspend fun insertRun(run: Run) = runDao.insertRun(run)

    suspend fun deleteRun(run: Run) = runDao.deleteRun(run)

    fun getSortedAllRun(sortingOrder: RunSortOrder) = Pager(
        config = PagingConfig(pageSize = 20),
    ) {
        when (sortingOrder) {
            RunSortOrder.DATE -> runDao.getAllRunSortByDate()
            RunSortOrder.DURATION -> runDao.getAllRunSortByDuration()
            RunSortOrder.CALORIES_BURNED -> runDao.getAllRunSortByCaloriesBurned()
            RunSortOrder.AVG_SPEED -> runDao.getAllRunSortByAvgSpeed()
            RunSortOrder.DISTANCE -> runDao.getAllRunSortByDistance()
        }
    }

    suspend fun getRunStatsInDateRange(fromDate: Date?, toDate: Date?) =
        runDao.getRunStatsInDateRange(fromDate, toDate)

    fun getRunByDescDateWithLimit(limit: Int): Flow<List<Run>> = runDao.getRunByDescDateWithLimit(limit)


    fun getTotalRunningDuration(fromDate: Date? = null, toDate: Date? = null): Flow<Long> =
        runDao.getTotalRunningDuration(fromDate, toDate)

    fun getTotalCaloriesBurned(fromDate: Date? = null, toDate: Date? = null): Flow<Long> =
        runDao.getTotalCaloriesBurned(fromDate, toDate)

    fun getTotalDistance(fromDate: Date? = null, toDate: Date? = null): Flow<Long> =
        runDao.getTotalDistance(fromDate, toDate)

    fun getTotalAvgSpeed(fromDate: Date? = null, toDate: Date? = null): Flow<Float> =
        runDao.getTotalAvgSpeed(fromDate, toDate)

    suspend fun submitRun(run: RunSubmissionRequest): RunningResponse? {
        val token = userRepository.getUserToken()
        return token?.let {
            val tokenWithBearer = "Bearer $it"
            userApiService.submitRun(tokenWithBearer, run)
        }
    }


}