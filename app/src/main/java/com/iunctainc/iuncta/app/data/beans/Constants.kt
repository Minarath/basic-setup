package com.iunctainc.iuncta.app.data.beans

import com.iunctainc.iuncta.app.BuildConfig

object Constants {

    object Updater {
        val ANDROID_KEY = "android"
    }


    object Token {
        val tokenList = ArrayList<String>()
        val key = ""
    }

    object PlaceApi {
        //        const val KEY = "AIzaSyBEJBrJz2GsQWFq7PksOubywpHsdGRomRw"
        const val KEY = "AIzaSyCp4AoKOCqMtT2Md_BsYtjqx0kA_TKQopI"
    }

    object Menu {
        const val HOME = "HOME"
        const val ABOUT = "About"
        const val SHOP = "Shop"
        const val SETTINGS = "Settings"
        const val LOG_OUT = "Log out"
    }

    const val DELETE_USER_PROFILE = 1
    const val PROFILE_ADDED = 2
    const val HOME = 1
    const val NOTIFICATION = 2
    const val KEY = 3
    const val WALLET = 4
    const val SETTING = 5

    object HomeTabs {
        const val SHOP = "Shop"
        const val SETTINGS = "Settings"
        const val LOG_OUT = "Log out"
    }

    object PrefsKeys {
        const val USER_ID = "USER_ID"
        const val USER_DATA = "USER_DATA"
        const val isFirstTime = "isFirstTime"
        const val AUTHENTICATION = "AUTHENTICATION"
        const val ISLOGIN = "ISLOGIN"
    }

    object Gender {
        const val GENDER_MALE = 0
        const val GENDER_FEMALE = 1
        const val strMale = "Male"
        const val strFemale = "Female"
    }

    object RequestCodes {
        const val ContactReqCode = 100
    }

    object Extra {
        const val DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm:ss"
    }

    object URLs {
        const val BaseUrl = BuildConfig.BASE_URL
    }

    object RequestParm {
        const val TYPE = "type"
        const val PROFILE_NAME = "profile_name"
        const val NAME = "name"
        const val FULL_NAME = "full_name"
        const val EMAIL = "email"
        const val COUNTRY_CODE = "country_code"
        const val PHONE_NUMBER = "phone_number"
        const val WEBSITE = "website"
        const val GENDER = "gender"
        const val ADDRESS = "address"
        const val COUNTRY = "country"
        const val STATE = "state"
        const val CITY = "city"
        const val COMPANY_NAME = "company_name"
        const val POSITION = "position"
        const val UNIT = "unit"
        const val PROFILE_PICTURE = "profile_picture"
        const val USER_PROFILE_ID = "user_profile_id"
        const val BIRTH_DATE = "birthdate"
    }

}