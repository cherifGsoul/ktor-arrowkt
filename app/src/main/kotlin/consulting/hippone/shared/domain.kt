package consulting.hippone.shared

sealed interface DomainError

@JvmInline
value class NonEmptyString private constructor(val s: String)