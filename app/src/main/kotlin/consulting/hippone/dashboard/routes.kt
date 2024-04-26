package consulting.hippone.dashboard

import consulting.hippone.User
import consulting.hippone.config.Dashboard
import consulting.hippone.shared.RootTemplate
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.resources.*
import io.ktor.server.routing.*
import kotlinx.html.*


fun Routing.dashboardRoutes() {
    get<Dashboard> {
        val sampleUser = User(1, "Cherif")
        val title = "Dashboard"
        val app = this@dashboardRoutes.application
        call.respondHtmlTemplate(RootTemplate(title, app)) {
            contentBlock {
                app.apply {
                    p { +"Dashboard content" }
                    val link: String = "/"
                    a(link) { +"Back" }
                }
            }
        }
    }
}