package project.catalin.mybets.vistas.utils.customAndroidComponents;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import project.catalin.mybets.R;

/**
 * Created by Trabajo on 04/05/2016.
 */
public class ApuestaExactaView extends RelativeLayout {
    private final ImageView mIconEquipoLocal;
    private final ImageView mIconEquipoVisitante;
    private final TextView mNombreEquipoLocal;
    private final TextView mNombreEquipoVisitante;
    private final TextView mApuestaEquipoLocal;
    private final TextView mApuestaEquipoVisitante;
    private final GradientDrawable mFondoNombreEquipoLocal;
    private final GradientDrawable mFondoNombreEquipoVisitante;
    private final GradientDrawable mFondoScoreEquipoLocal;
    private final GradientDrawable mFondoScoreEquipoVisitante;
    private final RelativeLayout mAreaNombreEquipoLocal;
    private final RelativeLayout mAreaNombreEquipoVisitante;
    private final RelativeLayout mAreaScoreEquipoLocal;
    private final RelativeLayout mAreaScoreEquipoVisitante;
    private final View mSeparadorFondo;
    private boolean isActivoLocal;
    private boolean isActivoVisitante;

    public ApuestaExactaView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_view_apuestaexact, this);

        mIconEquipoLocal = (ImageView) findViewById(R.id.apostarexact_icon_equipo1);
        mIconEquipoVisitante = (ImageView) findViewById(R.id.apostarexact_icon_equipo2);
        mNombreEquipoLocal = (TextView) findViewById(R.id.apostarexact_text_nombre_equipo1);
        mNombreEquipoVisitante = (TextView) findViewById(R.id.apostarexact_text_nombre_equipo2);
        mApuestaEquipoLocal = (TextView) findViewById(R.id.apostarexact_text_score_equipo1);
        mApuestaEquipoVisitante = (TextView) findViewById(R.id.apostarexact_text_score_equipo2);
        mAreaNombreEquipoLocal = (RelativeLayout) findViewById(R.id.apostarexact_fondo_nombre_equipo1);
        mAreaNombreEquipoVisitante = (RelativeLayout) findViewById(R.id.apostarexact_fondo_nombre_equipo2);
        mAreaScoreEquipoLocal = (RelativeLayout) findViewById(R.id.apostarexact_fondo_score_equipo1);
        mAreaScoreEquipoVisitante = (RelativeLayout) findViewById(R.id.apostarexact_fondo_score_equipo2);
        mFondoNombreEquipoLocal = (GradientDrawable) mAreaNombreEquipoLocal.getBackground();
        mFondoNombreEquipoVisitante = (GradientDrawable) mAreaNombreEquipoVisitante.getBackground();
        mFondoScoreEquipoLocal = (GradientDrawable) mAreaScoreEquipoLocal.getBackground();
        mFondoScoreEquipoVisitante = (GradientDrawable) mAreaScoreEquipoVisitante.getBackground();
        mSeparadorFondo = findViewById(R.id.apostarexact_separador_fondo);
        isActivoLocal = false;
        isActivoVisitante = false;

        initParams(attrs);
    }

    private void initParams(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.ApuestaExactaView);
        mSeparadorFondo.setBackgroundColor(getResources().getColor(R.color.gris_claro_peronotanto));
        mIconEquipoLocal.setImageResource(ta.getResourceId(R.styleable.ApuestaExactaView_icono_equipo_local, R.drawable.mybets_icono_equipo_placeholder));
        mNombreEquipoLocal.setText(ta.getString(R.styleable.ApuestaExactaView_nombre_equipo_local));
        mIconEquipoVisitante.setImageResource(ta.getResourceId(R.styleable.ApuestaExactaView_icono_equipo_visitante, R.drawable.mybets_icono_equipo_placeholder));
        mNombreEquipoVisitante.setText(ta.getString(R.styleable.ApuestaExactaView_nombre_equipo_visitante));
        setPuntuacionLocal(ta.getInt(R.styleable.ApuestaExactaView_apuesta_equipo_local, -1));
        setPuntuacionVisitante(ta.getInt(R.styleable.ApuestaExactaView_apuesta_equipo_visitante, -1));
    }

    private void setPuntuacionVisitante(int puntuacion) {
        if (puntuacion == -1) deshabilitarVisitante();
        else {
            habilitarVisitante();
            mApuestaEquipoVisitante.setText(Integer.toString(puntuacion));
        }
    }

    private void habilitarVisitante() {
        isActivoVisitante = true;
        int colorAzul = getResources().getColor(R.color.mybets_color_categoria_azul_oscuro);
        int colorAzulAlpha = getResources().getColor(R.color.mybets_color_categoria_azul_oscuro_alpha);
        mSeparadorFondo.setBackgroundColor(Color.WHITE);
        mFondoNombreEquipoVisitante.setColor(colorAzul);
        mFondoScoreEquipoVisitante.setColor(colorAzulAlpha);
        mFondoScoreEquipoVisitante.setStroke(0, colorAzulAlpha);
        mApuestaEquipoVisitante.setTextColor(Color.WHITE);
        mApuestaEquipoVisitante.setText("0");
    }

    private void deshabilitarVisitante() {
        isActivoVisitante = false;
        int colorGris = getResources().getColor(R.color.gris_medio);
        int colorGrisClaro = getResources().getColor(R.color.gris_claro_peronotanto);
        mFondoNombreEquipoVisitante.setColor(colorGris);
        mFondoScoreEquipoVisitante.setColor(Color.TRANSPARENT);
        mFondoScoreEquipoVisitante.setStroke(3, colorGrisClaro);
        mApuestaEquipoVisitante.setTextColor(colorGris);
        mApuestaEquipoVisitante.setText("_");
    }

    private void setPuntuacionLocal(int puntuacion) {
        if (puntuacion == -1) deshabilitarLocal();
        else {
            habilitarLocal();
            mApuestaEquipoLocal.setText(Integer.toString(puntuacion));
        }
    }

    private void habilitarLocal() {
        isActivoLocal = true;
        int colorAzul = getResources().getColor(R.color.mybets_color_categoria_azul_oscuro);
        int colorAzulAlpha = getResources().getColor(R.color.mybets_color_categoria_azul_oscuro_alpha);
        mSeparadorFondo.setBackgroundColor(Color.WHITE);
        mFondoNombreEquipoLocal.setColor(colorAzul);
        mFondoScoreEquipoLocal.setColor(colorAzulAlpha);
        mFondoScoreEquipoLocal.setStroke(0, colorAzulAlpha);
        mApuestaEquipoLocal.setTextColor(Color.WHITE);
        mApuestaEquipoLocal.setText("0");
    }

    private void deshabilitarLocal() {
        isActivoVisitante = false;
        int colorGris = getResources().getColor(R.color.gris_medio);
        int colorGrisClaro = getResources().getColor(R.color.gris_claro_peronotanto);
        mFondoNombreEquipoLocal.setColor(colorGris);
        mFondoScoreEquipoLocal.setColor(Color.TRANSPARENT);
        mFondoScoreEquipoLocal.setStroke(3, colorGrisClaro);
        mApuestaEquipoLocal.setTextColor(colorGris);
        mApuestaEquipoLocal.setText("_");
    }

    public void setFocusLocal() {
        if(isActivoLocal) mApuestaEquipoLocal.setBackgroundColor(getResources().getColor(R.color.colorFondoTextoClickable));
        else mApuestaEquipoLocal.setBackgroundColor(getResources().getColor(R.color.colorFondoTextoClickableGris));
    }

    public void setFocusVisitante() {
        if(isActivoVisitante) mApuestaEquipoVisitante.setBackgroundColor(getResources().getColor(R.color.colorFondoTextoClickable));
        mApuestaEquipoVisitante.setBackgroundColor(getResources().getColor(R.color.colorFondoTextoClickableGris));
    }

    public void unFocus() {
        mApuestaEquipoVisitante.setBackgroundColor(Color.TRANSPARENT);
        mApuestaEquipoLocal.setBackgroundColor(Color.TRANSPARENT);
    }

    public void setIconLocal(String urlIcono) {
        Picasso.with(getContext())
                .load(urlIcono)
                .into(mIconEquipoLocal);
    }

    public void setNombreLocal(String nombre) {
        mNombreEquipoLocal.setText(nombre);
    }

    public void setIconVisitante(String urlIcono) {
        Picasso.with(getContext())
                .load(urlIcono)
                .into(mIconEquipoVisitante);
    }

    public void setNombreVisitante(String nombre) {
        mNombreEquipoVisitante.setText(nombre);
    }

    public void setLocalClickListener(OnClickListener clickListener) {
        mAreaNombreEquipoLocal.setOnClickListener(clickListener);
        mAreaScoreEquipoLocal.setOnClickListener(clickListener);
    }

    public void setVisitanteClickListener(OnClickListener clickListener) {
        mAreaNombreEquipoVisitante.setOnClickListener(clickListener);
        mAreaScoreEquipoVisitante.setOnClickListener(clickListener);
    }
}
