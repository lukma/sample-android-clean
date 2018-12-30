package com.lukma.clean.data.content.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class GetContentsResponse(
    @JsonProperty("data")
    val data: List<DataResponse>
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class DataResponse(
        @JsonProperty("id")
        val id: String,
        @JsonProperty("title")
        val title: String,
        @JsonProperty("thumbnail")
        val thumbnail: String,
        @JsonProperty("content")
        val content: String
    )
}