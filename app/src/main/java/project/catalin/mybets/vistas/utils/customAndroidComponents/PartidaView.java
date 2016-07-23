package project.catalin.mybets.vistas.utils.customAndroidComponents;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import project.catalin.mybets.R;
import project.catalin.mybets.datos.dataObjects.Partida;

/**
 * Created by Trabajo on 28/04/2016.
 */
public class PartidaView extends RelativeLayout {
    public static final int PARTIDA_JUGABLE = Partida.ESTADO_JUEGA_YA;
    public static final int PARTIDA_ESPERNANDO_RESLTADOS = Partida.ESTADO_ESPERANDO_RESULTADOS;
    public static final int PARTIDA_RECHAZADA = Partida.ESTADO_PARTIDA_RECHAZADA;
    public static final int PARTIDA_GANADA = Partida.ESTADO_PARTIDA_GANADA;

    private final TextView mTitulo;
    private final TextView mBote;
    private final View mIconoIzquierdaFondo;
    private final ImageView mIconoIzquierda;
    private final View mIconoCountDown;
    private final TextView mTextCountDown;
    private final TextView mNumPersonas;
    private final TextView mBotonJuegaYa;
    private final TextView mTextApuestaRechazada;
    private final TextView mTextEsperandoResultados;
    private final TextView mTextFechaFin;
    private final ImageView mBotonAcciones;
    private final View mPopUpBox;

    private boolean mMostrarCountDown;
    private int mEstadoPartida;
    private CountDownTimer mTimer;
    private TextView mTextPuntosGanados;
    private PopupMenu mPopUp;
    private PopupMenu.OnMenuItemClickListener mPopUpListener;

    public PartidaView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_view_partida, this);

        mTitulo = (TextView) findViewById(R.id.partida_item_text_titulo);
        mBote = (TextView) findViewById(R.id.partida_item_text_bote);
        mIconoIzquierdaFondo = findViewById(R.id.partida_item_fondo_icon);
        mIconoIzquierda = (ImageView) findViewById(R.id.partida_item_icon_placeholder);
        mIconoCountDown = findViewById(R.id.partida_item_icon_reloj);
        mTextCountDown = (TextView) findViewById(R.id.partida_item_text_countdown);
        mNumPersonas = (TextView) findViewById(R.id.partida_item_text_num_personas);
        mBotonJuegaYa = (TextView) findViewById(R.id.partida_item_boton_juega_ya);
        mTextFechaFin = (TextView) findViewById(R.id.partida_item_text_fecha);
        mTextApuestaRechazada = (TextView) findViewById(R.id.partida_item_text_apuesta_rechazada);
        mTextEsperandoResultados = (TextView) findViewById(R.id.partida_item_text_apuesta_esperando_resultados);
        mBotonAcciones = (ImageView) findViewById(R.id.partida_item_boton_acciones);
        mTextPuntosGanados = (TextView) findViewById(R.id.partida_item_text_apuesta_ganada);
        mPopUpBox = findViewById(R.id.partida_item_popup_box);
