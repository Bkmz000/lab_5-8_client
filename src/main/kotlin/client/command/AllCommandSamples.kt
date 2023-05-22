package client.command

import client.command.processing.CommandType.*
import client.command.processing.CommandSample


class AllCommandSamples {
    val samples = mutableListOf<CommandSample>()

    init{
        addSample(CommandSample("insert", OBJECT, listOf(Int::class)))
        addSample(CommandSample("remove", ARGUMENT, listOf(Int::class)))
        addSample(CommandSample("show", NON_ARGUMENT))
        addSample(CommandSample("execute_script", SCRIPT, listOf(String::class)))
    }

    fun addSample(commandSample: CommandSample) {
        if(commandSample.type != NON_ARGUMENT && commandSample.typeOfArgs == null) return
        samples.add(commandSample)
    }
}