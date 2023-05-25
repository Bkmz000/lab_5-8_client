package client.command.packets

import client.command.execute.samples.ExecuteSample
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class RequestPacket (
    val requestType: RequestType,
    val args: List<ExecuteSample>? = null,
    ){
    constructor(requestType: RequestType, executeSample: ExecuteSample ) : this(requestType, mutableListOf(executeSample))

}