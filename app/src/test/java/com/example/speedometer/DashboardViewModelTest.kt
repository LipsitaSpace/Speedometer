package com.example.speedometer

import android.util.Log
import com.example.speedometer.data.FakeTripDataDao
import com.example.speedometer.data.TripData
import com.example.speedometer.data.TripDataRepo
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DashboardViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var dao: FakeTripDataDao
    private lateinit var repo: TripDataRepo
    private lateinit var viewModel: DashboardViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0

        dao = FakeTripDataDao()
        repo = TripDataRepo(dao)
        viewModel = DashboardViewModel(repo)
    }

    @After
    fun tearDown() {
        unmockkStatic(Log::class)
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state values are correct`() {
        assertEquals(0f, viewModel.speed.value)
        assertEquals(0f, viewModel.distance.value)
        assertEquals("", viewModel.time.value)
        assertTrue(viewModel.mode.value)
        assertEquals("km/h", viewModel.unit.value)
        assertEquals(0f, viewModel.totalDistance.value)
        assertEquals(0f, viewModel.totalTime.value)
        assertEquals("", viewModel.startLocation.value)
        assertEquals("", viewModel.destination.value)
    }

    @Test
    fun `pickNewTrip sets non empty start and destination`() {
        viewModel.pickNewTrip()

        val start = viewModel.startLocation.value
        val destination = viewModel.destination.value

        assertTrue(start.isNotEmpty())
        assertTrue(destination.isNotEmpty())
    }

    @Test
    fun `pickNewTrip sets different start and destination`() {
        viewModel.pickNewTrip()

        assertNotEquals(
            viewModel.startLocation.value,
            viewModel.destination.value
        )
    }

    @Test
    fun `saveTripSnapShot saves data into repository`() = runTest {
        val tripData = TripData(
            startLocation = "Delhi",
            startTime = "10:00",
            destination = "Mumbai",
            endTime = "14:00"
        )

        viewModel.saveTripSnapShot(tripData)
        advanceUntilIdle()

        val savedData = dao.insertedData

        assertNotNull(savedData)
        assertEquals("Delhi", savedData?.startLocation)
        assertEquals("10:00", savedData?.startTime)
        assertEquals("Mumbai", savedData?.destination)
        assertEquals("14:00", savedData?.endTime)
    }
}
