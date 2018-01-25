package mx.diegop.architecturecomponents.network;

import java.util.List;

import mx.diegop.architecturecomponents.network.entities.Issue;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubApiService {
    @GET("repos/{owner}/{repo}/issues")
    Call<List<Issue>> getIssues(@Path("owner") String owner, @Path("repo") String repo);
}
