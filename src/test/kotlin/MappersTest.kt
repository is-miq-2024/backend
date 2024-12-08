import org.example.dto.CreateCommentRequest
import org.example.dto.RouteCreateRequest
import org.example.dto.RouteUpdateRequest
import org.example.entities.Point
import org.example.entities.RouteType
import org.example.entities.User
import org.example.mappers.toEntity
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class MappersTest {
    @Test
    fun givenDtos_whenToEntity_thenReturnEntity() {
        val login = "test_user"

        val createCommentRequest = CreateCommentRequest(
            routeId = UUID.randomUUID(),
            text = "This is a comment",
            rate = 5
        )

        val routeCreateRequest = RouteCreateRequest(
            title = "New Route",
            description = "A beautiful scenic route.",
            recommendations = listOf("Bring water."),
            durationInMinutes = 120,
            difficulty = 3,
            types = listOf(RouteType.WALKING, RouteType.CYCLING),
            points = listOf(Point(1.1, 1.1), Point(2.2, 2.2))
        )

        val routeUpdateRequest = RouteUpdateRequest(
            id = UUID.randomUUID().toString(),
            title = "Updated Route",
            description = "An updated description.",
            recommendations = null,
            durationInMinutes = 90,
            difficulty = 2,
            types = listOf(RouteType.DRIVING),
            points = listOf(Point(1.1, 1.1), Point(2.2, 2.2))
        )

        val user = User(
            login = login,
            favoriteRoutes = listOf(),
            createdRoutes = listOf(),
            completedRoutes = listOf(),
            password = "password"
        )

        val commentEntity = createCommentRequest.toEntity(login)
        val routeCreateEntity = routeCreateRequest.toEntity()
        val routeUpdateEntity = routeUpdateRequest.toEntity()

        assertNotNull(commentEntity.id)
        assertEquals(createCommentRequest.text, commentEntity.text)
        assertEquals(login, commentEntity.userLogin)
        assertEquals(createCommentRequest.rate, commentEntity.rate)
        assertNotNull(commentEntity.timestamp)
        assertNotNull(createCommentRequest.routeId)

        assertNotNull(routeCreateEntity.id)
        assertEquals(routeCreateRequest.title, routeCreateEntity.title)
        assertEquals(routeCreateRequest.description, routeCreateEntity.description)
        assertEquals(routeCreateRequest.recommendations, routeCreateEntity.recommendations)
        assertEquals(routeCreateRequest.durationInMinutes, routeCreateEntity.durationInMinutes)
        assertEquals(routeCreateRequest.difficulty, routeCreateEntity.difficulty)
        assertEquals(routeCreateRequest.types, routeCreateEntity.types)
        assertEquals(routeCreateRequest.points, routeCreateEntity.points)
        assertTrue(routeCreateEntity.comments.isEmpty())
        assertEquals(0.0, routeCreateEntity.rate)

        assertEquals(UUID.fromString(routeUpdateRequest.id), routeUpdateEntity.id)
        assertEquals(routeUpdateRequest.title, routeUpdateEntity.title)
        assertEquals(routeUpdateRequest.description, routeUpdateEntity.description)
        assertEquals(routeUpdateRequest.recommendations.orEmpty(), routeUpdateEntity.recommendations)
        assertEquals(routeUpdateRequest.durationInMinutes, routeUpdateEntity.durationInMinutes)
        assertEquals(routeUpdateRequest.difficulty, routeUpdateEntity.difficulty)
        assertEquals(routeUpdateRequest.types, routeUpdateEntity.types)
        assertEquals(routeUpdateRequest.points, routeUpdateEntity.points)
        assertTrue(routeUpdateEntity.comments.isEmpty())
        assertEquals(0.0, routeUpdateEntity.rate)
    }
}