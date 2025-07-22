package com.okemwag.pulse.core.common.utils

object Constants {
    // Network
    const val NETWORK_TIMEOUT = 30L
    const val CACHE_SIZE = 10 * 1024 * 1024L // 10MB

    // Database
    const val DATABASE_NAME = "community_news_database"
    const val DATABASE_VERSION = 1

    // Blockchain
    const val POLYGON_MAINNET_CHAIN_ID = 137L
    const val POLYGON_MUMBAI_CHAIN_ID = 80001L

    // Preferences
    const val PREFS_NAME = "community_news_prefs"
    const val KEY_USER_TOKEN = "user_token"
    const val KEY_WALLET_ADDRESS = "wallet_address"
    const val KEY_FIRST_LAUNCH = "first_launch"

    // Content
    const val MAX_CONTENT_LENGTH = 5000
    const val MAX_IMAGES_PER_POST = 5
    const val IMAGE_COMPRESSION_QUALITY = 80

    // Pagination
    const val DEFAULT_PAGE_SIZE = 20
    const val PREFETCH_DISTANCE = 5

    // Tokens
    const val TOKEN_DECIMALS = 18
    const val MIN_TOKEN_REWARD = 1
    const val MAX_TOKEN_REWARD = 100
}

