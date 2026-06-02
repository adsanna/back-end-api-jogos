package com.backend.apiJogos.services.impls;

import java.util.List;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.backend.apiJogos.config.security.UserContext;
import com.backend.apiJogos.dtos.RoleUpdateDto;
import com.backend.apiJogos.dtos.UserDto;
import com.backend.apiJogos.exceptionHandler.exceptions.AccessDeniedException;
import com.backend.apiJogos.exceptionHandler.exceptions.InvalidUserDataException;
import com.backend.apiJogos.dtos.UserPublicDto;
import com.backend.apiJogos.exceptionHandler.exceptions.UserNotFoundException;
import com.backend.apiJogos.models.Role;
import com.backend.apiJogos.models.User;
import com.backend.apiJogos.repositorys.UserRepository;
import com.backend.apiJogos.services.interfaces.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserContext userContext;

  @Override
  public UserDto me(Jwt jwt) {

    String supabaseId = jwt.getSubject();

    User user = userRepository
        .findBySupabaseUserId(supabaseId)
        .orElseGet(() -> {

          User novo = new User();

          novo.setSupabaseUserId(supabaseId);

          String email = jwt.getClaimAsString("email");

          String nome = (email != null && !email.isBlank())
              ? email.split("@")[0]
              : supabaseId.substring(0, 8);

          novo.setNome(nome);

          novo.setRole(Role.USER);

          return userRepository.save(novo);
        });

    return new UserDto(
        user.getId(),
        user.getNome(),
        user.getSupabaseUserId(),
        user.getRole());
  }

  @Override
  public UserDto atualizarMeuPerfil(Jwt jwt, UserDto dto) {

    if (dto.getNome() == null || dto.getNome().trim().isEmpty()) {
      throw new InvalidUserDataException(
          "Nome não pode ser vazio");
    }

    String supabaseId = jwt.getSubject();

    User user = userRepository
        .findBySupabaseUserId(supabaseId)
        .orElseThrow(UserNotFoundException::new);

    user.setNome(dto.getNome().trim());

    userRepository.save(user);

    return new UserDto(
        user.getId(),
        user.getNome(),
        user.getSupabaseUserId(),
        user.getRole());
  }

  @Override
  public void deletarMinhaConta(Jwt jwt) {

    String supabaseId = jwt.getSubject();

    User user = userRepository
        .findBySupabaseUserId(supabaseId)
        .orElseThrow(UserNotFoundException::new);

    userRepository.delete(user);
  }

  @Override
  public UserDto alterarRole(
      Long id,
      RoleUpdateDto dto,
      Jwt jwt) {

    userContext.validarAdmin(jwt);

    User adminAtual = userContext.getUser(jwt);

    if (adminAtual.getId().equals(id)
        && dto.getRole() == Role.USER) {

      throw new AccessDeniedException(
          "Você não pode remover seu próprio acesso administrativo");
    }

    User user = userRepository
        .findById(id)
        .orElseThrow(UserNotFoundException::new);

    user.setRole(dto.getRole());

    userRepository.save(user);

    return new UserDto(
        user.getId(),
        user.getNome(),
        user.getSupabaseUserId(),
        user.getRole());
  }

  @Override
  public List<UserDto> listar(Jwt jwt) {
    userContext.validarAdmin(jwt);
    return userRepository.findAll()
        .stream()
        .map(user -> new UserDto(
            user.getId(),
            user.getNome(),
            user.getSupabaseUserId(),
            user.getRole()))
        .toList();
  }

  @Override
  public List<UserPublicDto> buscarPorNome(String nome, Jwt jwt) {
    return userRepository
        .findByNomeContainingIgnoreCase(nome)
        .stream()
        .map(user -> new UserPublicDto(
            user.getId(),
            user.getNome()))
        .toList();
  }
}
