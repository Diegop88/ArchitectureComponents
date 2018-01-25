package mx.diegop.architecturecomponents.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import mx.diegop.architecturecomponents.network.entities.Issue;

@Database(entities = {Issue.class}, version = 1)
public abstract class IssuesDatabase extends RoomDatabase{
    public abstract IssuesDao issuesDao();
}
