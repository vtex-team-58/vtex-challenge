package com.twilio.utils

import java.io.FileInputStream
import java.sql.Connection
import java.sql.DriverManager
import java.util.*

class DataBaseConnection {

    companion object {
        fun getConnection(): Connection {
            val fileProperty = FileInputStream("database.properties")
            val properties = Properties()
            properties.load(fileProperty)
//            Class.forName("com.postgresql.jdbc.Driver")
            return DriverManager.getConnection("jdbc:postgresql://${properties.getProperty("host")}:${properties.getProperty("port")}/${properties.getProperty("database")}?user=${properties.getProperty("user")}&password=${properties.getProperty("password")}&ssl=true" +
                    "&sslfactory=org.postgresql.ssl.NonValidatingFactory")
        }
    }
}
