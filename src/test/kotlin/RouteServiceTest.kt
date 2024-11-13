import org.example.KotlinDemoApplication
import org.example.entities.Comment
import org.example.entities.Point
import org.example.entities.Route
import org.example.entities.RouteType
import org.example.repositories.RouteRepository
import org.example.repositories.UserRepository
import org.example.services.AuthService
import org.example.services.RouteService
import org.example.services.UserService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ContextConfiguration
import java.time.Instant
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@DataMongoTest
@ContextConfiguration(classes = [KotlinDemoApplication::class])
@ComponentScan(basePackages = ["org.example"])
class RouteServiceTest(
    @Autowired private val routeService: RouteService,
    @Autowired private val routeRepository: RouteRepository,
    @Autowired private val authService: AuthService,
    @Autowired private val userRepository: UserRepository
) {
    @BeforeEach
    fun cleanDatabase() {
        routeRepository.deleteAll()
        userRepository.deleteAll()
    }

    @Test
    fun givenNoExistingRoute_whenCreateNewRouteAndAddComment_thenRouteIsCorrect() {
        authService.register("test", "1234567")
        val routeId = UUID.randomUUID()
        val newRoute = Route(
            id = routeId,
            title = "Mountain Trail",
            description = "A challenging mountain trail.",
            recommendations = listOf("Bring water", "Wear hiking boots"),
            durationInMinutes = 120,
            difficulty = 5,
            types = listOf(RouteType.CYCLING),
            points = listOf(Point(34.0, -118.0)),
            comments = listOf(),
            rate = 4.5
        )
        routeService.save("test", newRoute)
        val commentId = UUID.randomUUID()
        val newComment = Comment(
            id = commentId,
            text = "text",
            userLogin = "login",
            rate = 5,
            timestamp = Instant.now()
        )
        routeService.addComment(routeId, newComment)
        val actual = routeService.get(routeId.toString())
        assertNotNull(actual, "Route should not be null")
        assertEquals(routeId, actual.id, "Route ID should match the expected ID")

        assertEquals(1, actual.comments.size, "There should be exactly one comment")

        val comment = actual.comments.first()
        assertEquals(commentId, comment.id, "Comment ID should match the expected ID")
        assertEquals("text", comment.text, "Comment text should match")
        assertEquals("login", comment.userLogin, "Comment user login should match")
        assertEquals(5, comment.rate, "Comment rate should match")

    }
}