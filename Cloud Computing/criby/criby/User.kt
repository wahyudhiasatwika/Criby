package com.criby

import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.Column

object Users : IntIdTable() {
    val username: Column<String> = varchar("username", 50).uniqueIndex()
    val email: Column<String> = varchar("email", 50).uniqueIndex()
    val password: Column<String> = varchar("password", 64)
    val resetToken: Column<String?> = varchar("reset_token", 100).nullable()
}
