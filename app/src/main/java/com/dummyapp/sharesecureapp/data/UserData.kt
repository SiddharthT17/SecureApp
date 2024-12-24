package com.dummyapp.sharesecureapp.data

// Data Models
data class UserData(
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var password: String = "",
    var state: String = "",
    var shareCode: String = "",
    var bankAccount: String = "",
    var routingNumber: String = ""
)

data class BusinessData(
    var companyName: String = "",
    var fleetDetails: String = ""
)

data class TruckType(
    val name: String,  // Name of the truck (e.g., "Ten-footer")
    val size: String,  // Truck size (e.g., "10-foot")
    var count: Int = 0 // Number of trucks
)
