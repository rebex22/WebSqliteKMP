package com.example.sqldelight.hockey.platform

import kotlin.js.Date

@JsModule("dateformat")
external fun dateFormat(date: Date, format: String): String
