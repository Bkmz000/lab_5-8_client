package client.command.packets

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class RequestPacket (
    val requestType: RequestType,
    val args: @Contextual Any? = null,
    )