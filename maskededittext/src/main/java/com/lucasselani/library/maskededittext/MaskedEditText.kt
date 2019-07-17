package com.lucasselani.library.maskededittext

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class MaskedEditText : AppCompatEditText {

    companion object {
        @JvmStatic
        val documentMasks: List<String> = listOf("###.###.###-##", "##.###.###/####-##")
        @JvmStatic
        val phoneMasks: List<String> = listOf("(##) ####-####", "(##) #####-####")
        @JvmStatic
        val bankSlipMask = listOf("#####.#####*#####.#####*#####.##### #*##############",
            "8##########-#*###########-#*###########-#*###########-#")
    }

    private var masks: List<String> = emptyList()
    private var updating = false
    private var callback: (() -> Unit)? = null

    val masked: String
        get() = this.text.toString()

    val raw: String
        get() = unmask(this.text.toString())

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(editable: Editable?) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(masks.isNotEmpty()) onTextChanged(p0.toString())
            }
        })
    }

    private fun onTextChanged(text: String) {
        if (updating) {
            updating = false
            return
        }

        updating = true
        val maskedText = if(text.isEmpty()) "" else mask(text)
        this.setText(maskedText)
        this.setSelection(maskedText.length)
        this.callback?.invoke()
    }

    private fun mask(text: String): String {
        val unmasked = unmask(text)
        var chosenMask: String? = currentMask(unmasked)
        if (chosenMask.isNullOrEmpty()) return text.dropLast(1)

        chosenMask = replaceMask(unmasked, chosenMask)
        chosenMask = chosenMask.dropLastWhile { it != unmasked.last() }
        return if(chosenMask.contains('*')) chosenMask.replaceEvery("\n", '*')
        else chosenMask
    }

    private fun replaceMask(str: String, mask: String) : String {
        var tempMask = mask
        var isFirst = true
        str.forEach {
            tempMask = if(isFirst && tempMask.first().toString().matches("\\d+".toRegex())) {
                tempMask.replaceFirst(tempMask.first(), it)
            } else tempMask.replaceFirst('#', it)
            isFirst = false
        }
        return tempMask
    }

    private fun unmask(str: String): String = str.replace("[^A-Za-z0-9]*".toRegex(), "")

    private fun maskLength(mask: String): Int = mask.replace("[^#]*".toRegex(), "").length

    private fun hasLength(str: String, mask: String, bias: Int = 0) = str.length <= maskLength(mask) + bias

    private fun currentMask(str: String): String? =
        masks.find { str.matchMask(it) && hasLength(str, it, 1) } ?:
        masks.find { hasLength(str, it) && it.firstIsNotNumber() }

    fun setMasks(masks: List<String>) {
        this.masks = masks.sortedWith(compareBy { it.length })
    }

    fun setOnTextChangedCallback(callback: (() -> Unit)?) {
        this.callback = callback
    }
}

fun String.replaceEvery(substitute: String, placeholder: Char): String {
    var temp = ""
    this.forEach {
        temp += if(it == placeholder) substitute else it
    }
    return temp
}

fun String.matchMask(mask: String): Boolean {
    return this.first() == mask.first()
}

fun String.firstIsNotNumber(): Boolean {
    return this.first().toString().matches("[^0-9]".toRegex())
}