package com.lukma.clean.data.content.store.cloud.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class GetContentsResponse(
        @JsonProperty("data")
        val data: List<DataResponse>
)