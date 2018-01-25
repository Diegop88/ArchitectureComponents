package mx.diegop.architecturecomponents;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import mx.diegop.architecturecomponents.adapters.IssuesAdapter;
import mx.diegop.architecturecomponents.network.entities.Issue;
import mx.diegop.architecturecomponents.utils.Resource;
import mx.diegop.architecturecomponents.viewmodel.ListIssuesViewModel;

import static mx.diegop.architecturecomponents.utils.Status.ERROR;
import static mx.diegop.architecturecomponents.utils.Status.LOADING;
import static mx.diegop.architecturecomponents.utils.Status.SUCCESS;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private ListIssuesViewModel listIssuesViewModel;

    private IssuesAdapter adapter;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refreshLayout = findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(this);
        RecyclerView issues = findViewById(R.id.issues_list);
        issues.setLayoutManager(new LinearLayoutManager(this));
        adapter = new IssuesAdapter();
        issues.setAdapter(adapter);
        issues.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        listIssuesViewModel = ViewModelProviders.of(this, viewModelFactory).get(ListIssuesViewModel.class);
        addDisposable();
    }

    @Override
    protected void onResume() {
        super.onResume();
        listIssuesViewModel.loadIssues("dagger");
    }

    @Override
    public void onRefresh() {
        listIssuesViewModel.loadIssues("glide");
    }

    private void showIssues(List<Issue> issues) {
        adapter.setIssues(issues);
    }

    private void showLoading() {
        refreshLayout.setRefreshing(true);
    }

    private void hideLoading() {
        refreshLayout.setRefreshing(false);
    }

    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void addDisposable() {
        listIssuesViewModel.getResponse().observe(this, new Observer<Resource<List<Issue>>>() {
            @Override
            public void onChanged(Resource<List<Issue>> listResource) {
                if (SUCCESS.equals(listResource.status)) {
                    hideLoading();
                    showIssues(listResource.data);
                } else if (ERROR.equals(listResource.status)) {
                    hideLoading();
                    onError(listResource.message);
                } else if (LOADING.equals(listResource.status)) {
                    showLoading();
                    if (listResource.data != null)
                        showIssues(listResource.data);
                }
            }
        });
    }
}
