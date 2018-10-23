package com.lukma.clean.data.exception

import org.json.JSONObject

class ApiException(json: JSONObject) : Exception(if (json.has("error_message")) {
    json.getString("error_message")
} else {
    "Unexpected error"
})