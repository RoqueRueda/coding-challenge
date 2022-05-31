package com.roque.rueda.gbm.coding.domain.error

import java.io.IOException

class NoConnectivityException(
    message: String = "No network available, please check your WiFi or Data connection"
) : RuntimeException(message)