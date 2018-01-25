package mx.diegop.architecturecomponents.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import mx.diegop.architecturecomponents.network.GithubApiService;
import mx.diegop.architecturecomponents.network.entities.Issue;
import mx.diegop.architecturecomponents.room.IssuesDao;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import mx.diegop.architecturecomponents.utils.AppExecutors;
import mx.diegop.architecturecomponents.utils.NetworkBoundResource;
import mx.diegop.architecturecomponents.utils.Resource;

public class IssueRepositoryImp implements IssueRepository {
    private static final String TAG = IssueRepositoryImp.class.getSimpleName();

    private final GithubApiService service;
    private final IssuesDao issuesDao;
    private final AppExecutors executors;

    @Inject
    public IssueRepositoryImp(GithubApiService service, IssuesDao issuesDao, AppExecutors executors) {
        this.service = service;
        this.issuesDao = issuesDao;
        this.executors = executors;
    }

    @Override
    public LiveData<Resource<List<Issue>>> getIssues(final String owner, final String repo) {
        return new NetworkBoundResource<List<Issue>, List<Issue>>(executors) {

            @Override
            protected void saveCallResult(@NonNull List<Issue> item) {
                issuesDao.saveIssues(item);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Issue> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<Issue>> loadFromDb() {
                return issuesDao.getIssues();
            }

            @NonNull
            @Override
            protected MutableLiveData<Resource<List<Issue>>> createCall() {
                final MutableLiveData<Resource<List<Issue>>> mutableLiveData = new MutableLiveData<>();
                service.getIssues(owner, repo).enqueue(new Callback<List<Issue>>() {
                    @Override
                    public void onResponse(Call<List<Issue>> call, Response<List<Issue>> response) {
                        if (response.isSuccessful()) {
                            mutableLiveData.setValue(Resource.success(response.body()));
                        } else {
                            mutableLiveData.setValue(Resource.<List<Issue>>error(response.message(), null));
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Issue>> call, Throwable t) {
                        mutableLiveData.setValue(Resource.<List<Issue>>error(t.getMessage(), null));
                    }
                });
                return mutableLiveData;
            }

            @Override
            protected void onFetchFailed() {
                super.onFetchFailed();
                Log.d(TAG, "Fuck");
            }
        }.asLiveData();
    }
}
