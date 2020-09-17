package br.com.grupofgs.smartguide

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric


class MainActivity : AppCompatActivity() {
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
                    //Libera botao de mapa na home
                    val btMaps = this.findViewById<Button>(R.id.btMaps)
                    btMaps.setVisibility(View.VISIBLE);
                } else if (requestCode == 2) {
                    //Libera botao de chamada no mapa
                    val btCallHelp = this.findViewById<FloatingActionButton>(R.id.btCallHelp)
                    btCallHelp.setVisibility(View.VISIBLE);
                }
            }

        }

    }

    }