package com.backend.apiJogos.services.impls;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.backend.apiJogos.dtos.UserGameDto;
import com.backend.apiJogos.exceptionHandler.exceptions.DateException;
import com.backend.apiJogos.exceptionHandler.exceptions.GameNotFoundException;
import com.backend.apiJogos.exceptionHandler.exceptions.TimeException;
import com.backend.apiJogos.exceptionHandler.exceptions.RunInProgressException;
import com.backend.apiJogos.exceptionHandler.exceptions.RunNotFoundException;
import com.backend.apiJogos.exceptionHandler.exceptions.StatusException;
import com.backend.apiJogos.models.Game;
import com.backend.apiJogos.models.Status;
import com.backend.apiJogos.models.UserGame;
import com.backend.apiJogos.repositorys.GameRepository;
import com.backend.apiJogos.repositorys.UserGameRepository;
import com.backend.apiJogos.services.interfaces.UserGameService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserGameServiceImpl implements UserGameService {

  private final UserGameRepository repo;
  private final GameRepository gameRepo;

  @Override
  public UserGameDto criar(UserGameDto dto, Jwt jwt) {

    String supabaseUserId = jwt.getSubject();
    
    Game game = gameRepo
        .findById(dto.getGameId())
        .orElseThrow(() -> new GameNotFoundException("Game não encontrado"));

    if (dto.getStatus() == Status.JOGANDO
        && repo.existsBySupabaseUserIdAndGameIdAndStatus(
            supabaseUserId,
            game.getId(),
            Status.JOGANDO)) {

      throw new RunInProgressException();
    }

    UserGameDto normalized = normalizarCreate(dto);

    validarRegras(normalized);

    int numeroRun = repo
        .findTopBySupabaseUserIdAndGameIdOrderByNumeroRunDesc(
            supabaseUserId,
            game.getId())
        .map(r -> r.getNumeroRun() + 1)
        .orElse(1);

    UserGame entity = new UserGame();

    entity.setSupabaseUserId(supabaseUserId);
    entity.setGame(game);
    entity.setNumeroRun(numeroRun);
    entity.setStatus(normalized.getStatus());
    entity.setHorasJogadas(normalized.getHorasJogadas());
    entity.setDataInicio(normalized.getDataInicio());
    entity.setDataFim(normalized.getDataFim());

    return map(repo.save(entity));
  }

  @Override
  public List<UserGameDto> listar(Jwt jwt) {

    return repo.findBySupabaseUserId(jwt.getSubject())
        .stream()
        .map(this::map)
        .collect(Collectors.toList());
  }

  @Override
  public UserGameDto buscarPorId(Long id, Jwt jwt) {

    return map(
        repo.findByIdAndSupabaseUserId(
            id,
            jwt.getSubject())
            .orElseThrow(RunNotFoundException::new));
  }

  @Override
  public UserGameDto editar(Long id, UserGameDto dto, Jwt jwt) {

    UserGame entity = repo
        .findByIdAndSupabaseUserId(
            id,
            jwt.getSubject())
        .orElseThrow(RunNotFoundException::new);

    if (entity.getStatus() == Status.FINALIZADO
        && dto.getHorasJogadas() != null
        && entity.getHorasJogadas() != null
        && dto.getHorasJogadas().compareTo(entity.getHorasJogadas()) != 0) {

      throw new TimeException(
          "Horas não podem ser alteradas após FINALIZADO");
    }

    if (Status.JOGANDO.equals(dto.getStatus())
        && entity.getStatus() != Status.JOGANDO
        && repo.existsBySupabaseUserIdAndGameIdAndStatus(
            entity.getSupabaseUserId(),
            entity.getGame().getId(),
            Status.JOGANDO)) {

      throw new RunInProgressException();
    }

    validarTransicao(
        entity.getStatus(),
        dto.getStatus());

    UserGameDto finalDto = estadoEfetivo(dto, entity);

    validarRegras(finalDto);

    entity.setStatus(finalDto.getStatus());
    entity.setHorasJogadas(finalDto.getHorasJogadas());
    entity.setDataInicio(finalDto.getDataInicio());
    entity.setDataFim(finalDto.getDataFim());

    return map(repo.save(entity));
  }

  @Override
  public void deletar(Long id, Jwt jwt) {

    UserGame entity = repo
        .findByIdAndSupabaseUserId(
            id,
            jwt.getSubject())
        .orElseThrow(RunNotFoundException::new);

    repo.delete(entity);
  }

  private void validarRegras(UserGameDto dto) {

    if (dto.getHorasJogadas() != null
        && dto.getHorasJogadas().compareTo(BigDecimal.ZERO) < 0) {

      throw new TimeException(
          "Horas não podem ser negativas");
    }

    if (dto.getDataInicio() != null
        && dto.getDataFim() != null
        && dto.getDataFim().isBefore(dto.getDataInicio())) {

      throw new DateException(
          "dataFim não pode ser antes da dataInicio");
    }

    switch (dto.getStatus()) {

      case BACKLOG -> {
        if (dto.getDataInicio() != null
            || dto.getDataFim() != null
            || dto.getHorasJogadas() != null) {

          throw new StatusException(
              "BACKLOG não pode ter datas ou horas");
        }
      }

      case JOGANDO -> {
        if (dto.getDataInicio() == null) {
          throw new StatusException(
              "JOGANDO precisa de dataInicio");
        }

        if (dto.getDataFim() != null) {
          throw new StatusException(
              "JOGANDO não pode ter dataFim");
        }
      }

      case FINALIZADO -> {

        if (dto.getDataInicio() == null
            || dto.getDataFim() == null) {

          throw new StatusException(
              "FINALIZADO precisa de datas");
        }

        if (dto.getHorasJogadas() == null
            || dto.getHorasJogadas().compareTo(BigDecimal.ZERO) <= 0) {

          throw new StatusException(
              "FINALIZADO precisa de horas > 0");
        }
      }

      case DROPADO -> {

        if (dto.getHorasJogadas() != null
            && dto.getHorasJogadas().compareTo(BigDecimal.ZERO) < 0) {

          throw new StatusException(
              "DROPADO não aceita horas negativas");
        }
      }
    }
  }

  private void validarTransicao(Status atual, Status novo) {

    if (atual == novo)
      return;

    switch (atual) {

      case BACKLOG -> {
        if (novo != Status.JOGANDO) {
          throw new StatusException(
              "BACKLOG só pode ir para JOGANDO");
        }
      }

      case JOGANDO -> {
        if (novo != Status.FINALIZADO
            && novo != Status.DROPADO) {

          throw new StatusException(
              "JOGANDO só pode ir para FINALIZADO ou DROPADO");
        }
      }

      case FINALIZADO ->
        throw new StatusException(
            "Não é permitido alterar após FINALIZADO");

      case DROPADO -> {
        if (novo != Status.JOGANDO) {
          throw new StatusException(
              "DROPADO só pode voltar para JOGANDO");
        }
      }
    }
  }

  private UserGameDto normalizarCreate(UserGameDto dto) {

    if (dto.getStatus() == Status.BACKLOG) {
      dto.setHorasJogadas(null);
      dto.setDataInicio(null);
      dto.setDataFim(null);
    }

    if (dto.getStatus() == Status.JOGANDO
        && dto.getDataFim() != null) {

      throw new StatusException(
          "JOGANDO não pode ter dataFim");
    }

    return dto;
  }

  private UserGameDto estadoEfetivo(
      UserGameDto dto,
      UserGame atual) {

    return new UserGameDto(
        atual.getId(),
        atual.getGame().getId(),
        dto.getStatus(),
        dto.getHorasJogadas(),
        dto.getDataInicio(),
        dto.getDataFim());
  }

  private UserGameDto map(UserGame e) {

    return new UserGameDto(
        e.getId(),
        e.getGame().getId(),
        e.getStatus(),
        e.getHorasJogadas(),
        e.getDataInicio(),
        e.getDataFim());
  }
}
