package client.command.execute.samples

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlin.reflect.KClass

@Serializable
data class ExecuteSample(
    val name: String,
    val type: ExecuteType,
    val typeOfArgs: List<KClass<out @Contextual Any>>? = null){


    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        if (name != other) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}