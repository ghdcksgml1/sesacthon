package com.haechi.sesacthon.utils

data class PhoneNumberValid(
    val phoneNumber: String
) {
    companion object {
        fun valid(phoneNumber: String): String {
            val numberOnly = phoneNumber.replace("\\D".toRegex(), "")

            // 숫자가 11자리일 경우 010-0000-0000 형식으로 변환
            if (numberOnly.length == 11) {
                return numberOnly.replace("(\\d{3})(\\d{4})(\\d{4})".toRegex(), "$1-$2-$3")
            }

            // 그 외의 경우는 입력된 전화번호 그대로 반환
            return phoneNumber
        }
    }

}