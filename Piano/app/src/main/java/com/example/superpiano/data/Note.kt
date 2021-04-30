package com.example.superpiano.data

data class Note(val value:String, val startScore:Long, val startNote:Long, val endNote:Long){

    var scoreTime = (startNote - startScore)
    var noteDuration = (endNote - startNote)

    override fun toString(): String {
        return "Note: $value, Timestamp: $scoreTime ms, Note Duration: $noteDuration ms"
    }
}