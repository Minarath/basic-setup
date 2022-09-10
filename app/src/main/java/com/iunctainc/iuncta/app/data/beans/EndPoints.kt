package com.iunctainc.iuncta.app.data.beans

object EndPoints {

    object Auth {
        const val login = "login"
        const val getCategory1 = "category-1"


        const val register_psh = "register-for-push"
        const val delete_user_profile = "user-profile/{id}"
        const val updateProfile = "user-profile/update"
        const val setDefaultPaymentMethod = "profile-payment-default"
        const val deleteUserPaymentProfile = "user-payment/{id}"
        const val userProfilelist = "user-profile-list"
        const val userPaymentProfileWise = "user-payment"
        const val createUserProfile = "user-profile"
        const val changeSecurity = "change-password"
        const val setNotification = "save-notification"
        const val getNotificationData = "get-notification"
        const val sendUserHelp = "user-send-help"
        const val getHelplist = "help-list"
        const val setDefaultProfile = "save-default-profile"
        const val getDefaultProfile = "user-profile-list"
        const val verifyChangePhoneOtp = "verify-phone-otp"
        const val sendOtpToPhone = "send-phone-otp"
        const val changeNewEmail = "change-new-email"
        const val verifyOtpEmail = "verify-email-otp"
        const val sendEmail = "send-email-otp"
        const val change_username = "change-username"
        const val userInfor = "user-info"
        const val refresh_token = "refresh-token"
        const val register = "register"
        const val forgot = "forgotPassword"
        const val social_account = "social-account"
        const val update_profile = "user/update-profile"
        const val update_qr_code = "user/update-qr-code"
        const val user = "user/{user}"
        const val logout = "logout"
        const val resend = "resend-verify-email"
        const val resendOTP = "login-resend-otp"
        const val forgotPassword = "forgot-password"
        const val verifyOtp = "login-verify-otp"
        const val generate_key_token = "create-username"
        const val update_token = "get-update-token"
        const val associate_app_list = "associate-app-list"
        const val verify_email = "profile-send-verify-email"
        const val send_otp = "profile-send-phone-otp"
        const val verify_phn = "profile-verify-phone-otp"
        const val token_list = "list-of-token"
        const val notification_list = "notification-list"
        const val is_read_notification = "is-read"
        const val approve_request = "approve-request"
        const val transaction_history = "transaction-history"
        const val appUpdater = "force/update"
    }

    object ForgotPassword {
        const val forgot_password = "forgot-password"
        const val verify = "forgot-password/verify"
        const val reset = "forgot-password/reset"
    }

    object ChangePassword {
        const val change_password = "user/change-password"
        const val change_settings = "setting"
    }

}