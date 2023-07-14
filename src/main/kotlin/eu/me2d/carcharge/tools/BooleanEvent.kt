package eu.me2d.carcharge.tools

class BooleanEvent(val from: Boolean?, val to:Boolean?) {
    fun isChanged(): Boolean {
        return from != to
    }
    fun stayedFalse(): Boolean {
        return from == false && to == false
    }
    fun stayedTrue(): Boolean {
        return from == true && to == true
    }
    fun becameTrue(): Boolean {
        return from == false && to == true
    }
    fun becameFalse(): Boolean {
        return from == true && to == false
    }
}