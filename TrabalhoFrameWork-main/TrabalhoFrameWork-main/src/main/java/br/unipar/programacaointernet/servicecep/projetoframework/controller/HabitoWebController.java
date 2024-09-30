package br.unipar.programacaointernet.servicecep.projetoframework.controller;

import br.unipar.programacaointernet.servicecep.projetoframework.model.Habito;
import br.unipar.programacaointernet.servicecep.projetoframework.model.Usuario;
import br.unipar.programacaointernet.servicecep.projetoframework.service.HabitoService;
import br.unipar.programacaointernet.servicecep.projetoframework.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class HabitoWebController {

    private final HabitoService habitoService;
    private final UsuarioService usuarioService;

    public HabitoWebController(HabitoService habitoService, UsuarioService usuarioService) {
        this.habitoService = habitoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/historico")
    public String getAllHistorico(Model model, HttpSession session) {
        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        if (usuarioId != null) {
            List<Habito> habitos = habitoService.getAllHabitoPorUsuarioId(usuarioId);
            model.addAttribute("historico", habitos);
        }else{
            model.addAttribute("historico", List.of());
        }

        return "historico";
    }

    @PostMapping(path = "/habito/save")
    public String saveHabito(Habito habito, HttpSession session) {
        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        if (usuarioId != null) {
            Usuario usuario = usuarioService.getUsuarioById(usuarioId);
            habito.setUsuario(usuario);
            habitoService.save(habito);
        }
        return "redirect:/todasTarefas";
    }

    @GetMapping("/habitos")
    public String habito() {
        return "habitos";
    }

}
