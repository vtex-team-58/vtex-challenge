import com.twilio.web.chat.ChatServices
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe

class ChatServicesTest: Spek({
    describe("createNewChannel"){
        val teste = ChatServices().createNewChannel()
        println(teste)
    }
    describe("deleteChannel"){
        val teste = ChatServices().deleteChannel("CH77d698524693477586950c0e727a0b18")
        println(teste)
    }
    describe("getChannels"){
        val teste = ChatServices().getPendingChannels()
        println(teste)
    }
})