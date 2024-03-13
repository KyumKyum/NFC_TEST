package acr112u.manager

import javax.smartcardio.Card

//* Read manager
//* Managing Read-related operations
class ACR122UReadManager(
    private val card:Card
): ACR122UCommandManager(card) {

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

    /*
    * Function: readBlock_16
    * Param: Byte (block)
    * Return Type: List<String>
    *
    * Description: Reads 16 byte of binaries from the given page.
    * */

    fun readBlock_16(block: Byte): List<String>? {
        //* Send command to load auth key (default)

        val readBlockCmd: ByteArray = CommandList.cmdRead16Byte
        readBlockCmd[3] = block

        val resp: ByteArray = sendCommand(readBlockCmd)
        val converted = byteToStringConverter.convert(resp);

        if (converted.isEmpty()) return null
        if(!isSuccessful(converted)) return null

        return converted.subList(0, 16);
    }
}