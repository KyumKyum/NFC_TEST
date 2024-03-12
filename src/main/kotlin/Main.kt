import acr112u.manager.ACR122UCardManager
import acr112u.manager.ACR122UDeviceManager
import acr112u.manager.ACR122UReadManager
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

    val readManager: ACR122UReadManager = ACR122UReadManager(card);
    println(readManager.readUID())
}