package com.backend.apiJogos.services.impls;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.backend.apiJogos.dtos.UserDto;
import com.backend.apiJogos.exceptionHandler.exceptions.UserJaCadastradoException;
import com.backend.apiJogos.exceptionHandler.exceptions.UserNotFoundException;
import com.backend.apiJogos.exceptionHandler.exceptions.InvalidUserDataException;
import com.backend.apiJogos.models.User;
import com.backend.apiJogos.repositorys.UserRepository;
import com.backend.apiJogos.services.interfaces.UserService;

@Service
public class UserServiceImpl implements UserService{
  private final UserRepository userRepository;

 public UserServiceImpl(UserRepository userRepository){
    this.userRepository = userRepository;
 }

  @Override
  public UserDto criarUsuario(UserDto userDto){
    if(userDto == null || userDto.getNome() == null || userDto.getNome().trim().isEmpty()){
      throw new InvalidUserDataException("Nome do usuário não pode ser nulo ou vazio");
    }

    if(userRepository.existsByNome(userDto.getNome().trim())){
      throw new UserJaCadastradoException();
    }

    User user = new User(userDto.getNome().trim());
    userRepository.save(user);

    return new UserDto(user.getId(), user.getNome());
  }

  @Override
  public List<UserDto> listarUsuarios(){
    return userRepository.findAll()
       .stream()
       .map(user -> new UserDto(user.getId(), user.getNome()))
       .collect(Collectors.toList());
}

 @Override
 public UserDto buscarPorId(Long id){
    User user = userRepository.findById(id)
          .orElseThrow(() -> new UserNotFoundException());
    
     return new UserDto(user.getId(), user.getNome());
 } 

 @Override
 public void deletarUsuario(Long id){
     if(id == null){
       throw new InvalidUserDataException("ID do usuário não pode ser nulo");
     }
     
     if(!userRepository.existsById(id)){
       throw new UserNotFoundException();
     }
     
     userRepository.deleteById(id);
 }

@Override
public UserDto editarPorId(UserDto userDto, Long id) {
    if(id == null){
      throw new InvalidUserDataException("ID do usuário não pode ser nulo");
    }
    
    if(userDto == null || userDto.getNome() == null || userDto.getNome().trim().isEmpty()){
      throw new InvalidUserDataException("Nome do usuário não pode ser nulo ou vazio");
    }
    
    User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException());

    if(!user.getNome().equals(userDto.getNome().trim()) && userRepository.existsByNome(userDto.getNome().trim())){
      throw new UserJaCadastradoException();
    }

    user.setNome(userDto.getNome().trim());
    userRepository.save(user);

    return new UserDto(user.getId(), user.getNome());
}
  
@Override
public List<UserDto> buscarPorNome(String nome) {
   if(nome == null || nome.trim().isEmpty()){
     throw new InvalidUserDataException("Nome de busca não pode ser nulo ou vazio");
   }
   
   return userRepository.findByNomeContaining(nome.trim())
    .stream()
    .map(
      user -> new UserDto(user.getId(),user.getNome())).collect(Collectors.toList()
    );
}
}
