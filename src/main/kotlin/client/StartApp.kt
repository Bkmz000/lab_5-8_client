package client

import client.command.processing.ExecutePacketBuilder
import client.command.packets.RequestPacket
import client.command.packets.RequestType

class StartApp {

    fun start(){
        println("Welcome to the CLI \"Product Collection\"")
        while(true) {
            val messageFromUser = readln()
            val commandPacket = ExecutePacketBuilder.getExecutePacket(messageFromUser)


            if(commandPacket != null) {
                val requestPacket = RequestPacket(RequestType.COMMAND_EXECUTE, commandPacket)
                println(requestPacket)
            } else {

                println("Unknown command")
            }
        }
    }

}