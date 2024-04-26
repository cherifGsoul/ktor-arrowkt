package consulting.hippone.shared

import consulting.hippone.config.Dashboard
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.resources.*
import kotlinx.html.*

class RootTemplate(private val pageTitle: String, private val app: Application) : Template<HTML> {
    val contentBlock = Placeholder<FlowContent>()
    override fun HTML.apply() {

        head {
            title("Projectr App::$pageTitle")
            //if development
            script {
                type = "module"
                src = "http://localhost:5173/@vite/client"
            }
            script {
                type = "module"
                src = "http://localhost:5173/assets/js/main.js"
            }
        }
        body("has-navbar-fixed-top") {
            this@RootTemplate.app.apply {
                div("is-0 is-variable columns") {
                    aside("menu p-4") {
                        id = "sidebar"
                        ul {
                            classes = setOf("menu-list")
                            li {
                                val link = href(Dashboard)
                                a(link) {
                                    +"Dashboard"
                                }
                            }
                            li {
                                a {
                                    href = "/projects"
                                    +"Projects"
                                }
                            }
                        }
                    }
                }
            }
            main {
                id = "main"
                classes = setOf("column", "is-offset-2", "is-10", "px-4")
                insert(contentBlock)
            }
        }
    }
}