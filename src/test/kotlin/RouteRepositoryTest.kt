import org.example.KotlinDemoApplication
import org.example.entities.Point
import org.example.entities.Route
import org.example.entities.RouteType
import org.example.repositories.RouteRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.context.ContextConfiguration
import java.time.Duration
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@DataMongoTest
@ContextConfiguration(classes = [KotlinDemoApplication::class])
class RouteRepositoryTest (
    @Autowired
    private val routeRepository: RouteRepository
) {

    @BeforeEach
    fun cleanDatabase() {
        routeRepository.deleteAll()
    }

    @Test
    fun givenNoExistingRoute_whenCreateNewRoute_thenRouteIsCorrect() {
        val routeId = UUID.randomUUID()
        val newRoute = Route(
            id = routeId,
            title = "Mountain Trail",
            description = "A challenging mountain trail.",
            recommendations = listOf("Bring water", "Wear hiking boots"),
            durationInMinutes = Duration.ofMinutes(120),
            difficulty = 5,
            types = listOf(RouteType.CYCLING),
            points = listOf(Point(34.0, -118.0)),
            comments = listOf(),
            rate = 4.5
        )

        routeRepository.save(newRoute)

        val foundRoute = routeRepository.findById(routeId).orElse(null)
        assertNotNull(foundRoute, "Route should be found in database")
        assertEquals(newRoute.title, foundRoute?.title)
        assertEquals(newRoute.description, foundRoute?.description)
        assertEquals(newRoute.recommendations, foundRoute?.recommendations)
        assertEquals(newRoute.durationInMinutes, foundRoute?.durationInMinutes)
        assertEquals(newRoute.difficulty, foundRoute?.difficulty)
        assertEquals(newRoute.types, foundRoute?.types)
        assertEquals(newRoute.points, foundRoute?.points)
        assertEquals(newRoute.comments, foundRoute?.comments)
        assertEquals(newRoute.rate, foundRoute?.rate)
    }

    @Test
    fun given2CyclingRoutes_thenFound() {
        createRoutes()
        val cyclingRoutes = routeRepository.findByTypes(RouteType.CYCLING)
        assertEquals(2, cyclingRoutes.size, "Expected 2 cycling routes")
        assert(cyclingRoutes.all { it.types.contains(RouteType.CYCLING) })
    }


    fun createRoutes() {
        val routes = listOf(
            Route(
                id = UUID.randomUUID(),
                title = "Scenic Mountain Trail",
                description = "A beautiful trail through the mountains.",
                recommendations = listOf("Bring water", "Wear hiking boots"),
                durationInMinutes = Duration.ofMinutes(120),
                difficulty = 3,
                types = listOf(RouteType.CYCLING),
                points = listOf(),
                comments = listOf(),
                rate = 4.5
            ),
            Route(
                id = UUID.randomUUID(),
                title = "City Cycle Path",
                description = "Explore the city on a cycle path.",
                recommendations = listOf("Helmet recommended", "Watch for traffic"),
                durationInMinutes = Duration.ofMinutes(90),
                difficulty = 2,
                types = listOf(RouteType.WALKING),
                points = listOf(),
                comments = listOf(),
                rate = 4.2
            ),
            Route(
                id = UUID.randomUUID(),
                title = "Forest Adventure",
                description = "Navigate through dense forests.",
                recommendations = listOf("Insect repellent advised"),
                durationInMinutes = Duration.ofMinutes(150),
                difficulty = 4,
                types = listOf(RouteType.WALKING, RouteType.CYCLING),
                points = listOf(),
                comments = listOf(),
                rate = 4.8
            ),
            Route(
                id = UUID.randomUUID(),
                title = "Heritage Walk",
                description = "Discover historical landmarks.",
                recommendations = listOf("Guided tours available"),
                durationInMinutes = Duration.ofMinutes(60),
                difficulty = 1,
                types = listOf(RouteType.DRIVING),
                points = listOf(),
                comments = listOf(),
                rate = 4.0
            ),
            Route(
                id = UUID.randomUUID(),
                title = "River Kayak Trip",
                description = "Paddle down the river.",
                recommendations = listOf("Life jacket required"),
                durationInMinutes = Duration.ofMinutes(180),
                difficulty = 5,
                types = listOf(RouteType.DRIVING),
                points = listOf(),
                comments = listOf(),
                rate = 4.7
            )
        )

        routeRepository.saveAll(routes)
    }
}