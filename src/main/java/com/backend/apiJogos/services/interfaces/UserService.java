package com.backend.apiJogos.services.interfaces;

import java.util.List;
import org.springframework.security.oauth2.jwt.Jwt;

import com.backend.apiJogos.dtos.RoleUpdateDto;
import com.backend.apiJogos.dtos.UserDto;
import com.backend.apiJogos.dtos.UserPublicDto;

public interface UserService {
    UserDto me(Jwt jwt);
    UserDto atualizarMeuPerfil(Jwt jwt, UserDto dto);
    void deletarMinhaConta(Jwt jwt);
    UserDto alterarRole(Long id, RoleUpdateDto dto, Jwt jwt);
    List<UserDto> listar(Jwt jwt);
    List<UserPublicDto> buscarPorNome(String nome, Jwt jwt);
}
