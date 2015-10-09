package br.com.springcloudsamples.fotosservice.view;

import br.com.springcloudsamples.fotosservice.domain.Foto;
import br.com.springcloudsamples.fotosservice.domain.FotosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;

/**
 * Created by zozfabio on 15/09/15.
 */
@RestController
@ExposesResourceFor(Foto.class)
@RequestMapping("/")
class FotoController
{
    @Autowired
    private FotosRepository repository;

    @Autowired
    private FotoResourceAssembler fotoResourceAssembler;

    @RequestMapping(value = "/{usuario}", produces = "application/hal+json;charset=UTF-8")
    private ResponseEntity<?> findOne(@PathVariable Long usuario)
    {
        Optional<Foto> foto = repository.findOne(usuario);
        if (foto.isPresent())
            return foto.map(fotoResourceAssembler::toResource).map(ResponseEntity::ok).get();
        return notFound().build();
    }

    @RequestMapping(value = "/{usuario}", method = POST)
    private ResponseEntity<?> save(@PathVariable Long usuario, @RequestParam MultipartFile file) throws IOException
    {
        Foto foto = repository.save(Foto.of(usuario, file.getBytes()));
        return created(fromPath("/fotos/{usuario}").buildAndExpand(foto.getUsuario()).toUri())
                .body("Foto salva com sucesso!");
    }

    @Component
    private static class FotoResourceAssembler implements ResourceAssembler<Foto, Resource<Foto>> {

        @Override
        public Resource<Foto> toResource(Foto entity) {
            Resource<Foto> resource = new Resource<>(entity);

            resource.add(new Link(fromPath("/{usuario}").buildAndExpand(entity.getUsuario()).toUriString(), "self"));

            return resource;
        }
    }
}
