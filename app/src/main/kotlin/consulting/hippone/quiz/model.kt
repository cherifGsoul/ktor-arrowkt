package consulting.hippone.quiz

import arrow.core.Either.Companion.zipOrAccumulate
import arrow.core.EitherNel
import arrow.core.mapOrAccumulate
import java.util.UUID


@JvmInline value class QuestionId (val id: UUID)

@JvmInline value class QuestionPrompt private constructor(val prompt: String) {
    companion object {
        operator fun invoke(prompt: String): EitherNel<InvalidPrompt, QuestionPrompt> {
            return zipOrAccumulate(
                prompt.notBlank(),
                prompt.minSize(8),
                prompt.maxSize(200)
            ){a, _ , _ ->
                QuestionPrompt(a)
            }.mapLeft(toInvalidField(::InvalidPrompt))
        }
    }
}

data class Question(val id: QuestionId, val prompt: QuestionPrompt, val answerOptions: AnswerOptions)

data class AnswerOption(val answer: String, val isCorrect: Boolean = false)

@JvmInline value class AnswerOptions private constructor(val options: List<AnswerOption>) {
    companion object {
        operator fun invoke(options: List<AnswerOption>): EitherNel<InvalidAnswerOptions, AnswerOptions> {
            return options.mapOrAccumulate{ o ->
                zipOrAccumulate(
                    o.answer.notBlank(),
                    o.answer.minSize(8),
                    o.answer.maxSize(200)
                ){_, _,_ -> o}.bindNel()
            }.mapLeft(toInvalidField(::InvalidAnswerOptions)).map(::AnswerOptions)
        }
    }
}