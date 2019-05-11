package com.lukma.clean.data.common.exception.network

import org.json.JSONObject
import java.io.IOException

class ApiException(json: JSONObject) : IOException(
    if (json.has("error_message")) {
        json.getString("error_message")
    } else {
        "Unexpected error"
    }
)
