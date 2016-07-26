package ricardotubio.ahorcadoprueba;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DibujoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class DibujoFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    public ViewGroup view;   //final?



    public DibujoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = (ViewGroup) inflater.inflate(R.layout.fragment_dibujo, container, false);
        Log.d("view en CreatView", "view del inflate es null? " + (view == null));
        setView(view);


        //recibo la cantidad de errores desde activityJuega y lo muestro en el txtView
        Bundle bundle = getArguments();
        final int cantidadErrores = bundle.getInt("erroresCometidos");

        actualizarFragmentoDibujo(cantidadErrores);

        return view;
    }

    public void actualizarFragmentoDibujo(int cantidadErrores){
        ImageView imagenAhorcado = (ImageView) view.findViewById(R.id.imagenAhorcado);
        RelativeLayout.LayoutParams paramImage = new RelativeLayout.LayoutParams(280, 280);
        imagenAhorcado.setLayoutParams(paramImage);
        switch (cantidadErrores) {
            case 0:
                imagenAhorcado.setImageResource(R.drawable.ahorcado0);
                break;
            case 1:
                imagenAhorcado.setImageResource(R.drawable.ahorcado1);
                break;
            case 2:
                imagenAhorcado.setImageResource(R.drawable.ahorcado2);
                break;
            case 3:
                imagenAhorcado.setImageResource(R.drawable.ahorcado3);
                break;
            case 4:
                imagenAhorcado.setImageResource(R.drawable.ahorcado4);
                break;
            case 5:
                imagenAhorcado.setImageResource(R.drawable.ahorcado5);
                break;
            case 6:
                imagenAhorcado.setImageResource(R.drawable.ahorcado6);
                break;
            default:
                imagenAhorcado.setImageResource(R.drawable.ahorcadoerror);
                break;
        }
    }

    public void setView(ViewGroup view){
        this.view = view;
    }

    public ViewGroup getView(){
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    //context es para api 23 en adelante. En este proyecto el objetivo es api22
    public void onAttach(Activity activity) {
        super.onAttach(activity);  //revisar este tema con respecto a la api objetivo (< Api23) espera activity
        if (activity instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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

