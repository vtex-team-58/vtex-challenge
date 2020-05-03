import com.twilio.utils.TwilioAuth
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe

class TwilioAuthTest: Spek({
    describe("generateToken"){
        val token = TwilioAuth().generateToken("fernando", "browser")
        println("token: $token")
    }
})