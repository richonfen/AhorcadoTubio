package ricardotubio.ahorcadoprueba;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * {@link FinPartidaFragmento.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class FinPartidaFragmento extends Fragment {


    private OnFragmentInteractionListener mListener;
    public ViewGroup view;
    private Button botonNuevoJuego;
    private Button botonSalir;
    private TextView textPregunta;

    int ganadas;
    int perdidas;

    public FinPartidaFragmento() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_fin_partida_fragmento, container, false);
        setView(view);
        botonNuevoJuego = (Button) view.findViewById(R.id.buttonNuevoJuego);
        botonSalir = (Button) view.findViewById(R.id.buttonSalir);
        textPregunta = (TextView) view.findViewById(R.id.textViewPregunta);



        botonNuevoJuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AhorcadoActivity.class);
                intent.putExtra("ganadas", ganadas);
                intent.putExtra("perdidas", perdidas);
                Log.d("ganadasFinFragment", "ganadas en FinPartidaFragmento es:" + ganadas);
                Log.d("perdidasFinFragment", "perdidas en FinPartidaFragmento es: " + perdidas);
                startActivity(intent);
            }
            }
        );

        botonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finishAffinity(); //api>15!!
            }
            }
        );

        return view;
    }

    public void actualizarFragmento(int ganadas, int perdidas){
        this.ganadas = ganadas;
        this.perdidas = perdidas;

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public void setView(ViewGroup view){
        this.view = view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
