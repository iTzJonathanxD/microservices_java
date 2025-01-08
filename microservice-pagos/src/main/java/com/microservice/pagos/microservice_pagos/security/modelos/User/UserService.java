package com.microservice.pagos.microservice_pagos.security.modelos.User;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResponse updateUser(UserRequest userRequest) {
        // Crear un nuevo objeto User con los datos del request
        User user = new User(
                userRequest.id, // ID del usuario
                null, // username no se modifica
                userRequest.getFirstname(), // Actualiza el firstname
                null, // password no se modifica
                Role.USER // Rol asignado por defecto
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
