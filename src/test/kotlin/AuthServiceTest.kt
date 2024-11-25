import org.example.entities.User
import org.example.exception.AuthException
import org.example.repositories.UserRepository
import org.example.services.AuthService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.Optional
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExtendWith(MockitoExtension::class)
class AuthServiceTest {

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var passwordEncoder: PasswordEncoder

    private lateinit var authService: AuthService

    @BeforeEach
    fun setUp() {
        authService = AuthService(userRepository, passwordEncoder)
    }

    @Test
    fun givenLoginAndPassword_whenRegister_thenRegister() {
        val login = "testUser"
        val password = "plainPassword"
        val encodedPassword = "encodedPassword"

        `when`(userRepository.existsByLogin(login)).thenReturn(false)
        `when`(passwordEncoder.encode(password)).thenReturn(encodedPassword)

        authService.register(login, password)

        val captor = ArgumentCaptor.forClass(User::class.java)
        verify(userRepository).save(captor.capture())
        val savedUser = captor.value

        assertEquals(login, savedUser.login)
        assertEquals(encodedPassword, savedUser.password)
        assertTrue(savedUser.favoriteRoutes.isEmpty())
        assertTrue(savedUser.createdRoutes.isEmpty())
        assertTrue(savedUser.completedRoutes.isEmpty())
    }

    @Test
    fun givenAlreadyExistingLoginAndPassword_whenRegister_thenThrowAuthException() {
        val login = "existingUser"
        val password = "password"

        `when`(userRepository.existsByLogin(login)).thenReturn(true)

        val exception = assertThrows<AuthException> {
            authService.register(login, password)
        }
        assertEquals("User with username '$login' already exists", exception.message)
    }

    @Test
    fun givenLogin_whenLoadUserByUsername_thenReturnUserDetails() {
        val login = "testUser"
        val password = "encodedPassword"

        val user = User(
            login = login,
            favoriteRoutes = emptyList(),
            createdRoutes = emptyList(),
            completedRoutes = emptyList(),
            password = password
        )

        `when`(userRepository.findByLogin(login)).thenReturn(Optional.of(user))

        val userDetails = authService.loadUserByUsername(login)

        assertEquals(login, userDetails.username)
        assertEquals(password, userDetails.password)
        assertTrue(userDetails.authorities.any { it.authority == "USER" })
    }

    @Test
    fun givenNotExistingLogin_whenLoadUserByUsername_thenThrowsUsernameNotFoundException() {
        val login = "nonExistentUser"

        `when`(userRepository.findByLogin(login)).thenReturn(Optional.empty())

        val exception = assertThrows<UsernameNotFoundException> {
            authService.loadUserByUsername(login)
        }
        assertEquals("User with username '$login' not found", exception.message)
    }

    @Test
    fun givenLogin_whenFindUserByLogin_thenReturnUser() {
        val login = "testUser"
        val user = User(
            login = login,
            favoriteRoutes = emptyList(),
            createdRoutes = emptyList(),
            completedRoutes = emptyList(),
            password = "encodedPassword"
        )

        `when`(userRepository.findByLogin(login)).thenReturn(Optional.of(user))

        val foundUser = authService.findUserByLogin(login)

        assertEquals(user, foundUser)
    }

    @Test
    fun givenNotExistingLogin_whenFindUserByLogin_thenThrowsUsernameNotFoundException() {
        val login = "nonExistentUser"

        `when`(userRepository.findByLogin(login)).thenReturn(Optional.empty())

        val exception = assertThrows<UsernameNotFoundException> {
            authService.findUserByLogin(login)
        }
        assertEquals("User with login '$login' not found", exception.message)
    }
}