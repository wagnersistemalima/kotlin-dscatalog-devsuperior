package br.com.wagner.dscatalog.handler

import java.lang.RuntimeException

class ExceptionGenericValidated(val msg: String): RuntimeException(msg) {
}