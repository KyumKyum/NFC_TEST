package acr112u.manager;

import acr112u.exception.DefaultExceptionHandler
import java.lang.Exception
import javax.smartcardio.*;

//* Author: Jay Lim
/* *******************************************PLEASE READ THIS*******************************************
 * This device manager is developed to handle 'ONLY ONE DEVICE' per instance. (ex: by lazy will return only firstly initialized terminal)
 * Connecting new devices or change into new reader device will require to generate new DeviceManager Instance.
 */

class ACR122UDeviceManager: DefaultManager(){
    //* TODO: Need to encapsulate these with further development
    //* TODO: All field variables need to be private
    private lateinit var NFCCardTerminal: CardTerminal; //* Lateinit: This must be initialized for further operations.
    //* Read-only field for terminal
    //* Return initialized terminal
    val terminal: CardTerminal? by lazy {
        if(this::NFCCardTerminal.isInitialized) NFCCardTerminal
        else {
            println("❗️FATAL ERROR: Terminal has not been initialized!!")
            null
        }
    }


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
                if(terminal.name.contains("ACR122")){ //* DEVICE FOUND!!

                    //* 💫 Initialization Code for lateinit NFCCardTerminal💫
                    NFCCardTerminal = terminal

                    println("✨ Device Found!! Device name: ${terminal.name}")
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
    * Function: getDeviceList (Private)
    * Return Type: List<CardTerminals>

    * Description: Returns list of card terminals conencted. (Device == ACR122u Reader List)
    * */
    private fun initTerminalList(): List<CardTerminal>? {
        val cardTerminals: CardTerminals = TerminalFactory.getDefault().terminals()

        if(cardTerminals.list().isNullOrEmpty()) {
            println("❌ No Devices detected.");
            return null;
        }
        return cardTerminals.list();
    }

}