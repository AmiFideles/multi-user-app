package com.itmo.programming.service;


import com.itmo.programming.dto.UserDTO;
import com.itmo.programming.encryption.PasswordEncryptionException;
import com.itmo.programming.encryption.SHA512Generator;
import com.itmo.programming.mapper.todto.UserMapper;
import com.itmo.programming.mapper.toentity.UserDTOMapper;
import com.itmo.programming.model.User;
import com.itmo.programming.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
public class UserService {
    private final SHA512Generator sha512Generator;
    private final UserRepository userRepository;

    public UserService(SHA512Generator sha512Generator, UserRepository userRepository) {
        this.sha512Generator = sha512Generator;
        this.userRepository = userRepository;
    }

    @Transactional
    public boolean create(UserDTO userDTO, String salt) {
        Optional<UserDTO> maybeUser = getByLogin(userDTO.getLogin());
        if (maybeUser.isPresent()){
            return false;
        }else {
            User user = UserDTOMapper.INSTANCE.toUser(userDTO);
            user.setSalt(salt);
            userRepository.save(user);
            return true;
        }
    }

    @Transactional
    public Optional<UserDTO> findById(Long id) {
        return userRepository.findById(id).map(UserMapper.INSTANCE::toDTO);
    }

    @Transactional
    public boolean remove(Long id) {
        Optional<User> maybeUser = userRepository.findById(id);
        maybeUser.ifPresent(user -> userRepository.delete(user.getId()));
        return maybeUser.isPresent();
    }

    @Transactional
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public Optional<UserDTO> getByLogin(String login) {
        List<User> personList = userRepository.findByLogin(login);
        return personList.stream().map(UserMapper.INSTANCE::toDTO).findFirst();
    }

    @Transactional
    public boolean exists(UserDTO userDTO) throws PasswordEncryptionException {
        List<User> byLogin = userRepository.findByLogin(userDTO.getLogin());
        if (byLogin.isEmpty()) return false;
        User user = byLogin.get(0);
        String salt = user.getSalt();
        String maybeRealPassword = sha512Generator.getSHA512SecurePassword(userDTO.getPassword(), salt);
        return user.getPassword().equals(maybeRealPassword);
    }


}
