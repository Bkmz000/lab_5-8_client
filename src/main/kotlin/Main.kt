import client.StartApp
import client.modules.allModules
import com.sun.org.apache.bcel.internal.generic.INSTANCEOF
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.inject
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor


fun main(){
    startKoin {
        modules(allModules)
    }

    val app by inject<StartApp>(StartApp::class.java)
    app.start()


}