package consulting.hippone.quiz

import arrow.core.*
import arrow.core.mapOrAccumulate
import arrow.core.Either.Companion.zipOrAccumulate

sealed interface ValidationErrors {

}


sealed interface ValidationError

sealed interface InvalidField {
    val errors: NonEmptyList<String>
    val field: String
}

data class InvalidInput(val invalidFields: NonEmptyList<InvalidField>): ValidationError

data class InvalidPrompt(override val errors: NonEmptyList<String>): InvalidField {
    override val field: String = "prompt"
}

data class InvalidAnswer(override val errors: NonEmptyList<String>): InvalidField {
    override val field: String = "answer"
}

data class InvalidAnswerOption(override val errors: NonEmptyList<String>): InvalidField {
    override val field: String = "answerOption"
}

data class InvalidAnswerOptions(override val errors: NonEmptyList<String>): InvalidField {
    override val field: String = "answerOptions"
}

fun QuestionForm.validate(): Either<InvalidInput, QuestionForm> =
    zipOrAccumulate(
        prompt.validPrompt(),
        validaAnswerOptions(answerOptions).map{ it.toList() }
    ) { _, _-> QuestionForm(prompt, answerOptions) }.mapLeft(::InvalidInput)

private fun String.validPrompt(): EitherNel<InvalidPrompt, String> {
    val trimmed = trim()
    return zipOrAccumulate(
        trimmed.notBlank(),
        trimmed.minSize(8),
        trimmed.maxSize(200)
    ) { a, _, _ -> a }.mapLeft(toInvalidField(::InvalidPrompt))
}

private fun String.validAnswer(): EitherNel<InvalidAnswer, String> {
    val trimmed = trim()
    return zipOrAccumulate(
        trimmed.notBlank(),
        trimmed.minSize(8),
        trimmed.maxSize(200)
    ) { a, _, _ -> a }.mapLeft(toInvalidField(::InvalidAnswer))
}

internal fun <A: InvalidField> toInvalidField(
    transform: (NonEmptyList<String>) -> A
): (NonEmptyList<String>) -> NonEmptyList<A> = { nel -> nonEmptyListOf(transform(nel)) }

internal fun String.maxSize(size: Int): EitherNel<String, String> =
    if (length <= size) right() else "is too short (minimum is $size characters)".leftNel()

internal fun String.minSize(size: Int) =
    if (length >= size) right() else "is too short (minimum is $size characters)".leftNel()

internal fun String.notBlank(): EitherNel<String, String> =
    if (isNotBlank()) right() else "Cannot be blank".leftNel()

private fun validaAnswerOptions(answerOptions: List<AnswerOptionForm>): EitherNel<InvalidAnswerOption, Set<AnswerOptionForm>> =
    answerOptions.mapOrAccumulate { answerOption ->
        answerOption.answer.validAnswer().map { AnswerOptionForm(it) }.bindNel()
    }.mapLeft { invalidAnswers -> invalidAnswers.map { InvalidAnswerOption(it.errors) } }.map { it.toSet() }