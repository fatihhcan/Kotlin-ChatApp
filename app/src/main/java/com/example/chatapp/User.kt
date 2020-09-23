package com.example.chatapp

class User {
    var name: String? = null
    var phone: String? = null
    var image_profile: String? = null
    var level: String? = null
    var user_id: String? = null

    constructor(
        name: String?,
        phone: String?,
        image_profile: String?,
        level: String?,
        user_id: String?
    ) {
        this.name = name
        this.phone = phone
        this.image_profile = image_profile
        this.level = level
        this.user_id = user_id
    }

    constructor() {}
}