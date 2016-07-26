package ricardotubio.ahorcadoprueba;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;


public class JuegaActivity extends Activity implements DibujoFragment.OnFragmentInteractionListener, FinPartidaFragmento.OnFragmentInteractionListener {

    private TextView txtPalabraActual;
    private TextView txtPruebaPalabra;
    private TextView txtHistorial;
    private TextView txtLetra;
    private Button botonMostrarDibujo;
    private TextView txtFragmentTag;


    private View.OnClickListener clickListenerLetras;
    private ArrayList<Button> botonesLetras;

    private int maxErrores = 6;
    private int erroresCometidos = 0;
    private int aciertos = 0;
    private int longitud = 0;
    private int ganadas = 0;
    private int perdidas = 0;

    char[] arregloPalabraActual;
    char[] arregloGuionBajo;
    char[] palabraAMostrar;

    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    boolean mostrandoDibujo = false;
    private final static String FRAGMENT_TAG = "FRAGMENTB_TAG";

    private void ponerEspacios(char[] arregloOrigen, char[] arregloDestino) {
        int i, k;
        int j = arregloDestino.length;

        for (i = 0, k = 0; k < j; i++, k++) {
            arregloDestino[k] = arregloOrigen[i];
            if (k + 1 < j) {
                k++;
                arregloDestino[k] = ' ';
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juega);

        // Lista de botones, antes estaba en AhoracadoActivity, en metodo onCreate(...)
        botonesLetras = new ArrayList<Button>();
        // Rellenar la lista de botonesLetras
        botonesLetras.add((Button) findViewById(R.id.txt_a));
        botonesLetras.add((Button) findViewById(R.id.txt_b));
        botonesLetras.add((Button) findViewById(R.id.txt_c));
        botonesLetras.add((Button) findViewById(R.id.txt_d));
        botonesLetras.add((Button) findViewById(R.id.txt_e));
        botonesLetras.add((Button) findViewById(R.id.txt_f));
        botonesLetras.add((Button) findViewById(R.id.txt_g));
        botonesLetras.add((Button) findViewById(R.id.txt_h));
        botonesLetras.add((Button) findViewById(R.id.txt_i));
        botonesLetras.add((Button) findViewById(R.id.txt_j));
        botonesLetras.add((Button) findViewById(R.id.txt_k));
        botonesLetras.add((Button) findViewById(R.id.txt_l));
        botonesLetras.add((Button) findViewById(R.id.txt_m));
        botonesLetras.add((Button) findViewById(R.id.txt_n));
        botonesLetras.add((Button) findViewById(R.id.txt_ñ));
        botonesLetras.add((Button) findViewById(R.id.txt_o));
        botonesLetras.add((Button) findViewById(R.id.txt_p));
        botonesLetras.add((Button) findViewById(R.id.txt_q));
        botonesLetras.add((Button) findViewById(R.id.txt_r));
        botonesLetras.add((Button) findViewById(R.id.txt_s));
        botonesLetras.add((Button) findViewById(R.id.txt_t));
        botonesLetras.add((Button) findViewById(R.id.txt_u));
        botonesLetras.add((Button) findViewById(R.id.txt_v));
        botonesLetras.add((Button) findViewById(R.id.txt_w));
        botonesLetras.add((Button) findViewById(R.id.txt_x));
        botonesLetras.add((Button) findViewById(R.id.txt_y));
        botonesLetras.add((Button) findViewById(R.id.txt_z));


        txtPalabraActual = (TextView) findViewById(R.id.PalabraActual);
        txtHistorial = (TextView) findViewById(R.id.historial);

        txtHistorial.setText("Ganadas: " + ganadas + " Perdidas: " + perdidas);

        Intent intent = getIntent();

        //relacionamos palabraObjetivo con el texto de la actividad
        final String palabraActual = intent.getStringExtra("PALABRA");
        ganadas = intent.getIntExtra("ganadas", ganadas);
        perdidas = intent.getIntExtra("perdidas", perdidas);

        txtHistorial.setText("Ganadas: " + ganadas + " Perdidas: " + perdidas);

        //propiedades de la palabra
        longitud = palabraActual.length();
        //elimina espacios en blanco
        palabraActual.replaceAll("\\s+", "");



        final int longitudAMostrar = longitud * 2 - 1;

        arregloPalabraActual = palabraActual.toCharArray();
        arregloGuionBajo = palabraActual.toCharArray();

        for (int i = 0; i < longitud; i++) {
            arregloGuionBajo[i] = '_';
        }

        palabraAMostrar = new char[longitudAMostrar];    //para mostrar con espacios entre letras
        //the array itself is final, but the contents of the array can still be changed
        // (a little strange definition of final in Java, if you ask me; final does not mean immutable).

        ponerEspacios(arregloGuionBajo, palabraAMostrar);

        //creo el fragmento
        fragmentManager = iniciarFragmentoDibujo();
        fragmentManager = iniciarFragmentoFinPartida();

        //Boton que muestra el fragmento!!!!
        botonMostrarDibujo = (Button) findViewById(R.id.botonMostrarDibujo);

        botonMostrarDibujo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DibujoFragment fragmentCantError = (DibujoFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                if (mostrandoDibujo == false) {
                    fragmentCantError.actualizarFragmentoDibujo(erroresCometidos);

                    transaction.show(fragmentCantError);
                    transaction.commit();
                    mostrandoDibujo = true;
                } else {
                    transaction.hide(fragmentCantError);
                    transaction.commit();
                    mostrandoDibujo = false;
                }
            }
        });

