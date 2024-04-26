package consulting.hippone.quiz

import arrow.core.Either
import arrow.core.Either.Companion.zipOrAccumulate
import arrow.core.raise.either
import java.util.*

data class CreateQuestionCommand(val prompt: String, val answerOptions: List<CreateAnswerOptionCommand>)

data class CreateAnswerOptionCommand(val answer: String, val isCorrect: Boolean)

interface QuizService {
    fun createQuestion(command: CreateQuestionCommand): Either<ValidationError, QuestionId>
}

fun quizService(): QuizService = object : QuizService {
    override fun createQuestion(command: CreateQuestionCommand): Either<ValidationError, QuestionId> = either {
        val question = zipOrAccumulate(
            QuestionPrompt(prompt = command.prompt),
            AnswerOptions(options = command.answerOptions.map { AnswerOption(it.answer, it.isCorrect) })
        ){ qPrompt: QuestionPrompt, options: AnswerOptions ->
            Question(id = QuestionId(UUID.randomUUID()), prompt = qPrompt, answerOptions = options)
        }.mapLeft(::InvalidInput).bind()
        println("==================== $question ===================")
        question.id
    }
}