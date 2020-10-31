package dev.juanfe.instaflix

import androidx.test.core.app.ApplicationProvider
import dev.juanfe.instaflix.di.appModule
import org.junit.Test
import org.junit.experimental.categories.Category
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.category.CheckModuleTest
import org.koin.test.check.checkModules
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
@Category(CheckModuleTest::class)
class ModuleCheckTest : AutoCloseKoinTest() {

    @Test
    fun `checkDefinitionsForModules`()  {
        startKoin{
            androidContext(ApplicationProvider.getApplicationContext())
            modules(appModule)
        }.checkModules()
    }
}