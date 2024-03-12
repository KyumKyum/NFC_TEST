package acr112u.manager

import acr112u.utils.ByteToStringConverter
import javax.smartcardio.Card

//* Read manager
//* Managing Read-related operations
class ACR122UReadManager(
    private val card:Card
): ACR122UCommandManager(card) {

    private val byteToStringConverter: ByteToStringConverter = ByteToStringConverter();

    /*
    * Function: readUID
    * Return Type: List<String>
    *
    * Description: Reads a UID of card
    * */
    fun readUID(): List<String>? {
        val byteStream: ByteArray = sendCommand(CommandList.cmdGetUID);
        val converted = byteToStringConverter.convert(byteStream);
        //* Response Format: [UID, UID, UID, UID, SW1, SW2]
        if (converted.isEmpty()) return null
        if (!isSuccessful(converted)) return null;

        return converted.subList(0,4);
    }
}