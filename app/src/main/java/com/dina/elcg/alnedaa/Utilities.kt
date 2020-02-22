package com.dina.elcg.alnedaa

object Utilities {

    fun splitQuestion(question: String): List<String> {
        return question.split("\\s".toRegex())
    }

}