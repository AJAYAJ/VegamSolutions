package `in`.vegamdigital.app.util

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.net.Uri
import androidx.core.content.FileProvider
import `in`.vegamdigital.app.domain.model.Student
import java.io.File
import java.io.FileOutputStream

object CertificatePdfGenerator {
    private const val PAGE_WIDTH = 842
    private const val PAGE_HEIGHT = 595
    private const val NAVY = 0xFF0B1934.toInt()
    private const val GOLD = 0xFFF3C969.toInt()
    private const val BLUE = 0xFF8DB8FF.toInt()

    fun create(context: Context, student: Student): Uri {
        val directory = File(context.cacheDir, "certificates").apply { mkdirs() }
        val safeCode = student.code.replace(Regex("[^A-Za-z0-9_-]"), "_")
        val file = File(directory, "Vegam_Certificate_$safeCode.pdf")
        val document = PdfDocument()

        try {
            val pageInfo = PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, 1).create()
            val page = document.startPage(pageInfo)
            val canvas = page.canvas
            val paint = Paint(Paint.ANTI_ALIAS_FLAG)

            canvas.drawColor(NAVY)
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 4f
            paint.color = GOLD
            canvas.drawRect(22f, 22f, PAGE_WIDTH - 22f, PAGE_HEIGHT - 22f, paint)
            paint.strokeWidth = 1f
            canvas.drawRect(31f, 31f, PAGE_WIDTH - 31f, PAGE_HEIGHT - 31f, paint)
            paint.style = Paint.Style.FILL

            drawCentered(canvas, paint, "VEGAM DIGITAL ACADEMY", 83f, 21f, GOLD, true, 2.5f)
            drawCentered(canvas, paint, "CERTIFICATE OF COMPLETION", 145f, 17f, BLUE, true, 1.8f)
            drawCentered(canvas, paint, "This certificate is proudly presented to", 197f, 14f, Color.LTGRAY)
            drawCenteredFitted(canvas, paint, student.name, 258f, 32f, 520f, Color.WHITE, true)

            paint.color = GOLD
            canvas.drawRect(175f, 278f, PAGE_WIDTH - 175f, 280f, paint)

            drawCentered(canvas, paint, "for successfully completing the", 319f, 14f, Color.LTGRAY)
            drawCentered(canvas, paint, "Digital Marketing - Job Seeker Track", 357f, 21f, Color.WHITE, true)
            drawCentered(canvas, paint, student.code, 400f, 16f, GOLD, true, 1.2f)

            drawFact(canvas, paint, "JOINED", "15 Aug 2026", 285f)
            drawFact(canvas, paint, "COMPLETED", "15 Aug 2026", 557f)

            val location = listOf(student.branch, student.location)
                .filter { it.isNotBlank() }
                .distinct()
                .joinToString(" branch - ")
                .ifBlank { "SR Nagar branch - Hyderabad" }
            drawCentered(canvas, paint, location, 531f, 12f, Color.LTGRAY)

            document.finishPage(page)
            FileOutputStream(file).use(document::writeTo)
        } finally {
            document.close()
        }

        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
    }

    private fun drawFact(canvas: android.graphics.Canvas, paint: Paint, label: String, value: String, x: Float) {
        paint.textAlign = Paint.Align.CENTER
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        paint.textSize = 12f
        paint.color = BLUE
        canvas.drawText(label, x, 464f, paint)
        paint.textSize = 15f
        paint.color = Color.WHITE
        canvas.drawText(value, x, 489f, paint)
    }

    private fun drawCentered(
        canvas: android.graphics.Canvas,
        paint: Paint,
        text: String,
        y: Float,
        size: Float,
        color: Int,
        bold: Boolean = false,
        letterSpacing: Float = 0f
    ) {
        paint.textAlign = Paint.Align.CENTER
        paint.typeface = Typeface.create(Typeface.DEFAULT, if (bold) Typeface.BOLD else Typeface.NORMAL)
        paint.textSize = size
        paint.color = color
        paint.letterSpacing = letterSpacing / size
        canvas.drawText(text, PAGE_WIDTH / 2f, y, paint)
        paint.letterSpacing = 0f
    }

    private fun drawCenteredFitted(
        canvas: android.graphics.Canvas,
        paint: Paint,
        text: String,
        y: Float,
        preferredSize: Float,
        maxWidth: Float,
        color: Int,
        bold: Boolean
    ) {
        paint.typeface = Typeface.create(Typeface.DEFAULT, if (bold) Typeface.BOLD else Typeface.NORMAL)
        paint.textSize = preferredSize
        if (paint.measureText(text) > maxWidth) {
            paint.textSize = preferredSize * maxWidth / paint.measureText(text)
        }
        paint.textAlign = Paint.Align.CENTER
        paint.color = color
        canvas.drawText(text, PAGE_WIDTH / 2f, y, paint)
    }
}
