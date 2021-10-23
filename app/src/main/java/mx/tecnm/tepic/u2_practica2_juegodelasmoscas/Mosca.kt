package mx.tecnm.tepic.u2_practica2_juegodelasmoscas

import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint

class Mosca(m: Lienzo) {
    var p = m
    var moscaX = 0f
    var moscaY = 0f
    var imagen = BitmapFactory.decodeResource(m.resources, R.drawable.mosca_2)
    var vidaMosca = true
    /*
    * Tamaño de pantalla: 650X1350
    * */

    init {
        moscaX = (Math.random()*650).toFloat()
        moscaY = (Math.random()*1300).toFloat()
    }

    fun pintar(c: Canvas){
        c.drawBitmap(imagen,moscaX, moscaY, Paint())
    }

    fun moverMosca(){
            var seleccion = (Math.random()*4).toInt()
            //println(seleccion)
            when(seleccion){
                0 -> {
                    //derecha
                    moscaX += ((Math.random()*15)+1).toFloat()

                    if (moscaX >= 600) moscaX -= ((Math.random()*15)+1).toFloat()
                    if (moscaX <= 0) moscaX += ((Math.random()*15)+1).toFloat()
                }
                1 -> {
                    //izquierda
                    moscaX -= ((Math.random()*15)+1).toFloat()
                    if (moscaX <= 0) moscaX += ((Math.random()*15)+1).toFloat()
                    if (moscaX >= 600) moscaX -= ((Math.random()*15)+1).toFloat()
                }
                2-> {
                    //arriba
                    moscaY -= ((Math.random()*15)+1).toFloat()
                    if (moscaY <= 0) moscaY += ((Math.random()*15)+1).toFloat()
                    if (moscaY >= 1200) moscaY -= ((Math.random()*15)+1).toFloat()
                }
                3-> {
                    //abajo
                    moscaY += ((Math.random()*15)+1).toFloat()
                    if (moscaY >= 1200) moscaY -= ((Math.random()*15)+1).toFloat()
                    if (moscaY <= 0) moscaY += ((Math.random()*15)+1).toFloat()
                }
            }
    }

    fun estaEnArea(toqueX: Float, toqueY: Float) : Boolean{
        var x2 = moscaX+imagen.width
        var y2 = moscaY+imagen.height

        if(toqueX >= moscaX && toqueX <= x2){
            if(toqueY >= moscaY && toqueY <= y2){
                imagen = BitmapFactory.decodeResource(p.resources, R.drawable.mancha)
                vidaMosca = false
                p.puntuacion += 1
                return true
            }
        }

        return false
    }
}