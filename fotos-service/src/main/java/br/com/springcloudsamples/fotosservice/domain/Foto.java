package br.com.springcloudsamples.fotosservice.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * Created by zozfabio on 15/09/15.
 */
@Entity
public class Foto implements Serializable {

    private static final byte serialVersionUID = 1;

    @Id
    private Long usuario;

    @Column(nullable = false, length = 9999999)
    private byte[] conteudo;

    protected Foto() {
    }

    private Foto(Long usuario, byte[] conteudo) {
        this.usuario = usuario;
        this.conteudo = conteudo;
    }

    public static Foto of(Long usuario, byte[] conteudo) {
        return new Foto(usuario, conteudo);
    }

    public Long getUsuario() {
        return usuario;
    }

    public byte[] getConteudo() {
        return conteudo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == null) return false;
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
        sb.append(", conteudo=").append(Arrays.toString(conteudo));
        sb.append('}');
        return sb.toString();
    }
}
