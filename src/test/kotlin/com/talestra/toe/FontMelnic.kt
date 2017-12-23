package com.talestra.toe

import com.soywiz.korim.awt.awtShowImage
import com.soywiz.korim.bitmap.Bitmap1
import com.soywiz.korim.bitmap.Bitmap32
import com.soywiz.korim.color.Colors
import com.soywiz.korio.Korio
import com.soywiz.korio.stream.openFastStream
import com.soywiz.korio.vfs.uniVfs

object FontMelnic {
    @JvmStatic
    fun main(args: Array<String>) = Korio {
        //val font = "old/SRC/FONT_NORMAL.DAT".uniVfs.readAll().openFastStream()
        val font = "old/SRC/FONT_MELNIC.DAT".uniVfs.readAll().openFastStream()
        //font.skip(0x150)
        //font.skip(0x20)

        val cols = 24
        val rows = 24

        val out = Bitmap32(cols * 11, rows * 11)
        var n = 0
        while (!font.eof) {
            val width = font.readU8()
            val content = font.readBytes(15) + byteArrayOf(0)
            val bmp = Bitmap1(11, 11, content, intArrayOf(Colors.BLACK, Colors.RED))
            //showImageAndWait(bmp.toBMP32().scaleNearest(8, 8))
            val x = n % cols
            val y = n / cols
            out.put(bmp.toBMP32().flipX(), x * 11, y * 11)
            n++
        }
        awtShowImage(out)
    }
}

fun Bitmap32.flipX() = this.apply {
    for (y in 0 until height) {
        for (x in 0 until width / 2) {
            val a = this[x, y]
            val b = this[width - x - 1, y]
            this[width - x - 1, y] = a
            this[x, y] =  b
        }
    }
}