package com.wardrobes.porenut.ui.wardrobe.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.attachment.AttachmentRepository
import com.wardrobes.porenut.data.attachment.AttachmentRestRepository
import com.wardrobes.porenut.data.element.ElementRepository
import com.wardrobes.porenut.data.element.ElementRestRepository
import com.wardrobes.porenut.data.wardrobe.WardrobeRepository
import com.wardrobes.porenut.data.wardrobe.WardrobeRestRepository
import com.wardrobes.porenut.domain.Element
import com.wardrobes.porenut.domain.Wardrobe
import com.wardrobes.porenut.pdf.PdfGenerator
import com.wardrobes.porenut.pdf.WardrobeDetailsViewEntity
import com.wardrobes.porenut.ui.common.DefaultMeasureFormatter
import com.wardrobes.porenut.ui.common.Event
import com.wardrobes.porenut.ui.common.MeasureFormatter
import com.wardrobes.porenut.ui.common.extension.updateValue
import com.wardrobes.porenut.ui.element.detail.ElementViewEntity
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

class WardrobeDashboardViewModel(
    private val wardrobeRepository: WardrobeRepository = WardrobeRestRepository,
    private val elementRepository: ElementRepository = ElementRestRepository,
    private val attachmentRepository: AttachmentRepository = AttachmentRestRepository,
    private val measureFormatter: MeasureFormatter = DefaultMeasureFormatter
) : ViewModel() {
    val loadingState: LiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }
    val viewState: LiveData<WardrobeDetailsViewEntity> = MutableLiveData()
    val messageEvent: LiveData<Event<String>> = MutableLiveData()
    val navigateUpEvent: LiveData<Event<Unit>> = MutableLiveData()

    var pdfGenerator: PdfGenerator? = null

    private var wardrobeId: String? = null

    fun loadWardrobe(wardrobeId: String) {
        this.wardrobeId = wardrobeId
        wardrobeRepository.get(wardrobeId)
            .fetchStateFullModel(
                onLoading = { loadingState.updateValue(true) },
                onSuccess = { wardrobe ->
                    viewState.updateValue(wardrobe.toViewEntity())
                    loadingState.updateValue(false)
                },
                onError = {
                    messageEvent.updateValue(Event(it))
                    loadingState.updateValue(false)
                }
            )
    }

    fun generatePdf() {
        wardrobeId?.also { id ->
            Observable.zip(
                wardrobeRepository.get(id),
                elementRepository.getAll(id),
                BiFunction { wardrobe: Wardrobe, elements: List<Element> -> (wardrobe to elements) }
            ).fetchStateFullModel(
                onLoading = { loadingState.updateValue(true) },
                onSuccess = { (wardrobe, elements) ->
                    if (pdfGenerator?.generate(wardrobe.toViewEntity(), elements.toViewEntities()) == true) {
                        "Pomyślnie wygenerowano plik PDF."
                    } else {
                        "Niestety nie udało się wygenerować pliku PDF."
                    }.also { messageEvent.updateValue(Event(it)) }
                    loadingState.updateValue(false)
                },
                onError = {
                    messageEvent.updateValue(Event(it))
                    loadingState.updateValue(false)
                }
            )
        }
    }

    fun deleteWardrobe() {
        wardrobeId?.also { id ->
            wardrobeRepository.delete(id)
                .fetchStateFullModel(
                    onLoading = { loadingState.updateValue(true) },
                    onSuccess = {
                        messageEvent.updateValue(Event("Pomyślnie usunięto szafę!"))
                        navigateUpEvent.updateValue(Event(Unit))
                    },
                    onError = {
                        messageEvent.updateValue(Event(it))
                        loadingState.updateValue(false)
                    }
                )
        }
    }

    private fun Wardrobe.toViewEntity() = WardrobeDetailsViewEntity(
        symbol = symbol,
        width = width.format(),
        height = height.format(),
        depth = depth.format()
    )

    private fun List<Element>.toViewEntities() = map {
        ElementViewEntity(
            name = it.name,
            length = it.length.format(),
            width = it.width.format(),
            height = it.height.format()
        )
    }

    private fun Float.format() = measureFormatter.format(this)

}