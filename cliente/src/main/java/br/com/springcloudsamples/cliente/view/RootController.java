package br.com.springcloudsamples.cliente.view;

import br.com.springcloudsamples.cliente.domain.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zozfabio on 23/09/15.
 */
@Controller
@RequestMapping("/")
class RootController {

    @Autowired
    private UsuarioRepository repository;

    @RequestMapping
    private String home(Model model) {
        model.addAttribute("usuarios", repository.findAll());
        model.addAttribute("usuario1", repository.findOne(1L));

        return "home";
    }
}
