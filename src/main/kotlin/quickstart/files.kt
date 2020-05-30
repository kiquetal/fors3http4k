package cookbook.multipart_forms

import org.http4k.core.*
import org.http4k.core.Status.Companion.OK
import org.http4k.filter.CorsPolicy
import org.http4k.filter.ServerFilters
import org.http4k.lens.MultipartFormFile
import org.http4k.server.Jetty
import org.http4k.server.SunHttp
import org.http4k.server.asServer
import java.io.File

fun main()

{

    val handler={ request: Request ->
        val receivedForm = MultipartFormBody.from(request)
        println(receivedForm.fieldValues("field"))
        println("que recibir"  + receivedForm.field("fieldName"))
        val filesList=receivedForm.files("file")
        println("total files"+filesList.size)
        val newMap=filesList.map {d->

            val success = File("./guardia/fotos").mkdirs()

             File("./guardia/fotos/${d.filename}").createNewFile()
            File("./guardia/fotos/${d.filename}").also {
               it.outputStream().use {
                   d.content.copyTo(it)
               }
           }
        }

        Response(OK).body("what saup")
    }
    var corsHandler= ServerFilters.Cors(CorsPolicy(listOf("*"),listOf("*"), Method.values().toList(),true))
    val app =corsHandler.then(handler)
            app.asServer(Jetty(8000)).start()
}
