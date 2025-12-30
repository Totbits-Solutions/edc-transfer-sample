rootProject.name = "transfer-sample"

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        mavenLocal()
    }
}

include(":transfer-00-prerequisites:connector")

include(":transfer-04-event-consumer:consumer-with-listener")
include(":transfer-04-event-consumer:provider-with-listener")
include(":transfer-04-event-consumer:listener")

include(":transfer-05-file-transfer-cloud:cloud-transfer-consumer")
include(":transfer-05-file-transfer-cloud:cloud-transfer-provider")
include(":transfer-05-file-transfer-cloud:transfer-file-cloud")

include(":transfer-03-consumer-pull:provider-proxy-data-plane")

include(":transfer-06-kafka-broker:kafka-runtime")

include(":util:http-request-logger")
