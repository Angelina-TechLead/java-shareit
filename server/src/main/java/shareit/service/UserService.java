package shareit.service;

import shareit.dto.UserDto;

public interface UserService {
    UserDto create(UserDto userDto);
    UserDto update(Long id, UserDto userDto);
    void delete(Long id);
    UserDto getById(Long id);
}
