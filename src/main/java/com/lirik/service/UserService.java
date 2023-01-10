package com.lirik.service;

import com.lirik.dao.UserRepository;
import com.lirik.dto.UserCreateDto;
import com.lirik.dto.UserReadDto;
import com.lirik.entity.users.User;
import com.lirik.mapper.Mapper;
import com.lirik.mapper.UserCreateMapper;
import com.lirik.mapper.UserReadMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.graph.GraphSemantic;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateMapper userCreateMapper;

    @Transactional
    public Long create(UserCreateDto userDto) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<UserCreateDto>> validationResult = validator.validate(userDto);
        if (!validationResult.isEmpty()) {
            throw new ConstraintViolationException(validationResult);
        }
        User userEntity = userCreateMapper.mapFrom(userDto);
        return userRepository.save(userEntity).getId();

    }

    @Transactional
    public <T> Optional<T> findById(Long id, Mapper<User, T> mapper) {
        Map<String, Object> properties = Map.of(
                GraphSemantic.LOAD.getJakartaHintName(), userRepository.getEntityManager().getEntityGraph("WithCompany")
        );
        return userRepository.findById(id, properties)
                             .map(mapper::mapFrom);
    }

    @Transactional
    public Optional<UserReadDto> findById(Long id) {
        return findById(id, userReadMapper);
    }

    public boolean delete(Long id) {
        Optional<User> maybeUser = userRepository.findById(id);
        maybeUser.ifPresent(user -> userRepository.delete(user.getId()));
        return maybeUser.isPresent();
    }
}
