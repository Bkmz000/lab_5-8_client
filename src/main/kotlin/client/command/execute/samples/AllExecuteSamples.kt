package client.command.execute.samples

import client.command.execute.samples.ExecuteType.*


class AllExecuteSamples {
    val samples = mutableListOf<ExecuteSample>()

    init{
        addSample(ExecuteSample("insert", OBJECT, listOf(Int::class)))
        addSample(ExecuteSample("remove", ARGUMENT, listOf(Int::class)))
        addSample(ExecuteSample("show", NON_ARGUMENT))
        addSample(ExecuteSample("execute_script", SCRIPT, listOf(String::class)))
    }

    fun addSample(executeSample: ExecuteSample) {
        if(executeSample.type != NON_ARGUMENT && executeSample.typeOfArgs == null) return
        samples.add(executeSample)
    }
}