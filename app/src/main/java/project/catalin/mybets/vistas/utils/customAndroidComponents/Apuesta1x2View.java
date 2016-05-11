package project.catalin.mybets.vistas.utils.customAndroidComponents;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import project.catalin.mybets.R;

/**
 * Created by Trabajo on 04/05/2016.
 */
public class Apuesta1x2View extends RelativeLayout {
    public static final int APUESTA_SIN_APOSTAR = -1;
    public static final int APUESTA_EMPATE = 0;
    public static final int APUESTA_LOCAL = 1;
    public static final int APUESTA_VISIATANTE = 2;


    private final ImageView mIconoEquipoLocal;
    private final TextView mNombreEquipoLocal;
    private final ImageView mIconoEquipoVisitante;
    private final TextView mNombreEquipoVisitante;
    private final RadioButton mBotonGanaEquipoLocal;
    private final RadioButton mBotonEmpatan;
    private final RadioButton mBotonGanaEquipoVisitante;
    private final RelativeLayout mFondo;

    private int mApuesta;
    private boolean mActivo;

    public Apuesta1x2View(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_view_apuesta1x2, this);

        mIconoEquipoLocal = (ImageView) findViewById(R.id.apostar1x2_icon_equipo1);
        mNombreEquipoLocal = (TextView) findViewById(R.id.apostar1x2_nombre_equipo1);
        mIconoEquipoVisitante = (ImageView) findViewById(R.id.apostar1x2_icon_equipo2);
        mNombreEquipoVisitante = (TextView) findViewById(R.id.apostar1x2_nombre_equipo2);
        mBotonGanaEquipoLocal = (RadioButton) findViewById(R.id.apostar1x2_boton_1);
        mBotonEmpatan = (RadioButton) findViewById(R.id.apostar1x2_boton_x);
        mBotonGanaEquipoVisitante = (RadioButton) findViewById(R.id.apostar1x2_boton_2);
        mFondo = (RelativeLayout) findViewById(R.id.apostar1x2_fondo);


        initParams(attrs);
    }

    private void initParams(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.Apuesta1x2View);
        setApuesta(ta.getInt(R.styleable.Apuesta1x2View_apuesta, APUESTA_SIN_APOSTAR));
        mNombreEquipoLocal.setText(ta.getString(R.styleable.Apuesta1x2View_nombre_equipo_local));
        mNombreEquipoVisitante.setText(ta.getString(R.styleable.Apuesta1x2View_nombre_equipo_visitante));
        mIconoEquipoLocal.setImageResource(ta.getResourceId(R.styleable.Apuesta1x2View_icono_equipo_local, R.drawable.mybets_icono_equipo_placeholder));
        mIconoEquipoVisitante.setImageResource(ta.getResourceId(R.styleable.Apuesta1x2View_icono_equipo_visitante, R.drawable.mybets_icono_equipo_placeholder));

    }

    public void setApuesta(int apuesta) {
        switch (apuesta) {
            case APUESTA_SIN_APOSTAR:
                setActivo(false);
                break;
            case APUESTA_LOCAL:
                setActivo(true);
                mBotonGanaEquipoLocal.setChecked(true);
                break;
            case APUESTA_EMPATE:
                setActivo(true);
                mBotonEmpatan.setChecked(true);
                break;
            case APUESTA_VISIATANTE:
                setActivo(true);
                mBotonGanaEquipoVisitante.setChecked(true);
                break;
        }
    }

    public void setActivo(boolean activo) {
        mActivo = activo;
        if(!activo){
            int colorGris = getResources().getColor(R.color.gris_medio);
            mNombreEquipoLocal.setTextColor(colorGris);
            mNombreEquipoVisitante.setTextColor(colorGris);
            mBotonGanaEquipoLocal.setTextColor(colorGris);
            mBotonEmpatan.setTextColor(colorGris);
            mBotonGanaEquipoVisitante.setTextColor(colorGris);
            mBotonGanaEquipoLocal.setBackground(getResources().getDrawable(R.drawable.mybets_fondo_radiobutton_disabled));
            mBotonEmpatan.setBackground(getResources().getDrawable(R.drawable.mybets_fondo_radiobutton_disabled));
            mBotonGanaEquipoVisitante.setBackground(getResources().getDrawable(R.drawable.mybets_fondo_radiobutton_disabled));
            mFondo.setBackground(getResources().getDrawable(R.drawable.mybets_fondo_apuesta1x2_equipos_disabled));
        } else {
            int colorBlanco = Color.WHITE;
            mNombreEquipoLocal.setTextColor(colorBlanco);
            mNombreEquipoVisitante.setTextColor(colorBlanco);
            mBotonGanaEquipoLocal.setTextColor(colorBlanco);
            mBotonEmpatan.setTextColor(colorBlanco);
            mBotonGanaEquipoVisitante.setTextColor(colorBlanco);
            mBotonGanaEquipoLocal.setBackground(getResources().getDrawable(R.drawable.mybets_radiobutton_toggle));
            mBotonEmpatan.setBackground(getResources().getDrawable(R.drawable.mybets_radiobutton_toggle));
            mBotonGanaEquipoVisitante.setBackground(getResources().getDrawable(R.drawable.mybets_radiobutton_toggle));
            mFondo.setBackground(getResources().getDrawable(R.drawable.mybets_fondo_apuesta1x2_equipos));
        }
    }

    public void setIconLocal(String urlIcono) {
        Picasso.with(getContext())
                .load(urlIcono)
                .into(mIconoEquipoLocal);
    }

    public void setIconVisitante(String urlIcono) {
        Picasso.with(getContext())
                .load(urlIcono)
                .into(mIconoEquipoVisitante);
    }

    public void setNombreLocal(String nombreLocal) {
        mNombreEquipoLocal.setText(nombreLocal);
    }

    public void setNombreVisitante(String nombreVisitante) {
        mNombreEquipoVisitante.setText(nombreVisitante);
    }

    public void set1OnClickListener(OnClickListener clickListener) {
        mBotonGanaEquipoLocal.setOnClickListener(clickListener);
    }

    public void setXOnclickListener(OnClickListener clickListener) {
        mBotonEmpatan.setOnClickListener(clickListener);
    }

    public void set2OnclickListener(OnClickListener clickListener) {
        mBotonGanaEquipoVisitante.setOnClickListener(clickListener);
    }
}
















