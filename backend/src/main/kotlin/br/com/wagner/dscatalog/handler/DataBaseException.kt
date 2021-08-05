package br.com.wagner.dscatalog.handler

import java.lang.RuntimeException

class DataBaseException(val msg: String): RuntimeException(msg) {
}