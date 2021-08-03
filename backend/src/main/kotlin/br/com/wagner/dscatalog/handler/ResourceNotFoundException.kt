package br.com.wagner.dscatalog.handler

import java.lang.RuntimeException

class ResourceNotFoundException(val msg: String): RuntimeException(msg) {
}