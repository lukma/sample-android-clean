package com.lukma.android.data.common.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ListDataResponse<T>(
    @JsonProperty("data")
    val data: List<T>?
)
