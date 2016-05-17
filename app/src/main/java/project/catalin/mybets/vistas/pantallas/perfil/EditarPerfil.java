package project.catalin.mybets.vistas.pantallas.perfil;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import project.catalin.mybets.R;
import project.catalin.mybets.controladores.comunicacionVista.ViewDatosPropios;
import project.catalin.mybets.controladores.controladoresPantallas.ControladorDatosPropios;
import project.catalin.mybets.controladores.utils.comunicación.Constantes;
import project.catalin.mybets.datos.dataObjects.Persona;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerDatosPropios;

public class EditarPerfil extends AppCompatActivity implements ViewDatosPropios {

    private static final int ACCION_SELECCIONAR_FOTO = 0;
    private CircleImageView mImagenPerfil;
    private EditText mInputNombre;
    private EditText mInputUsername;
    private EditText mInputEmail;
    private EditText mInputPassword;
    private TextView mBotonGuardar;
    private ImageView mBotonCerrar;
    private ControllerDatosPropios mControladorDatosPropios;
    private ProgressDialog mDialogLoadingPartidas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_perfil);

        inicializarComponentes();
        inicializarBotones();
        inicializarControladores();

    }

    private void inicializarControladores() {
        mControladorDatosPropios = new ControladorDatosPropios(this);
        mControladorDatosPropios.initVista();
    }


    private void inicializarBotones() {
        mBotonCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mBotonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mControladorDatosPropios.guardarEdicionPerfil();
            }
        });

        mImagenPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(EditarPerfil.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
                    ActivityCompat.requestPermissions(EditarPerfil.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constantes.REQUEST_PERMISSION_CODE_FOTO_PICKER);
                else startPhotoPicker();
            }
        });
    }

    private void inicializarComponentes() {
        mImagenPerfil = (CircleImageView) findViewById(R.id.editar_perfil_icon_foto_placeholder);
        mInputNombre = (EditText) findViewById(R.id.editar_perfil_text_nombre);
        mInputUsername = (EditText) findViewById(R.id.editar_perfil_text_username);
        mInputEmail = (EditText) findViewById(R.id.editar_perfil_text_email);
        mInputPassword = (EditText) findViewById(R.id.editar_perfil_text_password);
        mBotonGuardar = (TextView) findViewById(R.id.editar_perfil_boton_guardar);
        mBotonCerrar = (ImageView) findViewById(R.id.editar_perfil_boton_cerrar);

        int gris = getResources().getColor(R.color.gris_medio);
        mInputUsername.getBackground().setColorFilter(gris, PorterDuff.Mode.SRC_IN);
        mInputPassword.getBackground().setColorFilter(gris, PorterDuff.Mode.SRC_IN);
        mInputEmail.getBackground().setColorFilter(gris, PorterDuff.Mode.SRC_IN);
        mInputNombre.getBackground().setColorFilter(gris, PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            startPhotoPicker();

    }

    private void startPhotoPicker() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, ACCION_SELECCIONAR_FOTO);
    }

    @Override
    public void showLoadingUsuario() {
        mDialogLoadingPartidas = ProgressDialog.show(this, "", "Cargando datos del usuario...", false, false);
        mDialogLoadingPartidas.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                InputStream streamImagen = getContentResolver().openInputStream(data.getData());
                Bitmap bitmapSeleccionado = BitmapFactory.decodeStream(streamImagen);
                mImagenPerfil.setImageBitmap(bitmapSeleccionado);
            } catch (FileNotFoundException ignored) {
                alert("Ha ocurrido un error");
            }
        }
    }

    @Override
    public void alert(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    @Override
    public void dismissLoadingUsuario() {
        mDialogLoadingPartidas.dismiss();
    }

    @Override
    public void setDatosUsuario(Persona persona) {
        mInputNombre.setHint(persona.getNombre());
        mInputEmail.setHint(persona.getEmail());
        mInputUsername.setHint(persona.getUsername());
        Picasso.with(this)
                .load(persona.getImagen())
                .into(mImagenPerfil);
    }

    @Override
    public byte[] getDataIconImagen() {
        BitmapDrawable drawableData = (BitmapDrawable) mImagenPerfil.getDrawable();
        Bitmap bitmapData = drawableData.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmapData.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    @Override
    public String getValorCampoNombre() {
        return mInputNombre.getText().toString();
    }

    @Override
    public String getValorCampoUsername() {
        return mInputUsername.getText().toString();
    }
}
