package spotify.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import spotify.model.UsuarioNormal;
import spotify.service.UsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

  @Autowired
  UsuarioService usuarioService;

  @RequestMapping
  public String listarUsuarios(ModelMap model,
      @RequestParam(value = "message", required = false) String message) {
    List<UsuarioNormal> usuarios = usuarioService.getAll();
    model.addAttribute("usuarios", usuarios);
    model.addAttribute("message", message);
    return "usuario/list";
  }
  
  @RequestMapping("/{idUsuarioNormal}")
  public String editarUsuario(ModelMap model, @PathVariable("idUsuarioNormal") String codigo) {
    UsuarioNormal usuario = usuarioService.getById_usuario(codigo);
    model.addAttribute("usuario", usuario);
    model.addAttribute("fullName", String.format("%s/%s, %s", usuario.getApellidoPaterno(),
        usuario.getApellidoMaterno(), usuario.getNombre()));
    return "usuario/editar";
  }

  @RequestMapping("/new")
  public String nuevoUsuario(ModelMap model) {
    UsuarioNormal usuario = new UsuarioNormal();
    model.addAttribute("usuario", usuario);
    model.addAttribute("title", "Nuevo Usuario");	
    return "usuario/editar";
  }

  @RequestMapping(value = "/save", method = RequestMethod.POST)
  public String saveUsuario(@ModelAttribute UsuarioNormal usuarioChanged, ModelMap model) {
    usuarioService.save(usuarioChanged);
    return "redirect:/usuario?message=El Usuario se actualizo correctamente";
    // return listarUsuarios(model);
  }
  
  @RequestMapping("/login")
  public String Login(ModelMap model) {
	  return "usuario/login";
  }

  @RequestMapping("/register")
  public String Register(ModelMap model) {
	  return "usuario/register";
  }
}