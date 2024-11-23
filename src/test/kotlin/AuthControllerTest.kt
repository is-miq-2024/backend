import com.fasterxml.jackson.databind.ObjectMapper
import org.example.KotlinDemoApplication
import org.example.controllers.AuthController
import org.example.dto.LoginAndRegisterRequest
import org.example.entities.User
import org.example.services.AuthService
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.Authentication
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import kotlin.test.Test

@SpringBootTest(classes = [KotlinDemoApplication::class])
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc
    private val objectMapper = ObjectMapper()

    private val authService = Mockito.mock(AuthService::class.java)
    private val authenticationConfiguration = Mockito.mock(AuthenticationConfiguration::class.java)
    private val authenticationManager = Mockito.mock(AuthenticationManager::class.java)

    private lateinit var authController: AuthController

    @BeforeEach
    fun setup() {
        Mockito.`when`(authenticationConfiguration.authenticationManager).thenReturn(authenticationManager)
        authController = AuthController(authService, authenticationConfiguration)
    }

    @Test
    fun givenLoginAndRegisterRequest_whenRegister_thenRegister() {
        val request = LoginAndRegisterRequest("test_user", "password123")

        mockMvc.post("/auth/register") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }
            .andExpect {
                "User with username 'test_user' already exists"
            }
    }

    @Test
    fun givenLoginAndRegisterRequest_whenLogin_thenLogin() {
        val request = LoginAndRegisterRequest("test_user", "password123")
        val authentication: Authentication = UsernamePasswordAuthenticationToken(request.login, request.password)

        Mockito.`when`(authenticationManager.authenticate(authentication)).thenReturn(authentication)
        Mockito.`when`(authService.findUserByLogin(request.login)).thenReturn(User(login = request.login, password = request.password))

        mockMvc.post("/auth/login") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }
            .andExpect {
                jsonPath("$.message") { value("Login successful!") }
                jsonPath("$.userId") { value("test_user") }
            }
    }

    @Test
    fun givenLogin_whenLogout_thenLogout() {
        mockMvc.post("/auth/logout") {
            contentType = MediaType.APPLICATION_JSON
        }
            .andExpect {
                jsonPath("$.message") { value("Logout successful!") }
            }
    }
}