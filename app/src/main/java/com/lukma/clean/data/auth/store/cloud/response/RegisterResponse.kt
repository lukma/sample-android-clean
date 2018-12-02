package com.lukma.clean.data.auth.store.cloud.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class RegisterResponse(
    @JsonProperty("data")
    val id: String
)