package br.com.springcloudsamples.cliente.domain;

import java.util.Objects;

/**
 * Created by zozfabio on 30/09/15.
 */
public class Foto {

    private Long usuario;

    private byte[] conteudo;

    protected Foto() {
    }

    public byte[] getConteudo() {
        return conteudo;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (this == o) return true;
        if (!(o instanceof Foto)) return false;
        Foto foto = (Foto) o;
        return Objects.equals(usuario, foto.usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuario);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Foto{");
        sb.append("usuario=").append(usuario);
        sb.append('}');
        return sb.toString();
    }
}
