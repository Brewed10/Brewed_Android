package com.ssafy.smartstore.src.main.dto

import java.util.*

data class Order (
    var id: Int,
    var userId: String,
    var storeId: Int,
    var orderTable: String,
    var orderTime: String,
    var complited: String,
    val details: ArrayList<OrderDetail> = ArrayList() ){

    var totalQnanty:Int = 0
    var totalPrice:Int = 0
    var topProductName:String = ""
    var topImg:String = ""

    constructor(userId: String, storeId: Int, orderTable: String, complited: String, details: ArrayList<OrderDetail>)
            :this(0, userId, storeId, orderTable,"",complited, details)
    constructor():this(0,"",1, "","","", arrayListOf())
}
