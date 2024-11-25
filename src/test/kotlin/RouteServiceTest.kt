import org.example.dto.RouteFilter
import org.example.entities.Comment
import org.example.entities.Route
import org.example.exception.RouteException
import org.example.repositories.RouteRepository
import org.example.services.RouteService
import org.example.services.UserService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.util.UUID
import java.util.Optional
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class RouteServiceTest {

    @Mock
    private lateinit var routeRepository: RouteRepository

    @Mock
    private lateinit var userService: UserService

    @Mock
    private lateinit var route: Route

    private lateinit var routeService: RouteService

    @BeforeEach
    fun setUp() {
        routeService = RouteService(routeRepository, userService);
    }

    @Test
    fun givenRoute_whenSave_thenSaveRoute() {
        val login = "testUser"

        `when`(routeRepository.save(route)).thenReturn(route)

        val savedRoute = routeService.save(login, route)

        assertEquals(route, savedRoute)
        verify(userService).addCreatedRoute(login, route.id)
        verify(routeRepository).save(route)
    }

    @Test
    fun givenRoute_whenUpdate_thenUpdateRoute() {
        `when`(routeRepository.save(route)).thenReturn(route)

        val updatedRoute = routeService.update(route)

        verify(routeRepository).deleteById(route.id)
        verify(routeRepository).save(route)
        assertEquals(route, updatedRoute)
    }

    @Test
    fun givenRouteId_whenFindById_thenReturnRoute() {
        val routeId = UUID.randomUUID()
        `when`(routeRepository.findById(routeId)).thenReturn(Optional.of(route))

        val foundRoute = routeService.get(routeId.toString())

        assertEquals(route, foundRoute)
    }

    @Test
    fun givenNotExistingRouteId_whenFindById_thenThrowRouteException() {
        val routeId = UUID.randomUUID()
        `when`(routeRepository.findById(routeId)).thenReturn(Optional.empty())

        val exception = assertThrows<RouteException> {
            routeService.get(routeId.toString())
        }

        assertEquals("route with id ${routeId} not found", exception.message)
    }

    @Test
    fun givenFilter_whenGetAll_thenReturnRoutes() {
        val filter = mock<RouteFilter>()
        val routes = listOf(route)
        `when`(routeRepository.findByFilter(filter)).thenReturn(routes)

        val foundRoutes = routeService.getAll(filter)

        assertEquals(routes.size, foundRoutes.size)
        assertEquals(routes, foundRoutes)
    }

    @Test
    fun givenRouteId_whenDelete_thenDeleteRoute() {
        val routeId = UUID.randomUUID()
        routeService.delete(routeId.toString())
        verify(routeRepository).deleteById(routeId)
    }

    @Test
    fun givenComment_whenAddComment_thenAddComment() {
        val routeId = UUID.randomUUID()
        val comment = mock<Comment>()

        `when`(routeRepository.findById(routeId)).thenReturn(Optional.of(route))
        val updatedRoute = route.copy(comments = listOf(comment))
        `when`(routeRepository.save(updatedRoute)).thenReturn(updatedRoute)

        routeService.addComment(routeId, comment)

        verify(routeRepository).findById(routeId)
        verify(routeRepository).save(updatedRoute)
    }

    @Test
    fun givenNotExistingRouteId_whenAddComment_thenAddComment() {
        val routeId = UUID.randomUUID()
        val comment = mock<Comment>()

        `when`(routeRepository.findById(routeId)).thenReturn(Optional.empty())

        val exception = assertThrows<RouteException> {
            routeService.addComment(routeId, comment)
        }

        assertEquals("route with id ${routeId} not found", exception.message)
    }
}