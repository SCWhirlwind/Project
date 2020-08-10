package com.example.projectmobile

class LikeGames {
    var key : String?
    val userId : String?
    val dataID : String?

    constructor() {
        key = null
        userId = null
        dataID = null
    }

    constructor(key: String?, userId: String?, businessID: String?) {
        this.key = key
        this.userId = userId
        this.dataID = businessID
    }
}