package com.bsk.parentt.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object Constants {

    var BASE_URL = "https://api.bsk.edu.kw/api/"
    const val cameraZoomLavel15_0_f = 15.0f

    const val android = "android"

    //Fiscal Period
    var parentFiscalPeriod = ""


    //StaticWebpages
    var HOW_TO_SIGNIN_URL = ""
    var CONTACT_DETAIL_URL = ""
    var BUS_SERVICE_URL = ""
    var ROYAL_SCOT_URL = ""
    var TERM_DATES_URL = ""
    var SHOP_URL = "https://royalscot.bsk.edu.kw/profile/login"
    var TERMS_CONDITIONS_URL = ""
    var PRIVACY_POLICY_URL = ""
    var BSK_CAREER_URL = ""
    var RECRUITERMENT_URL = ""
    var PAYMENT_TERMS_URL = ""
    var RE_REGISTRATION_TC_URL = ""


    var _error: MutableLiveData<String> = MutableLiveData()
    var error: LiveData<String> = _error
    const val SHOW_LOADER = "showloader"


    //=================API Request prms====================


    object HTTP_STATUS {
        const val OK = "200"
        const val NOTFOUND = "404"
        const val ALREADY_EXIST = "409"

    }


    object Param {
        const val user_type = "user_type"
        const val SUCCESS = "SUCCESS"
        const val ERROR = "ERROR"
        const val email_address = "email_address"
        const val password = "password"
        const val device_id = "device_id"
        const val device_type = "device_type"
        const val device_token = "device_token"
        const val device_name = "device_name"
        const val type = "type"
        const val sub_type = "sub_type"
        const val user_id = "user_id"
        const val p_code = "p_code"
        const val contact_about = "contact_about"
        const val message = "message"
        const val mostly_use = "mostly_use"
        const val rating = "rating"
        const val old_password = "old_password"
        const val new_password = "new_password"
        const val field_name = "field_name"
        const val field_val = "field_val"
        const val id = "id"
        const val PEmail = "PEmail"
        const val busservice = "busservice"
        const val admissionnumberforbusservice = "admissionnumberforbusservice"
        const val name = "name"
        const val code = "code"
        const val vehicle_maker_id = "vehicle_maker_id"
        const val parent_vehicle_id = "parent_vehicle_id"
        const val nickname = "nickname"
        const val licence_plate_number = "licence_plate_number"
        const val registration_country = "registration_country"
        const val make = "make"
        const val model = "model"
        const val color = "color"
        const val driver_phone_number = "driver_phone_number"
        const val driver_email = "driver_email"
        const val driver_name = "driver_name"
        const val driver_relationship = "driver_relationship"
        const val reason_for_report = "reason_for_report"
        const val property_type = "property_type"
        const val item_type = "item_type"
        const val item_color = "item_color"
        const val description = "description"
        const val photo = "photo"
        const val lost_property_id = "lost_property_id"
        const val cms_code = "cms_code"
    }


    object Preference {
        const val LANGUAGE_ID = "LANGUAGE_ID"
        const val LANGUAGE_TITLE = "LANGUAGE_TITLE"
        const val COUNTRY_ID = "COUNTRY_ID"
        const val CURRENCY_ID = "CURRENCY_ID"
        const val LANGUAGE_CODE = "LANGUAGE_CODE"
        const val FILEPATH = "FILEPATH"
    }


}