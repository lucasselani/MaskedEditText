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
        val phoneMasks: List<String> = listOf("(##)####-####", "(##)#####-####")
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

        unmasked.forEach {
            chosenMask = chosenMask!!.replaceFirst('#', it)
        }
        return chosenMask!!.dropLastWhile { it != unmasked.last() }
    }

    private fun unmask(s: String): String = s.replace("[^A-Za-z0-9]*".toRegex(), "")

    private fun maskLength(mask: String): Int = mask.replace("[^#]*".toRegex(), "").length

    private fun currentMask(str: String): String? = masks.find { str.length <= maskLength(it) }

    fun setMasks(masks: List<String>) {
        this.masks = masks.sortedWith(compareBy { it.length })
    }

    fun setOnTextChangedCallback(callback: (() -> Unit)?) {
        this.callback = callback
    }
}
