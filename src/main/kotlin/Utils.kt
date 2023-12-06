import java.io.File
import kotlin.time.measureTime

fun <T> pr(s: T): T {
    println(s)
    return s
}

fun <T> T.pri(): T {
    println(this)
    return this
}

fun timed(block: () -> Unit) = println("└[${measureTime { block() }}]")

fun readLines(file: String) = File("src/main/resources/$file.txt").readLines()
