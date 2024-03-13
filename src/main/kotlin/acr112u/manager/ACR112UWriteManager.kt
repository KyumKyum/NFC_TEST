package acr112u.manager

import javax.smartcardio.Card

//* Write manager
//* Managing Write-related operations
class ACR112UWriteManager(
    private val card:Card
): ACR122UCommandManager(card) {

    //* 16-bit padding value
    private val padding: ByteArray = byteArrayOf(0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte())

    /*
    * Function: writeBlock_16
    * Param: Byte (block), ByteArray(data)
    * Return Type: Boolean
    *
    * Description: Writes 16 byte of binaries to given block.
    * */

    fun writeBlock_16(block: Byte, data: ByteArray): Boolean{
        //* Command
        val writeBlockCmdIncomplete = CommandList.cmdWrite16Byte
        writeBlockCmdIncomplete[3] = block //* The location of 16-byte array to be written.

        //* Add pads into data
        var dataStream: ByteArray = data;

        if(dataStream.size != 16){
            //* Requires pad to fill 16-byte
            //* Clip the data if it exceeds 16 byte
            dataStream = (data + padding).sliceArray(0..15)
        }

        val writeBlockCmdComplete: ByteArray = writeBlockCmdIncomplete + dataStream //* Builds the complete write request command.

        //* Send Command
        val resp: ByteArray = sendCommand(writeBlockCmdComplete)
        val converted: List<String> = byteToStringConverter.convert(resp)

        if(converted.isEmpty()) return false

        return isSuccessful(converted)
    }

    /*
  * Function: resetBlock_16
  * Param: Byte (block)
  * Return Type: Boolean
  *
  * Description: Writes 16 byte of 0x00 to given block.
  * */
    fun resetBlock_16(block: Byte):Boolean{
        val writeBlockCmd = CommandList.cmdWrite16Byte
        writeBlockCmd[3] = block //* The location of 16-byte array to be written.

        val resetBlockCmd = writeBlockCmd + padding //* Fill 00 for current block

        val resp: ByteArray = sendCommand(resetBlockCmd)
        val converted: List<String> = byteToStringConverter.convert(resp)

        if(converted.isEmpty()) return false

        return isSuccessful(converted)
    }
}