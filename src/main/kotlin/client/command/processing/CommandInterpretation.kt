package client.command.processing

import client.command.*
import client.command.processing.RequestType.COMMAND_EXECUTE
import client.command.`object`.ProductBuilderCLI
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.File
import kotlin.reflect.*

object CommandInterpretation : KoinComponent {

    private val allCommandSamples by inject<AllCommandSamples>()

    fun getCommandPacket(message: String) : CommandPacket?{

        val listOfWords = message.toListWithoutBlanks() ?: return null
        val commandName = listOfWords.removeFirst()
        val commandSample = allCommandSamples.samples
                                                                    .filter { it.equals(commandName) }
                                                                    .let { if(it.isNotEmpty()) it[0] else return null }

        val commandPacket = when(commandSample.type) {
            CommandType.ARGUMENT -> getArgumentCommandPacket(commandSample, listOfWords)
            CommandType.OBJECT -> getObjectCommandPacket(commandSample, listOfWords)
            CommandType.NON_ARGUMENT -> getNonArgumentCommandPacket(commandSample, listOfWords)
            CommandType.SCRIPT -> getScriptCommandPacket(commandSample, listOfWords)
        }

        return commandPacket



    }

    private fun String.toListWithoutBlanks() : MutableList<String>? {

        this.ifEmpty { null }
        val listOfWords = this.split(" ").toMutableList()
        listOfWords.removeAll { this.isBlank() }
        return listOfWords.ifEmpty { null }

    }


    private fun getNonArgumentCommandPacket(commandSample: CommandSample, listOfWords: MutableList<String>) : CommandPacket?{
        return if(listOfWords.isEmpty()) CommandPacket(COMMAND_EXECUTE, commandSample.name) else null
    }

    private fun getArgumentCommandPacket(commandSample: CommandSample, possibleArgs: MutableList<String>) : CommandPacket? {

        val commandArgs = possibleArgs.tryCastListAs(commandSample.typeOfArgs!!) ?: return null

        return  CommandPacket(COMMAND_EXECUTE, commandSample.name, commandArgs)

    }

    private fun getObjectCommandPacket(commandSample: CommandSample, possibleArgs: MutableList<String>) : CommandPacket? {
        val commandArg = possibleArgs.tryCastListAs(commandSample.typeOfArgs!!) ?: return null

        val product = ProductBuilderCLI().build() ?: return null

        return CommandPacket(COMMAND_EXECUTE, commandSample.name, commandArg, product)
    }

    private fun getScriptCommandPacket(commandSample: CommandSample, possibleFileName: MutableList<String>) : CommandPacket? {
        val fileName = possibleFileName[0]
        val fileWithScript = File(fileName).also { if (!it.isFile) return null }
        val linesFromFile = fileWithScript.bufferedReader().readLines()

        return CommandPacket(COMMAND_EXECUTE, commandSample.name, linesFromFile)
    }





    private fun MutableList<String>.tryCastListAs(typeOfArgs: List<KClass<out Any>>) : MutableList<Any>?{
        val possibleArgs = this
        if(typeOfArgs.size != possibleArgs.size) return null

        val commandArgs = mutableListOf<Any>()
        typeOfArgs.forEachIndexed { index, typeOfArg ->
            val argument = possibleArgs[index].tryCastStringAs(typeOfArg) ?: return null
            commandArgs.add(argument)
        }

        return commandArgs
    }


    private fun String.tryCastStringAs(type: KClass<out Any>) : Any? {
        return when(type) {
            Int::class -> this.toIntOrNull()
            Double::class -> this.toDoubleOrNull()
            String::class -> this
            else -> return null
        }
    }

}