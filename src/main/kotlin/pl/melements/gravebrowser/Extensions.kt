package pl.melements.gravebrowser

import java.util.logging.Logger
import kotlin.reflect.full.companionObject

private val loggers: MutableMap<Any, Logger> = hashMapOf()

val <T : Any> T.log: Logger
    get() {
        if (!loggers.containsKey(this)) {
            val loggerName = unwrapCompanionClass(this.javaClass).name
            loggers[this] = Logger.getLogger(loggerName)
        }
        return loggers[this]!!
    }


fun <T : Any> unwrapCompanionClass(ofClass: Class<T>): Class<*> {
    return ofClass.enclosingClass?.takeIf {
        ofClass.enclosingClass.kotlin.companionObject?.java == ofClass
    } ?: ofClass
}
