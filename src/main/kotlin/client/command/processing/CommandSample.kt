package client.command.processing

import kotlin.reflect.KClass

data class CommandSample(
    val name: String,
    val type: CommandType,
    val typeOfArgs: List<KClass<out Any>>? = null){


    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        if (name != other) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}