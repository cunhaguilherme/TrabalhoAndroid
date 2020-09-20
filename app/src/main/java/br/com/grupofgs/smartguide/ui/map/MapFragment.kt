package br.com.grupofgs.smartguide.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import br.com.grupofgs.smartguide.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_map.*

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap

    val permissions = listOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE)

    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener

    private lateinit var myLocation: LatLng
    private var myLocationFirstTime: Boolean = false

    private val emergencyPhoneNumber = "12345678"

    private lateinit var btMyLocation: FloatingActionButton
    private lateinit var btPathToFIAP: ExtendedFloatingActionButton
    private lateinit var btCallHelp: FloatingActionButton
    private lateinit var btShareMap: FloatingActionButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Configuracoes iniciais da view
        setUpView(view)

        if (ContextCompat.checkSelfPermission(this.requireActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            //Sem permissão
            print("Não tem permissao de chamada, entao solicita")
            requestPermissions(this.requireActivity(), arrayOf(Manifest.permission.CALL_PHONE), 2)
        }else{
            //Com permissao
            print("Já tem permissao de chamada")
            btCallHelp.setVisibility(View.VISIBLE);
        }

        //Inicia mapa
        initMap(savedInstanceState)
    }

    private fun initMap(savedInstanceState: Bundle?) {
        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)
    }

    private fun setUpView(view: View) {
        btMyLocation = view.findViewById(R.id.btMyLocation)
        btPathToFIAP = view.findViewById(R.id.btPathToFIAP)
        btCallHelp = view.findViewById(R.id.btCallHelp)
        btShareMap = view.findViewById(R.id.btShareMap)

        //Botão de ligação de emergência inicia invisivel até usuário dar permissão
        btCallHelp.setVisibility(View.GONE);

        btMyLocation.setOnClickListener {
            googleMap.clear()
            //Centraliza na localização atual
            centreMapOnLocation(myLocation, getString(R.string.myCurrentLocationTitle), getString(R.string.myCurrentLocationSnippet))
        }

        btPathToFIAP.setOnClickListener {
            googleMap.clear()
            //Traça caminho do ponto de localizacao atual para FIAP
            pathToFIAP(myLocation)
        }

        btCallHelp.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:" + emergencyPhoneNumber)
            startActivity(callIntent)
        }

        btShareMap.setOnClickListener {
            val msgText = getString(R.string.myCurrentLocationSnippet) + " - " + myLocation.toString()

            val intent= Intent()
            intent.action=Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT,msgText)
            intent.type="text/plain"
            startActivity(Intent.createChooser(intent,getString(R.string.shareLocationTitle)))
        }
    }

    override fun onMapReady(map: GoogleMap?) {
        map?.let {
            googleMap = it
        }

        locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        initLocationListener()
        requestLocationUpdates()
    }

    @SuppressLint("MissingPermission")
    private fun requestLocationUpdates(){
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener);
    }

    fun centreMapOnLocation(locationToCenter: LatLng, title: String?, snippet: String?) {
        val userLocation = locationToCenter

        googleMap.clear()
        googleMap.addMarker(MarkerOptions()
                .position(userLocation)
                .title(title)
                .snippet(snippet))
                .showInfoWindow()

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 17.0f))
    }

    fun pathToFIAP(originLocation: LatLng) {
        val originLatLng = originLocation
        val fiapLatLng = LatLng(-23.5640419, -46.6525289)

        //Adiciona pin da Origem
        googleMap.addMarker(MarkerOptions()
                .position(originLatLng)
                .title(getString(R.string.myCurrentLocationTitle))
                .snippet(getString(R.string.myCurrentLocationSnippet)))
                .showInfoWindow()

        //Adiciona pin da FIAP
        googleMap.addMarker(MarkerOptions()
                .position(fiapLatLng)
                .title("FIAP")
                .snippet(getString(R.string.fiapLocationSnippet)))
                .showInfoWindow()

        val polylineOptions = PolylineOptions()
        polylineOptions.add(originLatLng)
        polylineOptions.add(fiapLatLng)
        polylineOptions.color(Color.RED)
        polylineOptions.width(15f)

        googleMap.addPolyline(polylineOptions)

        fitZoomToAllMarkers(originLatLng, fiapLatLng)
    }

    fun fitZoomToAllMarkers(origin: LatLng, dest: LatLng) {
        val b = LatLngBounds.Builder()

        b.include(origin)
        b.include(dest)

        val bounds = b.build()

        val padding = 200;
        val cu = CameraUpdateFactory.newLatLngBounds(bounds, padding)
        googleMap.animateCamera(cu)
    }

    private fun initLocationListener() {
        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                //Atualiza posição do usuário
                myLocation = LatLng(location.latitude, location.longitude)

                //Inicia mapa exibindo local atual
                if (!myLocationFirstTime) {
                    centreMapOnLocation(myLocation, getString(R.string.myCurrentLocationTitle), getString(R.string.myCurrentLocationSnippet))
                    myLocationFirstTime = true
                }
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                TODO("Not yet implemented")
            }

            override fun onProviderEnabled(provider: String?) {
                TODO("Not yet implemented")
            }

            override fun onProviderDisabled(provider: String?) {
                TODO("Not yet implemented")
            }
        }
    }

}



