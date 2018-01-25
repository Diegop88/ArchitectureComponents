package mx.diegop.architecturecomponents.model;

import android.arch.lifecycle.LiveData;

import java.util.List;

import mx.diegop.architecturecomponents.network.entities.Issue;
import mx.diegop.architecturecomponents.utils.Resource;

public interface IssueRepository {
    LiveData<Resource<List<Issue>>> getIssues(String owner, String repo);
}
