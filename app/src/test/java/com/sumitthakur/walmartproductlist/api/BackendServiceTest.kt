package com.sumitthakur.walmartproductlist.api

import com.sumitthakur.walmartproductlist.api.modle.ProductsResponse
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.observers.TestObserver
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito


class BackendServiceTest {
    @Mock
    lateinit var serviceInterface: ServiceInterface
    lateinit var mockedBackendService: BackendService

    @Before
    fun setUp() {
        mockedBackendService = BackendService()//Mockito.mock(BackendService::class.java)
        mockedBackendService.initService()
        serviceInterface = Mockito.mock(ServiceInterface::class.java)

        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun testSuccessResponse() {
        mockedBackendService.initService()
        val observable = mockedBackendService.getProducts(1, 1)
        val testObserver = TestObserver<ProductsResponse>()
        observable.subscribe(testObserver)
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)

        val listResult = testObserver.values()[0]

        assertThat(listResult.products.size, `is`(1))
        assertThat(listResult.products[0].productName, `is`("Ellerton TV Console"))
    }


    @Test
    fun testFailureResponse() {
        val observable = mockedBackendService.getProducts(1, 41)
        val testObserver = TestObserver<ProductsResponse>()
        observable.subscribe(testObserver)
        testObserver.assertNotComplete()
        testObserver.assertError(Throwable::class.java)
        testObserver.assertValueCount(0)
    }
}