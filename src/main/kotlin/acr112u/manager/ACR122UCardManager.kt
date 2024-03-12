package acr112u.manager

import javax.smartcardio.Card
import javax.smartcardio.CardException
import javax.smartcardio.CardTerminal

class ACR122UCardManager(
    private val terminal: CardTerminal
): DefaultManager() {

    //* Read NFC Card. This will be the basis field for all R/W operations.
    private lateinit var NFCCard: Card;

    //* Read-only field for card
    //* Return initialized card
    val card: Card? by lazy {
        if(this::NFCCard.isInitialized) NFCCard
        else {
            println("❗️FATAL ERROR: Card has not been initialized!!")
            null
        }
    }

    /*
    * Function: connectCard
    * Return Type: Boolean
    *
    * Description: Returns boolean if the card is connected to ACR122U Reader/Writer
    * */
    @Throws(CardException::class)
    fun connectCard(protocol: String):Boolean {
        try{
            if(!terminal.isCardPresent){
                println("❌ Cannot find any connected card on the device.");
                return false;
            }

            println("✨ Card Found!!! Connecting...")

            val connectionProtocol: String = if("" == protocol) "*" else protocol

            //* 💫 Initialization Code for lateinit NFCCard 💫
            NFCCard = terminal.connect(connectionProtocol)
            println("✨ Card Connected!!! Card is now fully operatable. :)")

            return true;
        }catch (e: Exception){
            defaultExceptionHandler.logMsg("Exception occurred while reading a card: ", e)
            return false;
        }
    }

    /*
    * Function: loadAuthKey
    * Return Type: Boolean
    *
    * Description: Returns true if it successfully loaded default key
    * */

}