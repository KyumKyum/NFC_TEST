package acr112u.manager

import javax.smartcardio.Card

//* Auth manager
//* Managing Authentication-related operations
class ACR122UAuthManager(
    private val card: Card
): ACR122UCommandManager(card) {

    /*
    * Function: loadDefaultAuthKey
    * Return Type: Boolean
    *
    * Description: Load a default key in reader, location: 00h
    * */
    fun loadDefaultAuthKey(): Boolean {
        val byteStream: ByteArray = sendCommand(CommandList.cmdLoadDefaultAuthKey);
        val converted = byteToStringConverter.convert(byteStream);

        if (converted.isEmpty()) return false
        return isSuccessful(converted)
    }

    /*
    * Function: authenticateBlock
    * param: Byte (block)
    * Return Type: Boolean
    *
    * Description: Authenticate the given block
    * */
    fun authenticateBlock(block:Byte): Boolean {
        val authCmd: ByteArray = CommandList.cmdAuthBlock
        authCmd[7] = block

        val resp: ByteArray = sendCommand(authCmd)
        val converted = byteToStringConverter.convert(resp);

        if (converted.isEmpty()) return false

        return isSuccessful(converted)
    }

}