package consulting.hippone

import consulting.hippone.dashboard.dashboardRoutes
import consulting.hippone.quiz.questionRoute
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.resources.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json

fun Application.configure() {
//    install(FreeMarker) {
//        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
//        outputFormat = HTMLOutputFormat.INSTANCE
//    }
    install(Resources)
    install(ContentNegotiation) {
        json(
            Json {
                isLenient = true
                ignoreUnknownKeys = true
            }
        )
    }
}

fun Application.routes() {
    routing {
        dashboardRoutes()
//        questionRoute()
    }
}

fun Application.examinr() {
    configure()
    routes()
}

data class User(val id: Int, val name: String)