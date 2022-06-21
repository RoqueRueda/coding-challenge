package com.roque.rueda.gbm.coding.domain.error

data class ErrorResult(
    val code: Int = UNKNOWN_ERROR,
    val message: String,
    val description: String = "",
    val cause: Throwable? = null
) {
    override fun toString(): String {
        return "ErrorResult [code: $code, description: $description, cause: $cause]\n" +
            cause?.stackTraceToString().orEmpty()
    }

    companion object {
        const val NO_CONNECTION = -1
        const val UNKNOWN_ERROR = 0
        const val NO_BIOMETRICS = 900
    }
}