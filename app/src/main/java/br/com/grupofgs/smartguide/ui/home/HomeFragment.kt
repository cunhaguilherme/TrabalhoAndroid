package br.com.grupofgs.smartguide.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import br.com.grupofgs.smartguide.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var btMaps: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView(view)

        //Verifica permissão de location, solicitar caso não tiver logo que abrir o app
        if (ContextCompat.checkSelfPermission(this.requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Sem permissão
            print("Não tem permissao de location, entao solicita")
            ActivityCompat.requestPermissions(this.requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        } else {
            //Com permissao
            print("Já tem permissao de location")
            btMaps.setVisibility(View.VISIBLE);
        }
    }

    private fun setUpView(view: View) {
        btMaps = view.findViewById(R.id.btMaps)

        //Botão de mapa inicia invisivel até usuário dar permissão
        btMaps.setVisibility(View.GONE);

        btMaps.setOnClickListener {
            NavHostFragment.findNavController(this)
                    .navigate(
                            R.id.action_homeFragment_to_mapFragment,
                            null,
                            null
                    )
        }
    }

}