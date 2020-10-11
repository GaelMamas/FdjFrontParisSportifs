package com.villejuif.fdjfrontparissportifs.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.villejuif.fdjfrontparissportifs.MainCoroutineRule
import com.villejuif.fdjfrontparissportifs.data.FakeDataRepository
import com.villejuif.fdjfrontparissportifs.data.FakeTheDBSportsApo
import com.villejuif.fdjfrontparissportifs.getOrWaitValue
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeoutException

class DetailsPresenterTest {

    private lateinit var presenter: DetailsPresenter
    private lateinit var errorPresenter: DetailsPresenter
    private lateinit var dataRepository: FakeDataRepository

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        dataRepository = FakeDataRepository(FakeTheDBSportsApo(false))
        presenter = DetailsPresenter(dataRepository)

        errorPresenter =
            DetailsPresenter(
                FakeDataRepository(
                    FakeTheDBSportsApo(true)
                )
            )
    }

    @Test
    fun `Get a team`() {

        val idTeam = "ideateam"
        val strTeam = "team"

        presenter.searchTeam(strTeam, idTeam)

        val team = presenter.team.getOrWaitValue()

        assertEquals(strTeam, team?.strTeam)
        assertEquals(idTeam, team?.idTeam)

    }

    @Test(expected = TimeoutException::class)
    fun `Get a team failed`() {
        errorPresenter.searchTeam("team", "ideateam")

        errorPresenter.team.getOrWaitValue()

    }

}