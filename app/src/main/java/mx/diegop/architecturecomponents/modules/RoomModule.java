package mx.diegop.architecturecomponents.modules;

import android.app.Application;
import android.arch.persistence.room.Room;

import dagger.Module;
import dagger.Provides;
import mx.diegop.architecturecomponents.room.IssuesDao;
import mx.diegop.architecturecomponents.room.IssuesDatabase;
import mx.diegop.architecturecomponents.utils.AppExecutors;

@Module
public class RoomModule {

    @Provides
    IssuesDatabase providesIssuesDataBase(Application application) {
        return Room.databaseBuilder(application, IssuesDatabase.class, "issuesdb").build();
    }

    @Provides
    IssuesDao providesIssuesDao(IssuesDatabase database) {
        return database.issuesDao();
    }

    @Provides
    AppExecutors providesExecutor() {
        return new AppExecutors();
    }
}
