package acr112u.manager

import java.lang.Exception
import javax.smartcardio.Card
import javax.smartcardio.CardChannel
import javax.smartcardio.CardException
import javax.smartcardio.CommandAPDU
import javax.smartcardio.ResponseAPDU

//* CommandManager
class ACR122UCommandManager(
    private val card: Card
): DefaultManager() {

    //* ACR122U CommandList
    //* Reference: ACS (Advanced Card Systems Ltd.) - ACR122U API Docs
    //* Command APDU Structure
    //* Refer the API docs for further information.
    protected object CommandList {
        //* Command: Get UID
        //* Command to read UID of card.
        val cmdGetUID: ByteArray = byteArrayOf(0xff.toByte(), 0xca.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte());
    }

    /*
    * Function: sendCommand (protected)
    * Param: ByteArray (apduCmd)
    * Return Type: ByteArray
    *
    * Description: Sends a command to NFC card.
    * */
    @Throws(CardException::class)
    protected fun sendCommand(apduCmd: ByteArray): ByteArray {
        try{
            //* Open card channel
            val cardChannel: CardChannel = card.basicChannel;
            //* CommandAPDU/ResponseAPDU to process APDU Structure
            val commandAPDU: CommandAPDU = CommandAPDU(apduCmd);
            val responseAPDU: ResponseAPDU = cardChannel.transmit(commandAPDU);

            //* Return the response
            return responseAPDU.bytes;
        } catch (ce: CardException){
            //* Card Exception
            //* TODO: Add logger or card exception handler
            println("❗️ Command Transmission Failed!!!!!! msg: ${ce.message}" )
            return byteArrayOf() //* Null byte array
        } catch (e: Exception){
            defaultExceptionHandler.logMsg("❗️ Exception occurred while sending command", e);
            return byteArrayOf() //* Null byte array
        }
    }


}