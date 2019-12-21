package git.oversadboy.anotherpictures.dagger.modules

import androidx.lifecycle.ViewModel
import git.oversadboy.anotherpictures.dagger.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import git.oversadboy.anotherpictures.ui.fragment.CollectionsViewModel
import git.oversadboy.anotherpictures.ui.fragment.ImagesViewModel

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ImagesViewModel::class)
    internal abstract fun imagesViewModel(viewModel: ImagesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CollectionsViewModel::class)
    internal abstract fun browserViewModel(viewModel: CollectionsViewModel): ViewModel

}