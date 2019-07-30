package com.lucasselani.maskededittext

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.lucasselani.library.maskededittext.MaskedEditText
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setEditText()
    }
    
    private fun setEditText() {
        phone.setMasks(MaskedEditText.phoneMasks)
        phone.setOnTextChangedCallback { logVerbose("Phone", phone) }
        phone.setRules { PhoneValidator.validate(phone.raw)  }

        document.setMasks(MaskedEditText.documentMasks)
        document.setOnTextChangedCallback { logVerbose("Document", document) }

        bankSlip.setMasks(MaskedEditText.bankSlipMask)
        bankSlip.setOnTextChangedCallback { logVerbose("BankSlip", bankSlip) }
    }

    private fun logVerbose(tag: String, editText: MaskedEditText) {
        Log.v(tag, "Masked: ${editText.masked}\nRaw: ${editText.raw}")
    }
}