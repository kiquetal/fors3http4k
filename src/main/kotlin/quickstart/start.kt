import com.fasterxml.jackson.databind.JsonNode
import org.http4k.aws.AwsCredentialScope
import org.http4k.aws.AwsCredentials
import org.http4k.client.ApacheClient
import org.http4k.core.*
import org.http4k.core.Method.*
import org.http4k.filter.AwsAuth
import org.http4k.filter.ClientFilters
import org.http4k.format.Jackson
import org.http4k.format.Json
import java.util.UUID

fun main() {

    val region = "us-east-1"
    val service = "s3"
    val accessKey = System.getenv("credentialKey")
    val secretKey = System.getenv("credentialSecret")


    val client = ClientFilters.AwsAuth(
            AwsCredentialScope(region, service),
            AwsCredentials(accessKey, secretKey))
            .then(ApacheClient())

    // create a bucket
    val bucketName = "amplify-reacts3app-test-215255-deployment"

    // create a key into the bucket
    val key = "shift-2020/05/3A"
    val json = Jackson

    val keyUri = Uri.of("https://$bucketName.s3.amazonaws.com/$key")
    val objectUsingDirectApi: JsonNode = json.obj(
            "thisIsAString" to json.string("stringValue"),
            "thisIsANumber" to json.number(12345),
            "thisIsAList" to json.array(listOf(json.boolean(true)))
    )

    println(client(Request(PUT, keyUri).body(objectUsingDirectApi.toString())))

    println(json.parse(client(Request(GET,keyUri)).body.toString()).get("thisIsAList"))


}