        txtPalabraActual.setText(palabraAMostrar, 0, longitudAMostrar);

        // ClickListener para las letras:
        clickListenerLetras = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String idString = view.getResources().getResourceEntryName(view.getId()); //http://stackoverflow.com/questions/3920833/android-imageview-getid-returning-integer

                Button botonPresionado = (Button) view.findViewById(getResources().getIdentifier(idString, "id", getPackageName()));  //http://stackoverflow.com/questions/14056817/findviewbyid-where-id-is-dynamic-string
                botonPresionado.setEnabled(false);

                boolean estaLetra;
                estaLetra = comprobarLetra(view);
                if (!estaLetra) {
                    erroresCometidos++;
                    botonPresionado.setBackgroundColor(Color.RED);
                    //actualizo el fragmento:
                    DibujoFragment fragmentCantError = (DibujoFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
                    fragmentCantError.actualizarFragmentoDibujo(erroresCometidos);
                    //

                    if (erroresCometidos >= maxErrores) {
                        Toast.makeText(getApplicationContext(), "Partida Perdida! Mejor suerte la próxima!", Toast.LENGTH_LONG).show();
                        //aca deshabilitar el resto de los botones
                        perdidas++;
                        partidaFinalizada();
                    }

                }
                else {
                    botonPresionado.setBackgroundColor(Color.BLUE);
                    if (aciertos == longitud){
                        Toast.makeText(getApplicationContext(), "Partida Ganada! Felicitaciones!", Toast.LENGTH_LONG).show();
                        ganadas++;
                        partidaFinalizada();
                    }
                }
            }
        };
        for (Button b : botonesLetras) {
            b.setOnClickListener(clickListenerLetras);
        }

    }

    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }

    public int getErroresComentidos(){
        return erroresCometidos;
    }


    public boolean comprobarLetra(View view) {
        boolean estaLetra = false;

        // para comparar el TextView clickeado con el char de la palabra por adivinar:
        Button boton = (Button)view;
        String letraString = boton.getText().toString();
        char letra = letraString.charAt(0);
        //

        int i;
        for (i = 0; i < arregloPalabraActual.length; i++){
            if (letra == arregloPalabraActual[i]){
                //la letra esta y la debería mostrar
                estaLetra = true;
                palabraAMostrar[2*i] = letra;  //letra sera un caracter? o sera string?
                aciertos++;

            }
        }
        return estaLetra;
    }

    private void resizeFragment(DibujoFragment f, int newWidth, int newHeight) {
        if (f != null) {
            View view = f.getView().findViewById(R.id.layout_juega);
            Log.d("getViewResizeFragment", "view en resizeFragment es null?: " + (view ==null) );
            RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(newWidth, newHeight);
            view.setLayoutParams(p);    //null pointer aca
            view.requestLayout();
        }
    }

    private FragmentManager iniciarFragmentoDibujo() {
        fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("ganadas", erroresCometidos);        //no corresponde?
        DibujoFragment fragmentCantError = new DibujoFragment();
        fragmentCantError.setArguments(bundle);
        transaction.add(R.id.fragment_container_dibujo, fragmentCantError, FRAGMENT_TAG);
        transaction.hide(fragmentCantError);                        //esconde el fragmenta luego de su creacion
        transaction.addToBackStack(FRAGMENT_TAG);                       //http://stackoverflow.com/questions/23581894/getfragmentmanager-findfragmentbytag-returns-null
        transaction.commit();
        Boolean pending = fragmentManager.executePendingTransactions();           //http://stackoverflow.com/questions/26988588/findfragmentbytag-always-return-null-android


        return fragmentManager;     //utilizar transaction para mostrar y esconder el fragmento desde el boton de la activity
    }

    private FragmentManager iniciarFragmentoFinPartida(){
        fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        FinPartidaFragmento finPartidaFragmento = new FinPartidaFragmento();
        transaction.add(R.id.fragment_container_fin_partida, finPartidaFragmento, getString(R.string.fragmento_fin_partida_tag));
        transaction.hide(finPartidaFragmento);                        //esconde el fragmenta luego de su creacion
        transaction.addToBackStack(FRAGMENT_TAG);                       //http://stackoverflow.com/questions/23581894/getfragmentmanager-findfragmentbytag-returns-null
        transaction.commit();

        return fragmentManager;
    }

    private void partidaFinalizada() {
        deshabilitarBotones();
        txtHistorial.setText("Ganadas: " + ganadas + " Perdidas: " + perdidas);
        Log.d("ganadasPerdidasJuega", "en juega ganadas es " + ganadas + " y perdidas " + perdidas);
        FinPartidaFragmento fragmentFinPartida = (FinPartidaFragmento) fragmentManager.findFragmentByTag(getString(R.string.fragmento_fin_partida_tag));

        fragmentFinPartida.actualizarFragmento(ganadas,perdidas);


        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.show(fragmentFinPartida);
        transaction.commit();

    }

    private void deshabilitarBotones(){
        for (Button boton: botonesLetras){
            boton.setEnabled(false);
        }
    }

}
