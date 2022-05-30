package com.roque.rueda.gbm.coding.domain.error

data class ErrorResult(
    val code: Int = CODE_NO_CONNECTION,
    val message: String,
    val description: String = "",
    val cause: Throwable? = null
) {
    override fun toString(): String {
        return "ErrorResult [code: $code, description: $description, cause: $cause]\n" +
            cause?.stackTraceToString().orEmpty()
    }

    companion object {
        const val CODE_NO_CONNECTION = -1
    }
}