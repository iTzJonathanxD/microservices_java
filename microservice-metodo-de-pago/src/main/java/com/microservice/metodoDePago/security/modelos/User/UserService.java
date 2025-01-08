package com.microservice.metodoDePago.security.modelos.User;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResponse updateUser(UserRequest userRequest) {
        User user = new User(
                userRequest.id,
                null,
                userRequest.getFirstname(),
                null,
                Role.USER
        );

        userRepository.updateUser(user.getId(), user.getFirstname());
        return new UserResponse("El usuario se actualizó satisfactoriamente");
    }

    public UserDTO getUser(Integer id) {
        User user = userRepository.findById(id).orElse(null);

        if (user != null) {
            return new UserDTO(
                    user.getId(),
                    user.getUsername(),
                    user.getFirstname()
            );
        }

        return null; // Si no se encuentra el usuario, retorna null
    }
}
