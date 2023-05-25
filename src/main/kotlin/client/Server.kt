package client

import client.command.execute.samples.ExecuteSample
import client.command.execute.samples.ExecuteType
import client.command.packets.RequestPacket
import client.command.packets.RequestType
import com.sun.jmx.remote.protocol.rmi.ServerProvider
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.encodeToString
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.encodeToJsonElement
import sun.nio.ch.DatagramSocketAdaptor
import java.net.DatagramPacket
import java.net.DatagramSocketImpl
import java.net.InetSocketAddress
import java.net.MulticastSocket
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel
import java.nio.channels.Selector
import java.nio.channels.ServerSocketChannel

class Server(private val address: InetSocketAddress) {


    fun start(){

        val datagramChannel = DatagramChannel.open()

        while (true){
            readln()
            val a = Json.encodeToString(RequestPacket(RequestType.COMMAND_EXECUTE, ExecuteSample("show", ExecuteType.NON_ARGUMENT)))
            val buffer = ByteBuffer.wrap(a.toByteArray())



            datagramChannel.send(buffer,address)

        }

    }


}