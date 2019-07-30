package com.lucasselani.maskededittext

class PhoneValidator {

    companion object {
        fun validate(text: String): Boolean {
            return checkDDD(text) && check9Digit(text) && checkFirstNumberDigit(text)
        }

        private fun checkDDD(text: String): Boolean {
            return when {
                text.length < 2 -> true
                text.first() == '1' -> true
                text.first() == '2' -> text[1] == '1' || text[1] == '2' || text[1] == '4' || text[1] == '7' || text[1] == '8'
                text.first() == '3' -> text[1] == '1' || text[1] == '2' || text[1] == '3' || text[1] == '4'
                        || text[1] == '5' || text[1] == '7' || text[1] == '8'
                text.first() == '4' -> true
                text.first() == '5' -> text[1] == '1' || text[1] == '3' || text[1] == '4' || text[1] == '5'
                text.first() == '6' -> true
                text.first() == '7' -> text[1] == '1' || text[1] == '3' || text[1] == '4' || text[1] == '5'
                        || text[1] == '7' || text[1] == '9'
                text.first() == '8' -> true
                text.first() == '9' -> true
                else -> false
            }
        }

        private fun check9Digit(text: String): Boolean {
            return when {
                text.length < 3 || text[2] == '9' -> true
                else -> false
            }
        }

        private fun checkFirstNumberDigit(text: String): Boolean {
            return when {
                text.length < 4 ||text[3] == '6' || text[3] == '7' || text[3] == '8' || text[3] == '9' -> true
                else -> false
            }
        }
    }
}