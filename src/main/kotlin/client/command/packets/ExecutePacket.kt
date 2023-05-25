package client.command.packets

import client.command.execute.samples.ExecuteSample
import client.product.Product
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class ExecutePacket(
    val executeSample: ExecuteSample,
    val listOfArgs: MutableList<out @Contextual Any>? = null,
    )