package mx.tecnm.tepic.u2_practica2_juegodelasmoscas

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.CountDownTimer
import android.view.MotionEvent
import android.view.View

class Lienzo(p:MainActivity) : View(p) {
    var lienzoP = p
    var puntuacion = 0
    var tiempo = 60
    var resultado = ""
    var aux = ""


    val hiloMosca = MoscasHilo(this)

    val temporizador = object: CountDownTimer(60000, 1000){
        override fun onTick(p0: Long) {
            tiempo -= 1
            if(puntuacion == hiloMosca.moscas.size){
                aux = "HAZ GANADO"
                hiloMosca.juego = false
                this.onFinish()
            }
            aux = "SE ACABÓ EL TIEMPO."
        }

        override fun onFinish() {
            hiloMosca.juego = false
            resultado = aux

        }

    }

    init {
        hiloMosca.start()
        temporizador.start()
    }

    @SuppressLint("NewApi")
    override fun onDraw(c: Canvas) {
        super.onDraw(c)
        c.drawColor(Color.WHITE)
        var p = Paint()
        p.color = Color.BLACK
        p.textSize =80f
        c.drawText("Puntuación: ${puntuacion}", ((c.width/2)-300).toFloat(), ((c.height)-200).toFloat(),p)
        c.drawText("Tiempo: ${tiempo}", ((c.width/2)-300).toFloat(), ((c.height)-400).toFloat(),p)

        p.textSize =40f
        p.color = Color.RED
        c.drawText("${resultado}",((c.width/2)-300).toFloat(), ((c.height)-500).toFloat(),p)


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
    var juego = true

    init{
        (1..80).forEach {
            val mosca = Mosca(puntero)
            moscas.add(mosca)
        }
    }

    override fun run() {
        super.run()

        while (juego){
            (0..79).forEach {
                if(moscas[it].vidaMosca)moscas[it].moverMosca()
            }
            puntero.lienzoP.runOnUiThread {
                puntero.invalidate()
            }
            sleep(80)
        }
        puntero.invalidate()
    }
}