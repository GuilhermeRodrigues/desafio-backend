package com.luizalabs.desafio.util

import org.slf4j.LoggerFactory
import java.net.URL
import java.net.URLClassLoader
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.jvm.internal.Reflection
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

fun testDataClassesByPackage(packageTest: String, directoryTest: String? = "src/main/kotlin") {
    val logger = LoggerFactory.getLogger("testDataClassesByPackage")

    Files.list(Paths.get(System.getProperty("user.dir"), "/$directoryTest/${packageTest.replace(".", "/")}")).forEach {
        try {
            val classLoaderUrls = arrayOf(URL("file:/" + it.toAbsolutePath().toString()))
            val urlClassLoader = URLClassLoader(classLoaderUrls)
            val entity = urlClassLoader.loadClass("$packageTest." + it.fileName.toString().split(".")[0])
            val kotlinClass = Reflection.createKotlinClass(entity)

            if (!kotlinClass.isSubclassOf(Enum::class) && !kotlinClass.isGeneric() && kotlinClass.isData) {
                kotlinClass.assertSerializable()
            }
        } catch (e: Exception) { }
    }
}

private fun <T : Any> KClass<T>.isGeneric(): Boolean {
    return this.typeParameters.isNotEmpty()
}
