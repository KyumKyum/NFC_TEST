package acr112u.manager

import acr112u.utils.ByteToStringConverter
import java.lang.Exception
import javax.smartcardio.Card
import javax.smartcardio.CardChannel
import javax.smartcardio.CardException
import javax.smartcardio.CommandAPDU
import javax.smartcardio.ResponseAPDU

//* CommandManager
open class ACR122UCommandManager(
    private val card: Card
): DefaultManager() {

    protected  val byteToStringConverter: ByteToStringConverter = ByteToStringConverter()

    //* ACR122U CommandList
    //* Reference: ACS (Advanced Card Systems Ltd.) - ACR122U API Docs
    //* Command APDU Structure
    //* Refer the API docs for further information.
    protected object CommandList {
        //* Command: Get UID
        //* Command to read UID of card.
        val cmdGetUID: ByteArray = byteArrayOf(0xff.toByte(), 0xca.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte())

        //* Command: load Authentication Key
        //* Command to load a default authentication key into dedicated location.
        //* Default Auth Key: Load a key {FF FF FF FF FF FFh} into the key location 00h (Referred the documentation)
        val cmdLoadDefaultAuthKey: ByteArray = byteArrayOf(0xff.toByte(), 0x82.toByte(), 0x00.toByte(), 0x00.toByte(), 0x06.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte())

        //* Command: Get Authentication for single block
        //* Command to use the key stored in the reader, proceed authentication for given block
        //* key is saved in 00h (cmdLoadDefaultAuthKey)
        val cmdAuthBlock: ByteArray = byteArrayOf(0xff.toByte(), 0x86.toByte(), 0x00.toByte(), 0x00.toByte(), 0x05.toByte(), 0x01.toByte(), 0x00.toByte(), 0x00.toByte(), 0x60.toByte(), 0x00.toByte())


        //* Command: Read Binary Block 16Byte
        //* Command to read 16-byte binary block from give page
        //* 4th block need to be parsed!
        val cmdRead16Byte: ByteArray = byteArrayOf(0xff.toByte(), 0xb0.toByte(), 0x00.toByte(), 0x00.toByte(), 0x10.toByte())
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

    /*
    * Function: verifyResp (protected)
    * Param: List<String>
    * Return Type: boolean
    *
    * Description: Verifies the response of the NFC.
    * */
    protected fun isSuccessful(resp: List<String>): Boolean {
        val code: List<String> = resp.takeLast(2);
        val sw1: String = code[0]
        val sw2: String = code[1]
        //* 90 00: Success
        //* 63 00: Failed -> TODO: Implement subroutines for such case
        //* 6A 81: Not Supported -> TODO: Implement subroutines for such case
        return (sw1 == "90") && (sw2 == "00")
    }

}