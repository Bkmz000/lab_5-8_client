package client

import client.command.processing.CommandInterpretation

class StartApp {

    fun start(){
        println("Welcome to the CLI \"Product Collection\"")
        while(true) {
            val messageFromUser = readln()
            val commandPacket = CommandInterpretation.getCommandPacket(messageFromUser)

            if(commandPacket != null) {
                println(commandPacket)
            } else {
                println("Unknown command")
            }
        }
    }

}