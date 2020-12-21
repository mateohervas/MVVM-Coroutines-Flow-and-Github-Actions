package com.shadows.bitsodream.data.remote.model

data class BaseResponse<T>(val success: Boolean, val payload: T?, val error: ErrorResponse?)