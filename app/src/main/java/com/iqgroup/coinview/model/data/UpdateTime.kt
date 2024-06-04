package com.iqgroup.coinview.model.data

import com.fasterxml.jackson.annotation.JsonProperty

@Suppress("SpellCheckingInspection")
data class UpdateTime(
    val updated: String,
    val updatedISO: String,

    @JsonProperty("updateduk")
    val updatedUK: String

)
