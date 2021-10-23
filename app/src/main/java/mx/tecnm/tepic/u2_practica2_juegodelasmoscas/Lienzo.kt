package mx.tecnm.tepic.u2_practica2_juegodelasmoscas

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View

class Lienzo(p:MainActivity) : View(p) {
    var lienzoP = p
    var puntuacion = 0
    var tiempo = 60
    val hiloMosca = MoscasHilo(this)


    init {
        hiloMosca.start()
    }

    @SuppressLint("NewApi")
    override fun onDraw(c: Canvas) {
        super.onDraw(c)
        c.drawColor(Color.WHITE)
        var p = Paint()
        p.color = Color.BLACK
        p.textSize =80f
        c.drawText("Puntuación: ${puntuacion}", ((c.width/2)-300).toFloat(), ((c.height)-200).toFloat(),p)

        //Generar moscas
        (0..79).forEach {
            hiloMosca.moscas[it].pintar(c)
        }
    }

    @SuppressLint("NewApi")
    override fun onTouchEvent(e: MotionEvent): Boolean {
        super.onTouchEvent(e)

        val accion = e.action

        if(accion == MotionEvent.ACTION_DOWN){
            //Tocas
            (0..79).forEach{
                if(hiloMosca.moscas[it].vidaMosca == true)hiloMosca.moscas[it].estaEnArea(e.x, e.y)
            }
        }

        invalidate()
        return true
    }

}

@SuppressLint("NewApi")
class MoscasHilo(p:Lienzo) : Thread(){
    val puntero = p
    val moscas = ArrayList<Mosca>()

    init{
        (1..80).forEach {
            val mosca = Mosca(puntero)
            moscas.add(mosca)
        }
    }

    override fun run() {
        super.run()
        while (puntero.puntuacion < moscas.size){
            (0..79).forEach {
                if(moscas[it].vidaMosca)moscas[it].moverMosca()
            }
            puntero.lienzoP.runOnUiThread {
                puntero.invalidate()
            }
            sleep(80)
        }
    }
}