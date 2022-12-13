package com.frt.customer.repository.rest.responses


import com.google.gson.annotations.SerializedName
/* {"error":{"name":"InvalidOtp","message":"The OTP entered is incorrect.","code":203,"language_code":"LBL_INVALID_OTP"},"data":null,"status":"203"}*/
data class ErrorApiResponse(
    @SerializedName("data")
    var `data`: Any,
    @SerializedName("error")
    var error: Error,
    @SerializedName("status")
    var status: String
) {
    data class Error(
        @SerializedName("code")
        var code: Int,
        @SerializedName("language_code")
        var languageCode: String,
        @SerializedName("message")
        var message: String,
        @SerializedName("name")
        var name: String
    )
}