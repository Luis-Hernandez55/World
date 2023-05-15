package mx.itson.world

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class VerVisitaActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_visita)

        val backButton = findViewById<Button>(R.id.btnCerrar)
        backButton.setOnClickListener {
            onBackPressed()
        }

        val txtLugar  = findViewById<TextView>(R.id.lugar)
        val lugar = intent.getStringExtra("lugar")
        txtLugar.setText(lugar)
        val txtMotivo  = findViewById<TextView>(R.id.motivo)
        val motivo = intent.getStringExtra("motivo")
        txtMotivo.setText(motivo
        )
    }

    override fun onClick(view: View?) {
        //Cerramos la ventana
        /*when (view?.id) {
            R.id.btnCerrar -> finish()
        }*/
    }
}