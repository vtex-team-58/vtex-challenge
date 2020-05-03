package com.twilio.utils

import java.io.FileInputStream
import java.sql.Connection
import java.sql.DriverManager
import java.util.*

class DataBaseConnection {

    companion object {
        fun getConnection(): Connection {
            return DriverManager.getConnection(System.getenv("JDBC_CONNECTION"))
        }
    }
}
