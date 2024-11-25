import org.example.entities.User
import org.example.exception.UserException
import org.example.repositories.UserRepository
import org.example.services.UserService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.util.UUID
import java.util.Optional
import kotlin.test.Test
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class UserServiceTest {

    @Mock
    private lateinit var userRepository: UserRepository
    @Mock
    private lateinit var user: User

    private lateinit var userService: UserService

    @BeforeEach
    fun setUp() {
        userService = UserService(userRepository)
    }

    @Test
    fun givenLogin_whenFindById_thenReturnUser() {
        val login = "testUser"

        `when`(userRepository.findById(login)).thenReturn(Optional.of(user))

        val foundUser = userService.findByLogin(login)

        assertEquals(user, foundUser)
    }

    @Test
    fun givenNotExistingLogin_whenFindById_thenThrowUserException() {
        val login = "nonExistentUser"

        `when`(userRepository.findById(login)).thenReturn(Optional.empty())

        val exception = assertThrows<UserException> {
            userService.findByLogin(login)
        }
        assertEquals("user with login $login not found", exception.message)
    }

    @Test
    fun givenLogin_whenAddFavorite_thenAddFavorite() {
        val login = "testUser"
        val routeId = UUID.randomUUID()
        val updatedUser = user.copy(favoriteRoutes = listOf(routeId))

        `when`(userRepository.findById(login)).thenReturn(Optional.of(user))
        `when`(userRepository.save(updatedUser)).thenReturn(updatedUser)

        userService.addFavoriteRoute(login, routeId)

        verify(userRepository).save(updatedUser)
    }

    @Test
    fun givenLogin_whenAddCreated_thenAddCreated() {
        val login = "testUser"
        val routeId = UUID.randomUUID()
        val updatedUser = user.copy(createdRoutes = listOf(routeId))

        `when`(userRepository.findById(login)).thenReturn(Optional.of(user))
        `when`(userRepository.save(updatedUser)).thenReturn(updatedUser)

        userService.addCreatedRoute(login, routeId)

        verify(userRepository).save(updatedUser)
    }

    @Test
    fun givenLogin_whenAddCompleted_thenAddCompleted() {
        val login = "testUser"
        val routeId = UUID.randomUUID()
        val updatedUser = user.copy(completedRoutes = listOf(routeId))

        `when`(userRepository.findById(login)).thenReturn(Optional.of(user))
        `when`(userRepository.save(updatedUser)).thenReturn(updatedUser)

        userService.addCompletedRoute(login, routeId)

        verify(userRepository).save(updatedUser)
    }
}