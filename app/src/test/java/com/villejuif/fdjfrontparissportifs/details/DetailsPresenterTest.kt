package com.villejuif.fdjfrontparissportifs.details

import com.villejuif.fdjfrontparissportifs.network.DataRepository
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class DetailsPresenterTest {

    private lateinit var presenter: DetailsPresenter
    private lateinit var dataRepository: DataRepository

    @Before
    fun setup(){
        dataRepository = mock(DataRepository::class.java)
        presenter = DetailsPresenter(dataRepository)
    }

    @Test
    fun `Get a team`(){
        //TODO I'm sorry not time to go through
    }

}