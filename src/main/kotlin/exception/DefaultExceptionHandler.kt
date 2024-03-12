package exception

import java.lang.Exception

class DefaultExceptionHandler {
    //* TODO: This is simple exception handler. Need for further development.
    fun logMsg(msg: String, e: Exception) {
        //* TODO: This need to be changed into logger
        println("${msg} ${e.message}")
    }
}