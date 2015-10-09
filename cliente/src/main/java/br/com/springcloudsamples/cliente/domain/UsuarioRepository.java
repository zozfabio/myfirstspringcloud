package br.com.springcloudsamples.cliente.domain;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by zozfabio on 29/09/15.
 */
@FeignClient("USUARIOS-SERVICE")
public interface UsuarioRepository {

    @RequestMapping(method = GET, value = "/")
    PagedResources<Resource<Usuario>> findAll();

    @RequestMapping(method = GET, value = "/{id}")
    Resource<Usuario> findOne(@PathVariable("id") Long id);
}
