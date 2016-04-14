package project.catalin.mybets.datos.utils;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Catalin on 07/04/2016.
 */
public class EncryptionUtilsTest {

    @Test
    public void textoAMd5(){
        String texto = "123";
        String md5 = "202cb962ac59075b964b07152d234b70";
        String encriptación = EncryptionUtils.toMD5(texto);

        assertTrue(encriptación.equals(md5));
    }
}
