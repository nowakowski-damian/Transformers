package com.dnowakowski.transformers.main.transformersTab.addDialog

import android.databinding.ObservableField
import com.dnowakowski.transformers.data.model.Transformer
import com.dnowakowski.transformers.data.repository.TransformersRepository
import com.dnowakowski.transformers.injection.dialog.DialogScope
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@DialogScope
class AddTransformerDialogViewModel @Inject constructor(
        private val transformersRepository: TransformersRepository
) {

    val name = ObservableField<String>("")
    val strength = ObservableField<String>("")
    val intelligence = ObservableField<String>("")
    val speed = ObservableField<String>("")
    val endurance = ObservableField<String>("")
    val rank = ObservableField<String>("")
    val courage = ObservableField<String>("")
    val firepower = ObservableField<String>("")
    val skill = ObservableField<String>("")

    var type = ObservableField<Transformer.Type>(Transformer.Type.AUTOBOT)

    val events = PublishSubject.create<AddTransformerDialogEvent>()

    fun onSaveTransformer() {
        val transformer = Transformer(
                name.get() ?:"",
                type.get() ?: Transformer.Type.AUTOBOT,
                parseIntSafely(strength),
                parseIntSafely(intelligence),
                parseIntSafely(speed),
                parseIntSafely(endurance),
                parseIntSafely(rank),
                parseIntSafely(courage),
                parseIntSafely(firepower),
                parseIntSafely(skill)
        )
        transformersRepository.saveTransformer(transformer)
                .subscribe( {events.onNext(AddTransformerDialogEvent.TransformerSaved(it))})
    }

    private fun parseIntSafely(field: ObservableField<String>): Int {
        val string = field.get() ?: "0"
        var number: Int
        try {
            number = Integer.parseInt(string)
        }
        catch (exception: NumberFormatException) {
            exception.printStackTrace()
            number = 0
        }
        return number
    }

}

sealed class AddTransformerDialogEvent {
    class TransformerSaved(val transformer: Transformer): AddTransformerDialogEvent()
}