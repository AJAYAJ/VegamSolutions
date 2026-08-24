package in.vegamdigital.app.data.local;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.RoomDatabase;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&\u00a8\u0006\u0005"}, d2 = {"Lin/vegamdigital/app/data/local/VegamDatabase;", "Landroidx/room/RoomDatabase;", "()V", "dao", "Lin/vegamdigital/app/data/local/VegamDao;", "app_debug"})
@androidx.room.Database(entities = {in.vegamdigital.app.data.local.SessionEntity.class, in.vegamdigital.app.data.local.DoubtEntity.class, in.vegamdigital.app.data.local.JobEntity.class}, version = 1, exportSchema = false)
public abstract class VegamDatabase extends androidx.room.RoomDatabase {
    
    public VegamDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract in.vegamdigital.app.data.local.VegamDao dao();
}