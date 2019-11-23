package com.wardrobes.porenut.pdf

import android.content.Context
import android.os.Environment
import com.itextpdf.text.Document
import com.itextpdf.text.Element
import com.itextpdf.text.Font
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.pdf.PdfWriter
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.element.detail.ElementViewEntity
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

// TODO REFACTOR!!!
// TODO ADD GENERATOR TO WARDROBE

interface PdfGenerator {

    fun generate(wardrobe: WardrobeDetailsViewEntity, elements: List<ElementViewEntity>): Boolean
}

class DefaultPdfGenerator(private val context: Context) : PdfGenerator {

    private val baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED)
    private val boldFont = Font(baseFont, 32F, Font.BOLD)
    private val normalFont = Font(baseFont, 12F, Font.NORMAL)

    override fun generate(
        wardrobe: WardrobeDetailsViewEntity,
        elements: List<ElementViewEntity>
    ): Boolean {
        val filePath = "${Environment.getExternalStorageDirectory()}/${wardrobe.symbol}.pdf"
        try {
            val document = Document()
            val writer = PdfWriter.getInstance(document, FileOutputStream(filePath))
            document.open()
            document.addTopSection(wardrobe.symbol)
            document.addEmptyLine(numberOfLines = 3)
            document.addWardrobe(wardrobe)
            elements.forEach {
                document.newPage()
                document.addElement(it)
            }
            document.close()
            writer.flush()
            writer.close()
            return true
        } catch (ignored: Exception) {
            ignored.printStackTrace()
            return false
        }
    }

    private fun Document.addTopSection(title: String) {
        Paragraph().apply {
            val currentDate = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(Date())
            addText(currentDate, normalFont, Element.ALIGN_RIGHT)
            addEmptyLine(numberOfLines = 3)
            addText(title, boldFont, Element.ALIGN_CENTER)
        }.also { add(it) }
    }

    private fun Document.addWardrobe(wardrobe: WardrobeDetailsViewEntity) {
        Paragraph().apply {
            addText("${context.getString(R.string.l_width)}: ${wardrobe.width}", normalFont)
            addText("${context.getString(R.string.l_height)}: ${wardrobe.height}", normalFont)
            addText("${context.getString(R.string.l_depth)}: ${wardrobe.depth}", normalFont)
        }.also { add(it) }
    }

    private fun Document.addElement(element: ElementViewEntity) {
        Paragraph().apply {
            addText(context.getString(R.string.l_element).capitalize(), Font(baseFont, 24F, Font.BOLD), Element.ALIGN_CENTER)
            addEmptyLine()
            addText("${context.getString(R.string.l_name)}: ${element.name}", normalFont)
            addText("${context.getString(R.string.l_length)}: ${element.length}", normalFont)
            addText("${context.getString(R.string.l_width)}: ${element.width}", normalFont)
            addText("${context.getString(R.string.l_height)}: ${element.height}", normalFont)
        }.also { add(it) }
    }

    private fun Paragraph.addText(text: String, font: Font, alignment: Int = Element.ALIGN_LEFT) {
        add(Paragraph(text, font).apply { this.alignment = alignment })
    }

    private fun Document.addEmptyLine(numberOfLines: Int = 1) {
        add(Paragraph().apply { addEmptyLine(numberOfLines) })
    }

    private fun Paragraph.addEmptyLine(numberOfLines: Int = 1) {
        repeat(numberOfLines) { add(emptyLine()) }
    }

    private fun emptyLine() = Paragraph(" ")
}

class WardrobeDetailsViewEntity(
    val symbol: String,
    val width: String,
    val height: String,
    val depth: String
)