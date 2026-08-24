package `in`.vegamdigital.app.data.repository

import `in`.vegamdigital.app.data.local.DoubtEntity
import `in`.vegamdigital.app.data.local.JobEntity
import `in`.vegamdigital.app.data.local.SessionEntity
import `in`.vegamdigital.app.data.local.VegamDao
import `in`.vegamdigital.app.data.remote.FirebaseAuthGateway
import `in`.vegamdigital.app.data.remote.FirestoreGateway
import `in`.vegamdigital.app.domain.model.*
import `in`.vegamdigital.app.domain.repository.StudentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StudentRepositoryImpl @Inject constructor(
    private val dao: VegamDao,
    private val auth: FirebaseAuthGateway,
    private val firestore: FirestoreGateway
) : StudentRepository {
    private val localAnswers = MutableStateFlow<Map<Long, List<Answer>>>(emptyMap())
    override val signedIn = dao.observeSession().map { it != null }

    private val student = Student(
        code = "SYF-AMP-DM26-B03-014", name = "Anusha Reddy", course = "Digital Marketing",
        branch = "AMP", batch = "B03", rollNumber = "014", location = "Ameerpet"
    )
    private val courses = listOf(
        Course("digital-marketing", "Digital Marketing", "డిజిటల్ మార్కెటింగ్", 15, 15, lessons = listOf(
            Lesson("1", "Digital marketing foundations", "18 min", true),
            Lesson("2", "SEO and keyword research", "26 min", true),
            Lesson("3", "Google Ads campaign setup", "32 min", true),
            Lesson("4", "Meta Ads and creative strategy", "24 min", true),
            Lesson("5", "Analytics, reports and client defense", "29 min", true)
        )),
        Course("ai-marketers", "AI Tools for Marketers", "ChatGPT, Canva AI, ad copy tools", 3, 0, true, listOf(
            Lesson("b1", "Prompting for marketing", "21 min", false),
            Lesson("b2", "AI creatives with Canva", "25 min", false),
            Lesson("b3", "AI-powered ad copy", "19 min", false)
        ))
    )
    private val seedJobs = listOf(
        Job(1001, "Junior SEO Executive", "Webdew Digital", "Madhapur, Hyderabad", "₹15,000 – ₹20,000 / month", "Fresher", "On-page SEO, blog publishing, Search Console reports. WordPress telisina vallaki preference.", "Rakesh Goud", "+91 98765 00011", "8 days ago"),
        Job(1002, "Meta Ads Executive", "Grow Media", "Gachibowli", "₹18,000 / month + incentives", "0–1 year", "Daily campaign monitoring, creative testing and reporting.", "Sravani M", "+91 98765 00022", "11 days ago")
    )
    private val seedDoubts = listOf(
        Doubt(1001, "Client ki monthly report lo em cover cheyyali?", "First client vachadu. Report lo enni metrics pettali?", "Naveen Kumar", "7 days ago"),
        Doubt(1002, "Meta pixel setup ayyaka events track avvatledu — em cheyyali?", "", "Imran Khan", "7 days ago", listOf(Answer("Rakesh Goud", "Events Manager lo test events first verify cheyyandi.", "6 days ago"))),
        Doubt(1003, "Google Ads lo quality score penchadaniki best way enti?", "", "Rakesh Goud", "8 days ago", listOf(Answer("Mentor", "Ad relevance, landing page and expected CTR ni improve cheyyandi.", "7 days ago")))
    )
    private val seniors = listOf(
        Senior("Rakesh Goud", "SEO Analyst", "Webdew Digital", "SYF-AMP-DM24-B01-006", "+919876500011"),
        Senior("Sravani M", "Google Ads Executive", "Grow Media", "SYF-AMP-DM24-B02-011", "+919876500022"),
        Senior("Imran Khan", "Performance Marketer", "PixelWorks", "SYF-AMP-DM25-B03-002", "+919876500033")
    )
    private val updates = listOf(
        Update("COURSE", "Free course: AI Tools for Marketers", "Extra fee ledu — Course tab lo chudandi.", "7 days ago"),
        Update("COURSE", "Module 14 add chesamu", "Ad Fraud Detection & Client Defense.", "9 days ago"),
        Update("JOBS", "Two new jobs are live", "SEO and Meta Ads roles in Hyderabad.", "11 days ago")
    )

    override val dashboard = combine(dao.observeDoubts(), dao.observeJobs(), localAnswers) { savedDoubts, savedJobs, answers ->
        Dashboard(
            student, courses,
            savedJobs.map { it.toDomain() } + seedJobs,
            savedDoubts.map { it.toDomain(answers[it.id].orEmpty()) } + seedDoubts,
            seniors, updates
        )
    }

    override suspend fun login(code: String, password: String): Result<Unit> = runCatching {
        require(auth.signIn(code, password)) { "Student code or password is incorrect" }
        dao.saveSession(SessionEntity(studentCode = code))
    }

    override suspend fun logout() { auth.signOut(); dao.clearSession() }

    override suspend fun askDoubt(question: String, description: String) {
        require(question.isNotBlank()) { "Please enter your question" }
        dao.insertDoubt(DoubtEntity(question = question, description = description, author = student.name))
        firestore.add("doubts", mapOf("question" to question, "description" to description, "studentCode" to student.code))
    }

    override suspend fun answerDoubt(doubtId: Long, answer: String) {
        if (answer.isBlank()) return
        localAnswers.value += (doubtId to (localAnswers.value[doubtId].orEmpty() + Answer(student.name, answer, "Just now")))
        firestore.add("answers", mapOf("doubtId" to doubtId, "answer" to answer))
    }

    override suspend fun postJob(job: Job) {
        require(job.title.isNotBlank() && job.company.isNotBlank() && job.phone.length >= 10) { "Enter job title, company and a valid phone number" }
        dao.insertJob(JobEntity(title = job.title, company = job.company, location = job.location, salary = job.salary,
            experience = job.experience, description = job.description, contactName = student.name, phone = job.phone))
        firestore.add("jobs", mapOf("title" to job.title, "company" to job.company, "status" to "pending"))
    }

    override suspend fun sendReferral(name: String, phone: String, note: String) {
        require(name.isNotBlank() && phone.length >= 10) { "Enter a valid name and phone number" }
        firestore.add("referrals", mapOf("name" to name, "phone" to phone, "note" to note, "referrer" to student.code))
    }

    private fun DoubtEntity.toDomain(answers: List<Answer>) = Doubt(id, question, description, author, "Just now", answers)
    private fun JobEntity.toDomain() = Job(id, title, company, location, salary, experience, description, contactName, phone, "Pending approval")
}
