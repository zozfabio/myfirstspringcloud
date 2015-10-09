package br.com.springcloudsamples.fotosservice.domain;

import org.springframework.data.repository.Repository;

import java.util.Optional;

/**
 * Created by zozfabio on 16/09/15.
 */
public interface FotosRepository extends Repository<Foto, Long> {

    Optional<Foto> findOne(Long usuario);

    Foto save(Foto foto);
}
