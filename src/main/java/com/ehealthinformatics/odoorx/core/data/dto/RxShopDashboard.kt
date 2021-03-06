package com.ehealthinformatics.odoorx.core.data.dto


data class RxShopDashboard(var user: User, var noOfCustomers: String, var noOfOrders: String, var noOfSessions: String, var noOfProducts: String, var totalPayments: String
                           , var posSession: PosSession, var totalAmount: String, var openingDate: String?, var openingBalance: String, var closingBalance: String)