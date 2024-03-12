package acr112u.utils

//* Util class that converts byte value to string value
class ByteToStringConverter {
    /*
    * Function: convert
    * Param: ByteArray (byteStream)
    * Return Type: List<String>
    *
    * Description: Converts byte array into string, returns it.
    * */

    fun convert(byteStream: ByteArray): List<String> {
        val convertedStream: MutableList<String> = mutableListOf<String>()

        for(byte:Byte in byteStream){
            convertedStream.add(String.format("%02X",byte));
        }

        return convertedStream.toList();
    }

}