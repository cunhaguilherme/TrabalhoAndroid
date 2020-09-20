package br.com.grupofgs.smartguide

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import br.com.grupofgs.smartguide.ui.ListenFromActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
//import com.crashlytics.android.Crashlytics
//import io.fabric.sdk.android.Fabric


class MainActivity : AppCompatActivity() {

    var activityListener: ListenFromActivity? = null

    var permissionGps: Boolean = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreen()
        setContentView(R.layout.activity_main)

       //Crashlytics.getInstance().crash();

    }

    private fun fullScreen() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        supportActionBar?.hide()
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        for (permissionResult in grantResults) {

            if (permissionResult == PackageManager.PERMISSION_GRANTED){
                if (requestCode == 1) {
                    //Libera botao de mapa ao inciar home
                    activityListener?.changeMapButtonState(true)

                } else if (requestCode == 2) {
                    //Libera botao de chamada no mapa
                    val btCallHelp = this.findViewById<FloatingActionButton>(R.id.btCallHelp)
                    btCallHelp.setVisibility(View.VISIBLE);
                }
            } else {
                if (requestCode == 1) {
                    //Não libera botão de mapa ao iniciar home
                    activityListener?.changeMapButtonState(false)
                }
            }
        }

    }

    @JvmName("setActivityListener1")
    fun setActivityListener(activityListener: ListenFromActivity?) {
        this.activityListener = activityListener
    }

    }