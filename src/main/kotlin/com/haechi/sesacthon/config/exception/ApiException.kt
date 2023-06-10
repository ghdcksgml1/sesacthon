package com.haechi.sesacthon.config.exception

open class ApiException : RuntimeException {

    constructor(message: String) : super(message)
}