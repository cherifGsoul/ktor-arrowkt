package consulting.hippone.quiz

import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable data class QuestionForm(
    val prompt: String,
    val answerOptions: List<AnswerOptionForm>
)

@Serializable data class AnswerOptionForm(val answer: String, val isCorrect: Boolean = false)

fun Routing.questionRoute() {
//    route("/questions") {
//        get {
//            call.respond(FreeMarkerContent("questions/index.ftl", null))
//        }
//        route("/create") {
//            get {
//                call.respond(FreeMarkerContent("questions/create.ftl", null))
//            }
//
//            post {
//                val res = either {
//                    val newQuestion = call.receive<QuestionForm>()
//                    val service = quizService()
//                    service.createQuestion(command = newQuestion.toCommand()).bind()
//                }
//                when(res) {
//                    is Either.Left -> call.respondText("Errors ${res.value}")
//                    is Either.Right -> call.respondRedirect("/questions")
//                }
//
//            }
//        }
//        route("/update/{id}") {
//            get{ }
//            post {  }
//        }
//        route("/delete/{id}") {
//            post {
//
//            }
//        }
//
//    }
}

private fun QuestionForm.toCommand(): CreateQuestionCommand  = CreateQuestionCommand(
    prompt = prompt,
    answerOptions = answerOptions.map { CreateAnswerOptionCommand(it.answer, it.isCorrect) }
)