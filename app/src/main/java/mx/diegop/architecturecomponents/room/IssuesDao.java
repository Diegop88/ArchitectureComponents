package mx.diegop.architecturecomponents.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import mx.diegop.architecturecomponents.network.entities.Issue;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface IssuesDao {
    @Insert(onConflict = REPLACE)
    void saveIssues(List<Issue> issues);

    @Query("SELECT * FROM issue")
    LiveData<List<Issue>> getIssues();
}
