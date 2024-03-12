package acr112u;

import exception.DefaultExceptionHandler
import java.lang.Exception
import javax.smartcardio.*;


class ACR122UDeviceManager{
    //* TODO: Need to encapsulate these with further development
    //* TODO: All field variables need to be private
    private lateinit var NFCCardTerminal: CardTerminal; //* Lateinit: This must be initialized for further operations.
    private val defaultExceptionHandler: DefaultExceptionHandler = DefaultExceptionHandler(); //* Excpetion Handler

    //* Read-only field for terminal
    //* Return initialized terminal
    val terminal: CardTerminal? by lazy {
        if(this::NFCCardTerminal.isInitialized) NFCCardTerminal
        else {
            println("❗️FATAL ERROR: Terminal has not been initialized!!")
            null
    } }

    //val logger: Logger = Logger.getLogger()
    //* Add Util Class

    /*
    * Function: connectDevice
    * Return Type: Boolean
    *
    * Description: Returns boolean if current environment is connected to ACR122U Reader/Writer
    * */

    //*TODO: Add throws exception
    fun connectDevice(): Boolean {
        val cardTerminalList: List<CardTerminal> = initTerminalList() ?: return false // null value means no connection.

        try{
            for(terminal: CardTerminal in cardTerminalList){
                if(terminal.name.contains("ACR122")){
                    //* DEVICE FOUND!!
                    NFCCardTerminal = terminal;
                    println("✨Device Connected!!✨ Device name: ${terminal.name}")
                    return true //* No need to search further.
                }
            }
        }catch (e:Exception){
            //* TODO add logger here
            defaultExceptionHandler.logMsg("Exception occurred while reading a device: ", e)
            return false;
        }

        //* Unreachable code if the terminal had been successfully initialized.
        return false;
    }

    /*
    * Function: connectCard
    * Return Type: Boolean
    *
    * Description: Returns boolean if the card is connected to ACR122U Reader/Writer
    * */
//    fun connectCard(protocol: String):Boolean {
//        if(! this::NFCCardTerminal.isInitialized) throw Exception("TerminalNotInitializedException");
//        try{
//            if(!NFCCardTerminal.isCardPresent){
//                println("Cannot find any connected card on the device.");
//                return false;
//            }
//
//            val connectionProtocol: String = if("" == protocol) "*" else protocol
//
//            NFCCard =
//
//            return true;
//        }catch (e: Exception){
//            defaultExceptionHandler.logMsg("Exception occurred while reading a card: ", e)
//            return false;
//        }
//    }

    //* Private Functions

    /*
    * Function: getDeviceList
    * Return Type: List<CardTerminals>

    * Description: Returns list of card terminals conencted. (Device == ACR122u Reader List)
    * */
    private fun initTerminalList(): List<CardTerminal>? {
        val cardTerminals: CardTerminals = TerminalFactory.getDefault().terminals()

        if(cardTerminals.list().isNullOrEmpty()) {
            println("No Devices detected");
            return null;
        }
        return cardTerminals.list();
    }

}