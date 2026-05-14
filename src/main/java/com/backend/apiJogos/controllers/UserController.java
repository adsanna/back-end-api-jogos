package com.backend.apiJogos.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.backend.apiJogos.dtos.UserDto;
import com.backend.apiJogos.services.impls.UserServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userServiceImpl){
    this.userService = userServiceImpl;
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody @Valid UserDto userDto, BindingResult bd){
        if(bd.hasErrors()){
            return ResponseEntity.badRequest().body(bd.getAllErrors());
        }
        UserDto userSalvo = userService.criarUsuario(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userSalvo);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> listar(){
        List<UserDto> userDtos = userService.listarUsuarios();
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("/buscar-id/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){
       UserDto UserDto = userService.buscarPorId(id);
       return ResponseEntity.ok().body(UserDto);
  }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id){
        userService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

  @PutMapping("/{id}")
  public ResponseEntity<?> editar(@RequestBody @Valid UserDto uDto, BindingResult br, @PathVariable Long id){
    if(br.hasErrors()){
      return ResponseEntity.badRequest().body(br.getAllErrors());
    }
    UserDto userEditado = userService.editarPorId(uDto, id);
    return ResponseEntity.status(HttpStatus.OK).body(userEditado);
  }

  @GetMapping("/buscar-nome/{nome}")
  public ResponseEntity<List<?>> buscarPorNome(@PathVariable String nome){
    List<UserDto> listaAproximada = userService.buscarPorNome(nome);
    return ResponseEntity.ok().body(listaAproximada);

  }








}
