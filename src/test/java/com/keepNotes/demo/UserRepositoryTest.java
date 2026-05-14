package com.keepNotes.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.keepNotes.demo.entity.UserEntity;
import com.keepNotes.demo.repository.UserRepository;

@SpringBootTest(properties = {
    "spring.datasource.url=jdbc:h2:mem:userrepositorytest;DB_CLOSE_DELAY=-1",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void createUser_savesUser() {
        String email = "vaishnavi-" + UUID.randomUUID() + "@example.com";
        UserEntity user = new UserEntity("Vaishnavi", email);

        UserEntity savedUser = userRepository.save(user);

        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getUserName()).isEqualTo("Vaishnavi");
        assertThat(savedUser.getUserEmail()).isEqualTo(email);
    }

    @Test
    void findByUserEmail_returnsUser() {
        String email = "findme-" + UUID.randomUUID() + "@example.com";
        userRepository.save(new UserEntity("Vaishnavi", email));

        Optional<UserEntity> foundUser = userRepository.findByUserEmail(email);

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUserName()).isEqualTo("Vaishnavi");
    }

    @Test
    void updateUser_changesUserName() {
        String email = "update-" + UUID.randomUUID() + "@example.com";
        UserEntity savedUser = userRepository.save(new UserEntity("Old Name", email));

        savedUser.setUserName("New Name");
        userRepository.save(savedUser);

        Optional<UserEntity> updatedUser = userRepository.findByUserEmail(email);

        assertThat(updatedUser).isPresent();
        assertThat(updatedUser.get().getUserName()).isEqualTo("New Name");
    }

    @Test
    void deleteUser_removesUser() {
        String email = "delete-" + UUID.randomUUID() + "@example.com";
        UserEntity savedUser = userRepository.save(new UserEntity("Delete Me", email));

        userRepository.deleteById(savedUser.getId());

        Optional<UserEntity> deletedUser = userRepository.findById(savedUser.getId());
        assertThat(deletedUser).isEmpty();
    }
}