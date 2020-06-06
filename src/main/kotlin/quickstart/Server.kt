package quickstart
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.server.Jetty
import org.http4k.server.asServer



    fun main(args:Array<String>) {
        { request: Request -> Response(OK).body("Hello World" + request.query("name")) }.asServer(Jetty(8088)).start()
    }

