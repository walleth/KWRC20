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
            postProcessWAT("wasm/build/konan/bin/wasm32/kwrc20.wat")
        }
    }
}

fun postProcessWAT(fileName: String) {

    var newFun = ""
    var newOut = ""
    File(fileName).forEachLine { line ->

        if (line.trim().startsWith("(import")) {
            val type = extractType(line)
            val replacement = when {
                line.contains("Konan_js_fetch_arg") -> "   (func \$Konan_js_fetch_arg (type $type)\n" +
                        "                    i32.const 0\n" +
                        "                    i32.const 0\n" +
                        "                    call \$revert\n" +
                        "                    i32.const 0)\n"
                line.contains("Konan_js_arg_size") -> " (func \$Konan_js_arg_size (type $type)\n" +
                        "                    i32.const 0)\n"
                line.contains("Konan_abort") -> "   (func \$Konan_abort (type $type)\n" +
                        "                    i32.const 0\n" +
                        "                    i32.const 0\n" +
                        "                    call \$revert)\n"
                line.contains("abort") -> "  (func \$abort (type 0)\n" +
                        "      i32.const 0\n" +
                        "      i32.const 0\n" +
                        "      call \$revert)"
                line.contains("Konan_notify_memory_grow") -> "(func \$Konan_notify_memory_grow (type $type)\n" +
                        "i32.const 0\n" +
                        "i32.const 0\n" +
                        "call \$revert)\n"

                line.contains("write") -> " (func \$write (type $type)\n" +
                        "    i32.const 0)"

                else -> {
                    newOut += line + "\n"
                    ""
                }
            }
            newFun += replacement

        } else newOut += line + "\n"
    }

    newOut = newOut.replaceFirst("end)", "end)\n$newFun")
    val outFile = File("$fileName.cleaned.wat").apply {
        createNewFile()
    }
    outFile.writeText(newOut)
}

val typeRegex = Regex("\\(type ([0-9]*)\\)")
fun extractType(line: String) = typeRegex.find(line)?.groupValues?.last()?.toIntOrNull()



