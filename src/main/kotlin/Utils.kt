import java.io.File

fun <T> pr(s: T): T {
    println(s)
    return s
}

fun <T> T.pri(): T {
    println(this)
    return this
}

fun readLines(file: String) = File("src/main/resources/$file.txt").readLines()
