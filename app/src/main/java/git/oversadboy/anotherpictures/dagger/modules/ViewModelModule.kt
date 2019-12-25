package git.oversadboy.anotherpictures.dagger.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import git.oversadboy.anotherpictures.dagger.ViewModelKey
import git.oversadboy.anotherpictures.ui.collections.CollectionsViewModel
import git.oversadboy.anotherpictures.ui.images.ImagesViewModel

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ImagesViewModel::class)
    internal abstract fun imagesViewModel(viewModel: ImagesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CollectionsViewModel::class)
    internal abstract fun collectionViewModel(viewModel: CollectionsViewModel): ViewModel

}