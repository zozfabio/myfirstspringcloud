package br.com.springcloudsamples.usuariosservice.view;

import br.com.springcloudsamples.usuariosservice.domain.Usuario;
import br.com.springcloudsamples.usuariosservice.domain.UsuarioRepository;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;

/**
 * Created by zozfabio on 22/09/15.
 */
@RestController
@ExposesResourceFor(Usuario.class)
@RequestMapping("/")
class UsuarioController
{
    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private UsuarioResourceAssembler usuarioResourceAssembler;

    @Autowired
    private PagedResourcesAssembler<Usuario> pagedResourcesAssembler;

    @RequestMapping(produces = "application/hal+json;charset=UTF-8")
    private ResponseEntity<?> findAll(Pageable pageable)
    {
        return ok(pagedResourcesAssembler.toResource(repository.findAll(pageable), usuarioResourceAssembler));
    }

    @RequestMapping(value = "/{id}", produces = "application/hal+json;charset=UTF-8")
    private ResponseEntity<?> findOne(@PathVariable Long id)
    {
        Optional<Usuario> foto = repository.findOne(id);
        if (foto.isPresent())
            return foto.map(usuarioResourceAssembler::toResource).map(ResponseEntity::ok).get();
        return notFound().build();
    }
}

/**
 * Created by zozfabio on 23/09/15.
 */
@Component
class UsuarioResourceAssembler implements ResourceAssembler<Usuario, Resource<Usuario>> {

    @Autowired
    private Set<ResourceProcessor<Resource<Usuario>>> processors = Collections.emptySet();

    @Override
    public Resource<Usuario> toResource(Usuario entity) {
        Resource<Usuario> resource = new Resource<>(entity);

        resource.add(new Link(fromPath("/{id}").buildAndExpand(entity.getId()).toUriString(), "self"));

        processors.forEach(p -> p.process(resource));

        return resource;
    }
}

/**
 * Created by zozfabio on 23/09/15.
 */
@Component
class UsuarioResourceFotoProcessor implements ResourceProcessor<Resource<Usuario>> {

    @Autowired
    private DiscoveryClient discoveryClient;

    public Resource<Usuario> processNull(ResourceSupport resource) {
        return null;
    }

    @Override
    @HystrixCommand(commandKey = "usuarioResource.addFotoLink", fallbackMethod = "processNull")
    public Resource<Usuario> process(Resource<Usuario> resource) {
        InstanceInfo fotosService = discoveryClient.getNextServerFromEureka("FOTOS-SERVICE", false);

        resource.add(new Link(fromHttpUrl(fotosService.getHomePageUrl() + "/{usuario}")
                .buildAndExpand(resource.getContent().getId()).toUriString(), "foto"));

        return resource;
    }
}