;


        initParams(attrs);
    }

    private void initParams(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.PartidaView);
        setEstadoPartida(ta.getInt(R.styleable.PartidaView_estadoPartida, -1));
        setMostrarCountDown(ta.getBoolean(R.styleable.PartidaView_mostrarCountdown, false));
        setMostrarBotonAcciones(ta.getBoolean(R.styleable.PartidaView_mostrarBotonAcciones, false));
        initBotonAcciones();

        ta.recycle();
    }

    private void initBotonAcciones() {
        mBotonAcciones.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopUp.show();
            }
        });
    }

    public void setMostrarBotonAcciones(boolean mostrar) {
        mBotonAcciones.setVisibility(GONE);
        if (mostrar) mBotonAcciones.setVisibility(VISIBLE);
    }
    public void setEstadoPartida(int estadoPartida) {
        mEstadoPartida = estadoPartida;

        mBotonJuegaYa.setVisibility(GONE);
        mTextApuestaRechazada.setVisibility(GONE);
        mTextEsperandoResultados.setVisibility(GONE);
        mTextPuntosGanados.setVisibility(GONE);

        switch (estadoPartida) {
            case PARTIDA_JUGABLE:
                mBotonJuegaYa.setVisibility(VISIBLE);
                mPopUp = new PopupMenu(getContext(), mPopUpBox);
                mPopUp.getMenuInflater().inflate(R.menu.menu_acciones_partida_juega_ya, mPopUp.getMenu());
                break;
            case PARTIDA_RECHAZADA:
                mTextApuestaRechazada.setVisibility(VISIBLE);
                mPopUp = new PopupMenu(getContext(), mPopUpBox);
                mPopUp.getMenuInflater().inflate(R.menu.menu_acciones_partida_rechazada, mPopUp.getMenu());
                break;
            case PARTIDA_ESPERNANDO_RESLTADOS:
                mTextEsperandoResultados.setVisibility(VISIBLE);
                mPopUp = new PopupMenu(getContext(), mPopUpBox);
                mPopUp.getMenuInflater().inflate(R.menu.menu_acciones_partida_esperando_resultados, mPopUp.getMenu());
                break;
            case PARTIDA_GANADA:
                mTextPuntosGanados.setVisibility(VISIBLE);
                mPopUp = new PopupMenu(getContext(), mPopUpBox);
                mPopUp.getMenuInflater().inflate(R.menu.menu_acciones_partida_ganada, mPopUp.getMenu());
                break;
        }
    }

    public void setMostrarCountDown(boolean mostrarCountDown) {
        mMostrarCountDown = mostrarCountDown;

        mTextFechaFin.setVisibility(GONE);
        mIconoCountDown.setVisibility(GONE);
        mTextCountDown.setVisibility(GONE);

        if (mMostrarCountDown) {
            mIconoCountDown.setVisibility(VISIBLE);
            mTextCountDown.setVisibility(VISIBLE);
        } else mTextFechaFin.setVisibility(VISIBLE);

    }

    public void setColorFondoIcono(String colorFondoIcono) {
        GradientDrawable drawableFondo = (GradientDrawable) mIconoIzquierdaFondo.getBackground();
        drawableFondo.setColor(Color.parseColor(colorFondoIcono));
    }

    public void setUrlImagenIcono(String urlImagenIcono) {
        Picasso.with(getContext())
                .load(urlImagenIcono)
                .into(mIconoIzquierda);
    }

    public void setTitulo(String titulo) {
        mTitulo.setText(titulo);
    }

    public void setBoteNum(int boteNum) {
        mBote.setText(MessageFormat.format(getContext().getString(R.string.partida_view_bote_text), boteNum));
    }

    public void setNumPersonas(int numPersonas) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        mNumPersonas.setText(numberFormat.format(numPersonas));
    }

    public void setFechaFin(Date fechaFin) {
        long millisFin = fechaFin.getTime();
        long millisEnUnDia = (1000 * 60 * 60 * 24);
        long millisHoy = Calendar.getInstance().getTimeInMillis();
        long millisRestantes = millisFin - millisHoy;

        if (millisRestantes > millisEnUnDia) {
            SimpleDateFormat formatoFecha = new SimpleDateFormat(getContext().getString(R.string.partida_view_formato_fecha_fin), Locale.getDefault());
            mTextFechaFin.setText(MessageFormat.format(getContext().getString(R.string.partida_veiw_texto_fecha_fin), formatoFecha.format(fechaFin)));
            setMostrarCountDown(false);
        } else if (millisRestantes > 0) {
            setUpTimer(millisRestantes);
            setMostrarCountDown(true);
        } else {
            mTextFechaFin.setText(R.string.partida_item_text_fin_apuesta);
            setMostrarCountDown(false);
        }
    }

    public void setUpTimer(long millisRestantes) {
        if (mTimer != null) mTimer.cancel();
        final SimpleDateFormat formatoFecha = new SimpleDateFormat(getContext().getString(R.string.partida_view_formato_contador), Locale.getDefault());

        mTimer = new CountDownTimer(millisRestantes, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

                Date data = new Date(millisUntilFinished);
                mTextCountDown.setText(formatoFecha.format(data));
            }

            @Override
            public void onFinish() {
                mTextFechaFin.setText(R.string.partida_item_text_fin_apuesta);
                setMostrarCountDown(false);
            }
        }.start();
    }

    public void setBotonJuegaYaClickListener(OnClickListener clickListener) {
        mBotonJuegaYa.setOnClickListener(clickListener);
    }

    public void setNumPersonas(String numPersonas) {
        mNumPersonas.setText(numPersonas);
    }

    public void setPuntosGanados(int puntosGanados) {
        mTextPuntosGanados.setText(MessageFormat.format(getContext().getString(R.string.partida_item_format_puntos_ganados), puntosGanados));
        setEstadoPartida(PARTIDA_GANADA);
    }

    public void setColorFecha(int color) {
        mTextFechaFin.setTextColor(getResources().getColor(color));
    }

    public void setAccionesItemClickListener(PopupMenu.OnMenuItemClickListener menuItemClickListener) {
        mPopUp.setOnMenuItemClickListener(menuItemClickListener);
    }
}
