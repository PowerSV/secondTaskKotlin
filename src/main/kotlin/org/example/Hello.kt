package org.example

/**
 * Вариант 7 -- du
Вывод на консоль размера указанного файла(-ов) или каталога(-ов) из файловой системы:
fileN задаёт имя файла, размер которого нужно вывести. Таких параметров может быть несколько.

Флаг -h указывает, что размер следует выдавать в человеко-читаемом формате: в зависимости от размера файла результат
выдается в байтах, килобайтах, мегабайтах или гигабайтах и дополняется соответствующей единицей измерения (B, KB, MB, GB).
Если данный флаг не указан, размер должен печататься в килобайтах и без единицы измерения.

Флаг -c означает, что для всех переданных на вход файлов нужно вывести суммарный размер.

Флаг --si означает, что для всех используемых единиц измерения следует брать основание 1000, а не 1024.


Command line: du [-h] [-c] [--si] file1 file2 file3 …

На вход может быть передано любое количество имён файлов. Все флаги совместимы друг с другом.
Размер каталога равен сумме размеров всех входящих в него файлов и каталогов. Если на вход передаётся имя,
не соответствующее существующему файлу, следует вместо результата выдать ошибку. Возвращаемое значение программы
в случае успеха равняется 0, в случае ошибки 1.

Кроме самой программы, следует написать автоматические тесты к ней.
 */


import java.io.File
import kotlin.math.round
import kotlin.system.exitProcess

var basis = 1024

fun main(args: Array<String>) {
    var flagH = false
    var flagC = false
    var flagSi = false

    val listOfFiles = mutableListOf<File>()

    // разбор командной строки
    for (element in args) {
        when (element) {
            "-h" -> flagH = true
            "-c" -> flagC = true
            "--si" -> flagSi = true
            else -> if (!File(element).exists()) {
                System.err.println("указаного файла/директории не существует!")
                exitProcess(1)
            } else listOfFiles.add(File(element))
        }
    }
    if (listOfFiles.isEmpty()) {
        System.err.println("не указан файл/директория!")
        exitProcess(1)
    }

    // создаем список пар (Размер файла, Имя файла)
    val listOfFileAndSize = mutableListOf<Pair<Long, String>>()

    for (file in listOfFiles) {
        listOfFileAndSize.add(Pair(sizeFile(file), file.name))
    }

    // работа с флагами
    if (flagSi) basis = 1000
    if (flagC) {
        println(humanReadable(listOfFileAndSize.sumOf { it.first }, flagH))
        return

    }
    else listOfFileAndSize.forEach { println("${humanReadable(it.first, flagH)} -- ${it.second}") }
}

fun sizeFile(file: File): Long {
    return if (file.isDirectory) file.listFiles().map { sizeFile(it) }.sum()
    else file.length()
}

fun humanReadable(size: Long, flagH: Boolean): String {
    if (!flagH) return "${round(size * 100.0 / basis) / 100.0}"
    else {
        val giga = basis * basis * basis
        val mega = basis * basis
        if (size >= giga) return "${round(size * 100.0 / giga) / 100.0} GB"
        if (size >= mega) return "${round(size * 100.0 / mega) / 100.0} MB"
        if (size >= basis) return "${round(size * 100.0 / basis) / 100.0} KB"
        return "$size B"
    }
}
