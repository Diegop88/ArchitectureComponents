package mx.diegop.architecturecomponents.viewmodel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import mx.diegop.architecturecomponents.model.IssueRepository;
import mx.diegop.architecturecomponents.network.entities.Issue;
import mx.diegop.architecturecomponents.utils.AbsentLiveData;
import mx.diegop.architecturecomponents.utils.Resource;

public class ListIssuesViewModel extends ViewModel {

    private final MediatorLiveData<String> request = new MediatorLiveData<>();

    private final LiveData<Resource<List<Issue>>> issues;

    @Inject
    public ListIssuesViewModel(final IssueRepository issueRepository) {
        issues = Transformations.switchMap(request, new Function<String, LiveData<Resource<List<Issue>>>>() {
            @Override
            public LiveData<Resource<List<Issue>>> apply(String repo) {
                if (repo == null || repo.trim().length() == 0) {
                    return AbsentLiveData.create();
                } else {
                    return issueRepository.getIssues("google", repo);
                }
            }
        });
    }

    public void loadIssues(final String repo) {
        request.setValue(repo);
    }

    @NonNull
    public LiveData<Resource<List<Issue>>> getResponse() {
        return issues;
    }
}
