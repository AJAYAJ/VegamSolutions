package `in`.vegamdigital.app.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "session")
data class SessionEntity(@PrimaryKey val id: Int = 1, val studentCode: String)

@Entity(tableName = "doubts")
data class DoubtEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val question: String,
    val description: String,
    val author: String,
    val postedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "jobs")
data class JobEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val company: String,
    val location: String,
    val salary: String,
    val experience: String,
    val description: String,
    val contactName: String,
    val phone: String,
    val postedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "admin_logs")
data class AdminLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val studentCode: String,
    val fullName: String,
    val password: String,
    val batch: String,
    val createdAt: Long = System.currentTimeMillis()
)

@Dao
interface VegamDao {
    @Query("SELECT * FROM session WHERE id = 1") fun observeSession(): Flow<SessionEntity?>
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun saveSession(session: SessionEntity)
    @Query("DELETE FROM session") suspend fun clearSession()
    @Query("SELECT * FROM doubts ORDER BY postedAt DESC") fun observeDoubts(): Flow<List<DoubtEntity>>
    @Insert suspend fun insertDoubt(doubt: DoubtEntity)
    @Query("SELECT * FROM jobs ORDER BY postedAt DESC") fun observeJobs(): Flow<List<JobEntity>>
    @Insert suspend fun insertJob(job: JobEntity)

    @Query("SELECT * FROM admin_logs ORDER BY createdAt DESC") fun observeAdminLogs(): Flow<List<AdminLogEntity>>
    @Insert suspend fun insertAdminLog(log: AdminLogEntity)
}

@Database(entities = [SessionEntity::class, DoubtEntity::class, JobEntity::class, AdminLogEntity::class], version = 2, exportSchema = false)
abstract class VegamDatabase : RoomDatabase() { abstract fun dao(): VegamDao }
