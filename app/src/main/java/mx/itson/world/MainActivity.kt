package mx.itson.world

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import mx.itson.world.entidades.Visita
import mx.itson.world.utilerias.RetrofitUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), OnMapReadyCallback, LocationListener, View.OnClickListener {

    var mapa : GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn: Button = findViewById(R.id.btnnuevo)
        btn.setOnClickListener {
            val intent: Intent = Intent(this, NuevaVisitaActivity::class.java)
            startActivity(intent)
        }


        var mapaFragment = supportFragmentManager.findFragmentById(R.id.mapa) as SupportMapFragment
        mapaFragment.getMapAsync(this)
        obtenerVisitas(true)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        try {
            mapa = googleMap
            mapa!!.mapType = GoogleMap.MAP_TYPE_HYBRID

        }catch (ex:Exception){
            Log.e("error al cargar el maoa",ex.toString())
        }
    }

    fun obtenerVisitas(v:Boolean){
        val call : Call<List<Visita>> = RetrofitUtil.getApi().getVisitas()
        call.enqueue(object : Callback<List<Visita>> {
            override fun onResponse(call: Call<List<Visita>>, response: Response<List<Visita>>) {
                val visitas : List<Visita> = response.body()!!
                if(v){
                mapa?.clear()
                }

                for(v in visitas){
                    /*val latLng = LatLng(v.latitud!!, v.longitud!!)
                    mapa!!.addMarker(MarkerOptions().position(latLng)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_cheems)))*/
                    val latlng= LatLng(v.latitud!!,v.longitud!!)
                    mapa?.addMarker(MarkerOptions().position(latlng).title(v.lugar).snippet(v.motivo+ ", "+"\n"+
                            "\n"+getResources().getString(R.string.responsable)+" \n" +v.responsable).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_cheems)))!!

                    mapa?.setOnMarkerClickListener(object:OnMarkerClickListener{
                        override fun onMarkerClick(marker: Marker): Boolean {

                            //Solo se ejecutara cuadno el usuario presiona el marker
                            val intent = Intent(applicationContext,VerVisitaActivity::class.java)

                            intent.putExtra("lugar",marker.title)
                            intent.putExtra("motivo",marker.snippet )
                            startActivity(intent)
                            return true
                        }
                    })
                }
            }

            override fun onFailure(call: Call<List<Visita>>, t: Throwable) {
            }

            })
        }

    override fun onLocationChanged(location: Location) {


        mapa?.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
            override fun onMarkerDrag(p0: Marker) {

            }

            override fun onMarkerDragEnd(marker: Marker) {


            }

            override fun onMarkerDragStart(p0: Marker) {

            }

        })
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btnnuevo ){
            val estaPermitido = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )== PackageManager.PERMISSION_GRANTED
            val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
            val location = locationManager.getLastKnownLocation(locationManager.getBestProvider(Criteria(),true)!!)
            location?.let { onLocationChanged(it) }
        }
    }

}
