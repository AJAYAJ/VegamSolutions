package `in`.vegamdigital.app.domain.model

data class Student(
    val code: String,
    val name: String,
    val course: String,
    val branch: String,
    val batch: String,
    val rollNumber: String,
    val location: String
)

data class Course(
    val id: String,
    val title: String,
    val subtitle: String,
    val moduleCount: Int,
    val completedModules: Int,
    val isBonus: Boolean = false,
    val lessons: List<Lesson> = emptyList()
) {
    val progress: Float get() = if (moduleCount == 0) 0f else completedModules.toFloat() / moduleCount
}

data class Lesson(val id: String, val title: String, val duration: String, val completed: Boolean)

data class Job(
    val id: Long,
    val title: String,
    val company: String,
    val location: String,
    val salary: String,
    val experience: String,
    val description: String,
    val contactName: String,
    val phone: String,
    val postedAgo: String
)

data class Doubt(
    val id: Long,
    val question: String,
    val description: String,
    val author: String,
    val postedAgo: String,
    val answers: List<Answer> = emptyList()
)

data class Answer(val author: String, val text: String, val postedAgo: String)
data class Senior(val name: String, val role: String, val company: String, val code: String, val phone: String)
data class Update(val type: String, val title: String, val message: String, val postedAgo: String)

data class Dashboard(
    val student: Student,
    val courses: List<Course>,
    val jobs: List<Job>,
    val doubts: List<Doubt>,
    val seniors: List<Senior>,
    val updates: List<Update>
)
