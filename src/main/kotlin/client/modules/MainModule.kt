package client.modules

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import client.StartApp
import client.command.AllCommandSamples

val commandModule = module {
    singleOf(::AllCommandSamples)

}


val collectionModule = module {

}

val appModule = module {
    singleOf(::StartApp)
}


val allModules = module {
    includes(collectionModule, appModule, commandModule)
}