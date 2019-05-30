
import org.openrndr.animatable.Animatable
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.grayscale
import org.openrndr.draw.isolated
import org.openrndr.draw.tint
import org.openrndr.extra.noclear.NoClear
import org.openrndr.ffmpeg.FFMPEGVideoPlayer

fun main() {
    application {
        configure {
            width= 1280
            height = 720
        }
        program {
            val videoPlayer = FFMPEGVideoPlayer.fromDevice()
            videoPlayer.start()

            var imageColor = ColorRGBa.WHITE
            var scale = 1.0
            var clear = false

            class Brush:Animatable() {
                var scale = 1.0
            }
            val brush = Brush()

            mouse.clicked.listen{
                imageColor = ColorRGBa(Math.random(), Math.random(), Math.random())
            }

            keyboard.character.listen{
                if (it.character=='a') {
                    scale = Math.random()
                }
                if (it.character=='s') {
                    clear = true
                }

                if (it.character == 'd') {
                    brush.cancel()
                    brush

                }
            }


            extend(NoClear())
            extend {
//                drawer.background(ColorRGBa.BLACK)
                videoPlayer.next()

                drawer.translate(mouse.position)

                drawer.isolated {
                    drawer.drawStyle.colorMatrix = tint(imageColor) * grayscale()
                    drawer.scale(scale)

                    if (clear) {
                        drawer
                    }

//                drawer.drawStyle.colorMatrix = tint(ColorRGBa(Math.random(),
//                        Math.random(),
//                        Math.random())) * grayscale()
//
//                val r = Math.cos(seconds) * 0.5 + 0.5
//                val g = Math.sin(seconds) * 0.5 + 0.5
//                val b = Math.sin(seconds * 1.32) * 0.5 + 0.5
//
//                drawer.drawStyle.colorMatrix = tint(ColorRGBa(r,g,b))



                videoPlayer.draw(drawer)
                }
            }
        }
    }
}