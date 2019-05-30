

import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.FontImageMap
import org.openrndr.draw.loadImage
import org.openrndr.draw.tint
import org.openrndr.text.Writer

fun main() = application {
    configure {
        width = 768
        height = 576
    }

    program {
        val font = FontImageMap.fromUrl("file:data/fonts/IBMPlexMono-Regular.ttf", 64.0)

        extend {
            drawer.background(ColorRGBa.PINK)
            drawer.fill = ColorRGBa.BLACK
            drawer.fontMap = font
            val w = Writer(drawer)

            w.newLine()
            w.text("Hello my name is")
            w.newLine()
            w.text("<your name here>")
        }
    }
}