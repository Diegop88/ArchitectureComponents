package mx.diegop.architecturecomponents.modules;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import dagger.Module;
import dagger.Provides;
import mx.diegop.architecturecomponents.model.IssueRepository;
import mx.diegop.architecturecomponents.model.IssueRepositoryImp;
import mx.diegop.architecturecomponents.network.GithubApiService;
import mx.diegop.architecturecomponents.viewmodel.ListIssuesViewModel;
import mx.diegop.architecturecomponents.viewmodel.ViewModelFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = RoomModule.class)
public class AppModule {
    public static final String BASE_URL = "https://api.github.com/";

    @Provides
    GithubApiService providesApiService() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor).build();

        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(client)
                .build()
                .create(GithubApiService.class);
    }

    @Provides
    IssueRepository provideApiIssueRepository(IssueRepositoryImp repository) {
        return repository;
    }

    @Provides
    ViewModel provideIssuesViewModel(ListIssuesViewModel viewModel) {
        return viewModel;
    }

    @Provides
    ViewModelProvider.Factory provideListIssuesViewModelFactory(ViewModelFactory factory) {
        return factory;
    }
}
