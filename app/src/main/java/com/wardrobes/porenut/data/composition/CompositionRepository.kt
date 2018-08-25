package com.wardrobes.porenut.data.composition

import com.wardrobes.porenut.domain.ReferenceElementRelativeDrillingComposition
import com.wardrobes.porenut.domain.ReferenceElementRelativeDrillingCompositionLight
import io.reactivex.Observable

interface CompositionRepository {

    fun getAll(elementId: Long): Observable<List<ReferenceElementRelativeDrillingComposition>>

    fun add(composition: ReferenceElementRelativeDrillingCompositionLight): Observable<Unit>
}