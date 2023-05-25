package client.command.processing

import client.command.packets.ExecutePacket
import client.command.execute.samples.AllExecuteSamples
import client.command.execute.samples.ExecuteSample
import client.command.execute.samples.ExecuteType
import client.product.ProductBuilderCLI
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.File
import kotlin.reflect.*

object ExecutePacketBuilder : KoinComponent {

    private val allCommandSamples by inject<AllExecuteSamples>()

    fun getExecutePacket(message: String) : ExecutePacket?{

        val listOfWords = message.toListWithoutBlanks() ?: return null
        val commandName = listOfWords.removeFirst()
        val commandSample = allCommandSamples.samples
                                            .filter { it.equals(commandName) }
                                            .let { if(it.isNotEmpty()) it[0] else return null }

        val executeCommandPacket = when(commandSample.type) {
            ExecuteType.ARGUMENT -> getArgumentCommandPacket(commandSample, listOfWords)
            ExecuteType.OBJECT -> getObjectCommandPacket(commandSample, listOfWords)
            ExecuteType.NON_ARGUMENT -> getNonArgumentCommandPacket(commandSample, listOfWords)
            ExecuteType.SCRIPT -> getScriptCommandPacket(commandSample, listOfWords)
        }

        return executeCommandPacket



    }

    private fun String.toListWithoutBlanks() : MutableList<String>? {

        this.ifEmpty { null }
        val listOfWords = this.split(" ").toMutableList()
        listOfWords.removeAll { this.isBlank() }
        return listOfWords.ifEmpty { null }

    }


    private fun getNonArgumentCommandPacket(executeSample: ExecuteSample, listOfWords: MutableList<String>) : ExecutePacket?{
        return if(listOfWords.isEmpty()) ExecutePacket(executeSample) else null
    }

    private fun getArgumentCommandPacket(executeSample: ExecuteSample, possibleArgs: MutableList<String>) : ExecutePacket? {
        val commandArgs = possibleArgs.tryCastListAs(executeSample.typeOfArgs!!) ?: return null

        return ExecutePacket(executeSample, commandArgs)

    }

    private fun getObjectCommandPacket(executeSample: ExecuteSample, possibleArgs: MutableList<String>) : ExecutePacket? {
        val commandArg = possibleArgs.tryCastListAs(executeSample.typeOfArgs!!) ?: return null

        val product = ProductBuilderCLI().build() ?: return null
        commandArg.add(product)

        return ExecutePacket(executeSample, commandArg)
    }

    private fun getScriptCommandPacket(executeSample: ExecuteSample, possibleFileName: MutableList<String>) : ExecutePacket? {
        val fileName = possibleFileName[0]
        val fileWithScript = File(fileName).also { if (!it.isFile) return null }
        val linesFromFile = fileWithScript.bufferedReader().readLines().toMutableList()

        return ExecutePacket(executeSample, linesFromFile)
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