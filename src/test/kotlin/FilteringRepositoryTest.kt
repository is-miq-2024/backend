import org.example.KotlinDemoApplication
import org.example.dto.RangeFilter
import org.example.dto.RouteFilter
import org.example.entities.Point
import org.example.entities.Route
import org.example.entities.RouteType
import org.example.repositories.FilteringRepositoryImpl
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.test.context.ContextConfiguration
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals

@DataMongoTest
@ContextConfiguration(classes = [KotlinDemoApplication::class])
class FilteringRepositoryTest (
    @Autowired
    private val mongoTemplate: MongoTemplate,
    @Autowired
    private val filteringRepository: FilteringRepositoryImpl
) {

    @BeforeEach
    fun setUp() {
        mongoTemplate.dropCollection(Route::class.java)

        val routes = listOf(
            Route(
                id = UUID.randomUUID(),
                title = "Easy Hike",
                description = "Easy Hike",
                recommendations = listOf(),
                durationInMinutes = 120,
                difficulty = 2,
                types = listOf(RouteType.WALKING),
                points = listOf(
                    Point(1.1, 1.1),
                    Point(2.2, 2.2)
                ),
                comments = listOf(),
                rate = 4.5
            ),
            Route(
                id = UUID.randomUUID(),
                title = "Mountain Cycling",
                description = "Mountain Cycling",
                recommendations = listOf(),
                durationInMinutes = 300,
                difficulty = 8,
                types = listOf(RouteType.CYCLING),
                points = listOf(
                    Point(1.1, 1.1),
                    Point(2.2, 2.2)
                ),
                comments = listOf(),
                rate = 5.0
            ),
            Route(
                id = UUID.randomUUID(),
                title = "City Driving",
                description = "City Driving",
                recommendations = listOf(),
                durationInMinutes = 60,
                difficulty = 1,
                types = listOf(RouteType.DRIVING),
                points = listOf(
                    Point(1.1, 1.1),
                    Point(2.2, 2.2)
                ),
                comments = listOf(),
                rate = 3.5
            )
        )
        mongoTemplate.insert(routes, Route::class.java)
    }

    @Test
    fun givenFilterByTitle_whenFindByFilter_thenReturnRoute() {
        val filter = RouteFilter(
            pageSize = 3,
            pageNumber = 0,
            title = "Easy",
            durationInMinutes = null,
            difficulty = null,
            rate = null,
            types = null
        )

        val result = filteringRepository.findByFilter(filter)

        assertEquals(1, result.size)
        assertEquals("Easy Hike", result[0].title)
    }

    @Test
    fun givenFilterByType_whenFindByFilter_thenReturnRoute() {
        val filter = RouteFilter(
            pageSize = 3,
            pageNumber = 0,
            title = null,
            durationInMinutes = null,
            difficulty = null,
            rate = null,
            types = setOf(RouteType.CYCLING)
        )

        val result = filteringRepository.findByFilter(filter)

        assertEquals(1, result.size)
        assertEquals("Mountain Cycling", result[0].title)
    }

    @Test
    fun givenFilterByDuration_whenFindByFilter_thenReturnRoutes() {
        val filter = RouteFilter(
            pageSize = 3,
            pageNumber = 0,
            title = null,
            durationInMinutes = RangeFilter(60, 120),
            difficulty = null,
            rate = null,
            types = null
        )

        val result = filteringRepository.findByFilter(filter)

        assertEquals(2, result.size)
    }

    @Test
    fun givenFilterByRate_whenFindByFilter_thenReturnRoutes() {
        val filter = RouteFilter(
            pageSize = 3,
            pageNumber = 0,
            title = null,
            durationInMinutes = null,
            difficulty = null,
            rate = RangeFilter(4.0, 5.0),
            types = null
        )

        val result = filteringRepository.findByFilter(filter)

        assertEquals(2, result.size)
    }

    @Test
    fun givenFilterByDifficulty_whenFindByFilter_thenReturnRoutes() {
        val filter = RouteFilter(
            pageSize = 3,
            pageNumber = 0,
            title = null,
            durationInMinutes = null,
            difficulty = RangeFilter(0, 5),
            rate = null,
            types = null
        )

        val result = filteringRepository.findByFilter(filter)

        assertEquals(2, result.size)
    }

    @Test
    fun givenEmptyFilter_whenFindByFilter_thenReturnAll() {
        val filter = RouteFilter(
            pageSize = 3,
            pageNumber = 0,
            title = null,
            durationInMinutes = null,
            difficulty = null,
            rate = null,
            types = null
        )

        val result = filteringRepository.findByFilter(filter)

        assertEquals(3, result.size)
    }
}