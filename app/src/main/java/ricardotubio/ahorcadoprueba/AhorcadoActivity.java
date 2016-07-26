package ricardotubio.ahorcadoprueba;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;


public class AhorcadoActivity extends Activity {

    private EditText ingresoPalabra;
    private int ganadas = 0;
    private int perdidas = 0;

    private Button botonAceptar;
    private ArrayList<TextView> botonesLetras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ahorcado);

        //Obtenemos una referencia a los controles de la interfaz
        ingresoPalabra = (EditText)findViewById(R.id.IngresoPalabra);
        botonAceptar = (Button)findViewById(R.id.BotonAceptar);



        botonAceptar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intentEntrada = getIntent();

                Log.d("intentEntrada", "intentEntrada null? " + (intentEntrada == null));
                if (intentEntrada != null){
                    Bundle bundle = intentEntrada.getExtras();

                    if (bundle !=null){
                        ganadas = bundle.getInt("ganadas" , ganadas);
                        perdidas = bundle.getInt("perdidas", perdidas);

                        Log.d("ganadasComienzo", "ganadas en primera actividad: " + ganadas);
                        Log.d("perdidasComienzo", "perdidas en primera actividad: " + ganadas);
                    }

                }

                //obtenemos la palabra ingresada
                String palabra = ingresoPalabra.getText().toString().toUpperCase();

                //Creamos el Intent
                Intent intent = new Intent(AhorcadoActivity.this, JuegaActivity.class);

                //Anadimos la informacion al intent
                intent.putExtra("PALABRA", palabra);
                intent.putExtra("ganadas", ganadas);
                intent.putExtra("perdidas", perdidas);

                //Iniciamos la nueva actividad
                startActivity(intent);
            }
        });
    }

}



