import acr112u.ACR122UDeviceManager
import java.lang.Exception
import javax.smartcardio.CardTerminal

fun main(args: Array<String>) {
    //* MacOS Dedicated: Big Sur
    //* TODO: need a condition that only MacOS execute this
    System.setProperty("sun.security.smartcardio.library", "/System/Library/Frameworks/PCSC.framework/Versions/Current/PCSC");

    val deviceManager: ACR122UDeviceManager = ACR122UDeviceManager()
    if(deviceManager.connectDevice()){
        val terminal: CardTerminal = deviceManager.terminal ?: throw Exception("TODO")
    }

}