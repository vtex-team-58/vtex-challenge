import com.twilio.utils.DataBaseConnection
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import java.sql.PreparedStatement

class DataBaseConnectionTest: Spek({
    describe("getConnection"){
        val conn = DataBaseConnection.getConnection()
        val ps = conn.prepareStatement("""
            SELECT id from teste
        """.trimIndent())
        val rs = ps.executeQuery()
        while(rs.next()){
            println(rs.getString("id"))
        }
        rs.close()
        ps.close()
        conn.close()
    }
})