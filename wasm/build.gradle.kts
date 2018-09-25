import org.jetbrains.kotlin.gradle.plugin.KonanLibrary
import org.gradle.api.tasks.Exec

plugins {
    id("konan")
}

konan.targets = mutableListOf("wasm32")

konanArtifacts {
    program("kwrc20")
}

tasks {
    val wat by creating {
        doLast {
            exec {
                commandLine("wasm2wat", "build/konan/bin/wasm32/kwrc20.wasm", "--ignore", "-o", "build/konan/bin/wasm32/kwrc20.wat")
            }
        }
    }
}

