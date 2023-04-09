package com.taruns.herway.models

import java.io.Serializable

data class ResponseJson(
    val risk_scores: List<Double>,
    val routes: Routes
): Serializable