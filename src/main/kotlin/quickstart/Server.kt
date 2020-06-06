package quickstart
import org.http4k.core.*
import org.http4k.core.Status.Companion.OK
import org.http4k.events.AutoJsonEvents
import org.http4k.events.Event
import org.http4k.events.EventFilters
import org.http4k.events.then
import org.http4k.filter.ResponseFilters
import org.http4k.format.Jackson
import org.http4k.server.Jetty
import org.http4k.server.asServer
import java.time.Clock


fun main(args:Array<String>) {
    val audit = ResponseFilters.ReportHttpTransaction { tx: HttpTransaction ->
        logger("my call to ${tx.request.uri} returned ${tx.response.status} and took ${tx.duration.toMillis()}")
    }
        val app = audit.then{ request: Request -> Response(OK).body("Hello World" + request.query("name")) }.asServer(Jetty(8088))


        app.start()

    }
fun logger(message: String) = println("${Clock.systemUTC().instant()} $message")

