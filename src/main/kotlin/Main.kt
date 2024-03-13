import acr112u.manager.*
import acr112u.utils.ByteToStringConverter
import java.lang.Exception
import javax.smartcardio.Card
import javax.smartcardio.CardTerminal
//* Tests!
fun main(args: Array<String>) {
    //* MacOS Dedicated: Big Sur
    //* TODO: need a condition that only MacOS execute this
    System.setProperty("sun.security.smartcardio.library", "/System/Library/Frameworks/PCSC.framework/Versions/Current/PCSC");

    val deviceManager: ACR122UDeviceManager = ACR122UDeviceManager()
    if(!deviceManager.connectDevice()) return; //* Device didn't connected.
    val terminal: CardTerminal = deviceManager.terminal ?: throw Exception("TODO")

    val cardManager: ACR122UCardManager = ACR122UCardManager(terminal)
    if(!cardManager.connectCard("")) return
    val card: Card = cardManager.card ?: throw Exception("TODO")

    val readManager: ACR122UReadManager = ACR122UReadManager(card)
    val writeManager: ACR112UWriteManager = ACR112UWriteManager(card)
    val authManager: ACR122UAuthManager = ACR122UAuthManager(card)
//    println(readManager.readUID())

    authManager.loadDefaultAuthKey() //* Init

    val block: Byte = 0x05.toByte()

//    if(authManager.authenticateBlock(block)) {
//        println(readManager.readBlock_16(block))
//    }

    val data: ByteArray = byteArrayOf(0xff.toByte(), 0x82.toByte(), 0x02.toByte(), 0x30.toByte(), 0x06.toByte(), 0xfa.toByte(), 0xd3.toByte())

    if(authManager.authenticateBlock(block)){
        //writeManager.writeBlock_16(block,data)
        writeManager.resetBlock_16(block)
    }

}