package com.criby

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.mindrot.jbcrypt.BCrypt

fun Route.userRoutes() {
    route("/register") {
        post {
            val register = call.receive<UserRegister>()
            transaction {
                val hashedPassword = BCrypt.hashpw(register.password, BCrypt.gensalt())
                Users.insert {
                    it[username] = register.username
                    it[email] = register.email
                    it[password] = hashedPassword
                }
            }
            call.respond(HttpStatusCode.Created, "User registered successfully")
        }
    }

    route("/login") {
        post {
            val login = call.receive<UserLogin>()
            val user = transaction {
                Users.select { Users.email eq login.email }.singleOrNull()
            }
            if (user != null && BCrypt.checkpw(login.password, user[Users.password])) {
                call.respond(HttpStatusCode.OK, "Login successful")
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Invalid credentials")
            }
        }
    }

    route("/reset_password") {
        post {
            val request = call.receive<PasswordResetRequest>()
            val user = transaction {
                Users.select { Users.email eq request.email }.singleOrNull()
            }
            if (user != null) {
                val resetToken = java.util.UUID.randomUUID().toString()
                transaction {
                    Users.update({ Users.email eq request.email }) {
                        it[Users.resetToken] = resetToken
                    }
                }
                // Send email with reset token
                // sendResetEmail(user[Users.email], resetToken)
                call.respond(HttpStatusCode.OK, "Reset email sent")
            } else {
                call.respond(HttpStatusCode.NotFound, "Email not found")
            }
        }
    }
}
