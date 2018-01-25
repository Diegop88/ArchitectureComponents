package mx.diegop.architecturecomponents.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import javax.inject.Inject;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private ListIssuesViewModel viewModel;

    @Inject
    public ViewModelFactory(ListIssuesViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(ListIssuesViewModel.class)) {
            return (T) viewModel;
        }
        throw new IllegalArgumentException("Unknow class name");
    }
}
