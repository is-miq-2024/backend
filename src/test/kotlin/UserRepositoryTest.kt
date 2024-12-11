import org.example.KotlinDemoApplication
import org.example.entities.User
import org.example.repositories.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.context.ContextConfiguration
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

@DataMongoTest
@ContextConfiguration(classes = [KotlinDemoApplication::class])
class UserRepositoryTest (
    @Autowired
    private val userRepository: UserRepository
) {

    @BeforeEach
    fun setUp() {
        userRepository.deleteAll()
        userRepository.save(User(
            login = "test",
            password = "12345678"
        ))
    }

    @Test
    fun givenNotExistingLogin_whenExistsByLogin_thenReturnFalse() {
        val login = "nonExistentUser"

        val exists = userRepository.existsByLogin(login)

        assertFalse(exists)
    }

    @Test
    fun givenLogin_whenExistsByLogin_thenReturnTrue() {
        val existingUser = User(
            login = "existingUser",
            password = "encodedPassword"
        )

        userRepository.save(existingUser)

        val exists = userRepository.existsByLogin(existingUser.login)

        assertTrue(exists)
    }

    @Test
    fun givenLogin_whenFindByLogin_thenReturnUser() {
        val existingUser = User(
            login = "existingUser",
            password = "encodedPassword"
        )

        userRepository.save(existingUser)

        val result = userRepository.findByLogin(existingUser.login)

        assertTrue(result.isPresent)
        assertEquals(existingUser, result.get())
    }

    @Test
    fun givenNotExistingLogin_whenFindByLogin_thenReturnEmpty() {
        val login = "nonExistentUser"

        val result = userRepository.findByLogin(login)

        assertFalse(result.isPresent)
    }
}