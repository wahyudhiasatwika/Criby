package com.criby

import org.jetbrains.exposed.sql.Database

object DatabaseFactory {
    fun init() {
        Database.connect(
            "jdbc:mariadb://localhost:3306/yourdatabase",
            driver = "org.mariadb.jdbc.Driver",
            user = "youruser",
            password = "yourpassword"
        )
    }
}
