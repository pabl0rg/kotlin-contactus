import com.beust.kobalt.*
import com.beust.kobalt.plugin.packaging.*
import com.beust.kobalt.plugin.application.*
import com.beust.kobalt.plugin.kotlin.*

val bs = buildScript {
    repos("https://jitpack.io", "http://kotlin.bintray.com/ktor")
}

val p = project {

    name = "kotlin-contactus"
    group = "com.guatec"
    artifactId = name
    version = "0.1"

    kotlinCompiler {
        args("-jvm-target 1.8")
    }

    sourceDirectories {
        path("src/main/kotlin")
    }

    sourceDirectoriesTest {
        path("src/test/kotlin")
    }

    dependencies {
        compile("com.typesafe:config:1.2.1",
                "org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:jar:0.14.1",
                "org.jetbrains.kotlin:kotlin-stdlib-jre8:1.1.1",
                "org.jetbrains.kotlin:kotlin-reflect:1.1.1",
                "org.jetbrains.ktor:ktor-netty:0.3.1",
                "org.jetbrains.ktor:ktor-locations:0.3.1",
                "org.tinylog:slf4j-binding:1.2")
    }

    dependenciesTest {
        compile("org.testng:testng:6.11",
                "org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:jar:0.14.1",
                "org.jetbrains.kotlin:kotlin-stdlib-jre8:1.1.1")
    }

    assemble {
        jar {
            fatJar=true
            manifest {
                attributes("Main-Class", "com.guatec.kc.MainKt")
            }
        }
    }

    application {
        mainClass = "com.guatec.kc.MainKt"
    }
}
