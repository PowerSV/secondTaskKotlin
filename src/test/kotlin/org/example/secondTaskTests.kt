package org.example

import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertEquals


class SecondTaskTests {

    @Test
    fun testForHumanReadable(){
        assertEquals("${1024.0 / basis}", humanReadable(1024, false))
    }

    private val outContent = ByteArrayOutputStream()
    private val errContent = ByteArrayOutputStream()
    private val originalOut = System.out
    private val originalErr = System.err

    @Before
    fun setUpStreams() {
        System.setOut(PrintStream(outContent))
        System.setErr(PrintStream(errContent))
    }

    @Test
    fun testMain1() {
        main(arrayOf("-h", "forTest"))
        assertEquals("21.05 MB -- forTest\r\n", outContent.toString())
    }

    @Test
    fun testMain7() {
        main(arrayOf("forTest\\floppa.jpg", "forTest\\pizza.txt", "forTest\\answers.xlsx", "-h", "-c"))
        assertEquals("21.05 MB\r\n", outContent.toString())
    }

    @Test
    fun testMain2() {
        main(arrayOf("forTest"))
        assertEquals("21553.13 -- forTest\r\n", outContent.toString())
    }

    @Test
    fun testMain3() {
        main(arrayOf("forTest\\answers.xlsx"))
        assertEquals("21481.75 -- answers.xlsx\r\n", outContent.toString())
    }

    @Test
    fun testMain4() {
        main(arrayOf("forTest\\floppa.jpg", "-h"))
        assertEquals("69.2 KB -- floppa.jpg\r\n", outContent.toString())
    }

    @Test
    fun testMain5() {
        main(arrayOf("forTest\\floppa.jpg", "-h", "--si"))
        assertEquals("70.86 KB -- floppa.jpg\r\n", outContent.toString())
    }

    @Test
    fun testMain6() {
        main(arrayOf("forTest\\floppa.jpg", "forTest\\pizza.txt"))
        assertEquals("69.2 -- floppa.jpg\r\n2.18 -- pizza.txt\r\n", outContent.toString())
    }

    @Test
    fun testMain8() {
        main(arrayOf("forTest\\floppa.jpg", "forTest\\pizza.txt", "forTest\\answers.xlsx", "-h", "-c", "--si"))
        assertEquals("22.07 MB\r\n", outContent.toString())
    }

    @Test
    fun testMain9() {
        main(arrayOf("h"))
        assertEquals("", errContent.toString()) // важно что "process finished with exit code 1"
    }

    @After
    fun restoreStreams() {
        System.setOut(originalOut)
        System.setErr(originalErr)
    }

}
