package br.com.springcloudsamples.usuariosservice.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.util.Optional;

/**
 * Created by zozfabio on 11/09/15.
 */
public interface UsuarioRepository extends Repository<Usuario, Long> {

    Page<Usuario> findAll(Pageable pageable);

    Optional<Usuario> findOne(Long id);
}
