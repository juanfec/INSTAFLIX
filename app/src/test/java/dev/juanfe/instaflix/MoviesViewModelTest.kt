package dev.juanfe.instaflix

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import dev.juanfe.instaflix.data.models.MovieGeneral
import dev.juanfe.instaflix.data.remote.ApiCalls
import dev.juanfe.instaflix.ui.home.movies.MainActivityViewModel
import dev.juanfe.instaflix.ui.home.movies.MoviePagedListRepository
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import org.junit.After

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


@RunWith(JUnit4::class)
class MoviesViewModelTest {
    @Rule @JvmField
    var instantExecutorRule = InstantTaskExecutorRule()
    @Mock
    var repository: MoviePagedListRepository? = null
    private var viewModel: MainActivityViewModel? = null
    @Mock
    var observer: Observer<PagedList<MovieGeneral>>? = null


    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = MainActivityViewModel(repository!!)
        observer?.let { viewModel!!.movieGeneralPagedList.observeForever(it) }
    }

    @Test
    fun testNull() {
        `when`(repository?.movieGeneralPagedList).thenReturn(null)
        assertNotNull(viewModel?.movieGeneralPagedList)
        viewModel?.movieGeneralPagedList?.hasObservers()?.let { assertTrue(it) }
    }
    /*
    @Test
    fun testApiFetchDataSuccess() {
        // Mock API response
        `when`(apiClient.fetchNews()).thenReturn(Single.just(NewsList()))
        viewModel.fetchNews()
        verify(observer).onChanged(NewsListViewState.LOADING_STATE)
        verify(observer).onChanged(NewsListViewState.SUCCESS_STATE)
    }

    @Test
    fun testApiFetchDataError() {
        `when`(apiClient.fetchNews())
            .thenReturn(Single.error(Throwable("Api error")))
        viewModel.fetchNews()
        verify(observer).onChanged(NewsListViewState.LOADING_STATE)
        verify(observer).onChanged(NewsListViewState.ERROR_STATE)
    }

     */

    @After
    @Throws(Exception::class)
    fun tearDown() {
        repository = null
        viewModel = null
    }
}