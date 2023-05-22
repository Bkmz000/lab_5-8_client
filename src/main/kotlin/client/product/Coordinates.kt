package client.product

import kotlinx.serialization.Serializable

@Serializable
class Coordinates {

    val x: Int
    val y: Double

    constructor(x:Int, y:Double){
        this.x = x
        this.y = y
    }

    override fun toString(): String {
        return "Coordinates(x=$x, y=$y)"
    }


}