package com.twilio

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.twilio.utils.Service
import org.eclipse.jetty.websocket.api.annotations.WebSocket
import org.reflections.Reflections
import spark.Filter
import spark.Request
import spark.Spark
import spark.Spark.before
import spark.Spark.webSocket
import spark.kotlin.port
import kotlin.reflect.KParameter
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.declaredFunctions

class Application {
    companion object {

        val gson = Gson()
        val packageReflectionName = "com.twilio.web"
        val reflection = Reflections(packageReflectionName)

        @JvmStatic
        fun main(args: Array<String>) {
            val classes: Set<Class<*>> = reflection.getSubTypesOf(Service::class.java)
            port(getHerokuAssignedPort())
            for(classObject in classes.map { it.kotlin }){
                val classInstance = classObject.createInstance()
                if(classObject.annotations.any { it is WebSocket }){
                    webSocket("/${classObject.java.packageName.replace(packageReflectionName.toRegex(), "").replace("\\.".toRegex(), "/")}/${classObject.simpleName}/socket".toLowerCase(), classInstance::class.java)
                }
            }
            enableCors()
            for(classObject in classes.map { it.kotlin }){
                val classInstance = classObject.createInstance()
                if(!classObject.annotations.any { it is WebSocket }){
                    val methods = classObject.declaredFunctions
                    for(method in methods){
                        if(method.annotations.any { it is Service.Get }){
                            Spark.get("${classObject.java.packageName.replace(packageReflectionName.toRegex(), "").replace("\\.".toRegex(), "/")}/${classObject.simpleName}/${method.name}".toLowerCase(), { request, response ->
                                val result = ApplicationReturn()
                                val methodParameters = method.parameters
                                val validatedParameters = mutableMapOf<KParameter, Any>()
                                for(parameter in methodParameters){
                                    validatedParameters[parameter] = if(parameter.kind == KParameter.Kind.INSTANCE) classInstance else getParameterName(parameter, request)
                                }
                                result.result = method.callBy(validatedParameters.toMap())
                                result
                            }, gson::toJson)
                        }
                        if(method.annotations.any { it is Service.Post }){
                            Spark.post("${classObject.java.packageName.replace(packageReflectionName.toRegex(), "").replace("\\.".toRegex(), "/")}/${classObject.simpleName}/${method.name}".toLowerCase(), { request, response ->
                                val result = ApplicationReturn()
                                val methodParameters = method.parameters
                                val validatedParameters = mutableMapOf<KParameter, Any>()
                                for(parameter in methodParameters){
                                    validatedParameters[parameter] = if(parameter.kind == KParameter.Kind.INSTANCE) classInstance else getParameterName(parameter, request)
                                }
                                result.result = method.callBy(validatedParameters.toMap())
                                result
                            }, gson::toJson)
                        }
                    }
                }
            }
        }

        private fun enableCors(){
            before(Filter { request, response ->
                response.header("Access-Control-Allow-Origin", "*")
                response.header("Access-Control-Request-Methods", "*")
                response.header("Access-Control-Allow-Headers", "*")
            })
        }

        private fun getParameterName(parameter: KParameter, request: Request): Any {
            val type = object : TypeToken<HashMap<String, Any?>>() {}.type
            return request.queryMap(parameter.name).value() ?: (gson.fromJson(request.body(), type) as HashMap<String, Any?>)[parameter.name] ?: throw Exception("Não encontramos o parametro")
        }

        private fun getHerokuAssignedPort(): Int {
            val processBuilder = ProcessBuilder()
            return if (processBuilder.environment()["PORT"] != null) {
                processBuilder.environment()["PORT"]!!.toInt()
            } else 4567
        }
    }

    data class ApplicationReturn(
            var result: Any? = Any()
    )
}