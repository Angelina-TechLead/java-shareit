package shareit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shareit.dto.UserDto;
import shareit.model.User;
import shareit.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDto create(UserDto userDto) {
        User user = new User(null, userDto.getName(), userDto.getEmail());

        userRepository.save(user);
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }

    @Override
    public UserDto getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден!"));
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }

    @Override
    public UserDto update(Long id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден!"));

        if (userDto.getName() != null && !userDto.getName().isBlank()) {
            user.setName(userDto.getName());
        }
        if (userDto.getEmail() != null && !userDto.getEmail().isBlank()) {
            user.setEmail(userDto.getEmail());
        }

        userRepository.save(user);
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден!"));

        userRepository.delete(user);
    }
}
