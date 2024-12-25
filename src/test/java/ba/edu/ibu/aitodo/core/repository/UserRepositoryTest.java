package ba.edu.ibu.aitodo.core.repository;

import ba.edu.ibu.aitodo.core.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindUserByEmail_UserExists() {
        // Given
        String email = "test@example.com";
        User testUser = new User();
        testUser.setEmail(email);

        // Mock behavior
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(testUser));

        // When
        Optional<User> foundUser = userRepository.findUserByEmail(email);

        // Then
        assertTrue(foundUser.isPresent(), "User should be found");
        assertEquals(email, foundUser.get().getEmail(), "Email should match");
    }

    @Test
    void testFindUserByEmail_UserDoesNotExist() {
        // Given
        String email = "nonexistent@example.com";

        // Mock behavior
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.empty());

        // When
        Optional<User> foundUser = userRepository.findUserByEmail(email);

        // Then
        assertFalse(foundUser.isPresent(), "User should not be found");
    }
}
