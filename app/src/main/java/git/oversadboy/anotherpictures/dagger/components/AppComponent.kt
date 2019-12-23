package git.oversadboy.anotherpictures.dagger.components

import dagger.Component
import git.oversadboy.anotherpictures.dagger.ViewModelFactory
import git.oversadboy.anotherpictures.dagger.modules.AppModule
import git.oversadboy.anotherpictures.dagger.modules.ViewModelModule
import git.oversadboy.anotherpictures.ui.base.BaseActivity
import git.oversadboy.anotherpictures.ui.base.BaseFragment
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    fun inject(baseActivity: BaseActivity)
    fun inject(baseFragment: BaseFragment)
    fun inject(viewModelFactory: ViewModelFactory)

}