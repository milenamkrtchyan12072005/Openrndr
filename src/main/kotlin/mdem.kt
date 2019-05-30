import org.openrndr.animatable.Animatable
import org.openrndr.animatable.easing.Easing
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
            width = 1280
            height = 720
        }

        program {
            val videoPlayer = FFMPEGVideoPlayer.fromDevice()
            videoPlayer.start()

            var imageColor = ColorRGBa.WHITE
            var scale = 1.0
            var clear = false

            class Brush : Animatable() {
                var scale = 1.0
                var rotation = 0.0
                var amplitude = 0.0
                var hi = 0.0


            }

            val brush = Brush()

            mouse.clicked.listen {
                imageColor = ColorRGBa(Math.random(), Math.random(), Math.random())
            }

            keyboard.character.listen {
                if (it.character == 'a') {
                    scale = Math.random()
                }
                if (it.character == 's') {
                    clear = true
                }

                if (it.character == 'd') {
                    brush.cancel()
                    brush.animate("scale",
                            Math.random(),
                            1000,
                            Easing.CubicInOut)
                }
                if (it.character == 'k') {
                    brush.animate("amplitude",
                            Math.random() * 500.0,
                            1000,
                            Easing.CubicInOut)

                }
                if (it.character == 'l') {
                    brush.animate("hi",
                            Math.random() * 4.0,
                            1000,
                            Easing.CubicInOut)

                }
            }
            extend(NoClear())
            extend {

                brush.updateAnimation()

                if (clear) {
                    drawer.background(ColorRGBa.BLACK)
                    clear = false
                }

                videoPlayer.next()
                //drawer.translate(140.0, 140.0)
                drawer.translate(mouse.position)

                drawer.isolated {
                    drawer.drawStyle.colorMatrix = tint(imageColor) * grayscale()
                    drawer.scale(brush.scale)
//                    drawer.drawStyle.colorMatrix = tint(ColorRGBa(Math.random(),
//                            Math.random(),
//                            Math.random())) * grayscale()
//                    val r = Math.cos(seconds)*0.5+0.5
//                    val g = Math.sin(seconds)*0.5+0.5
//                    val b = Math.sin(seconds*1.32)*0.5+0.5
                    //drawer.drawStyle.colorMatrix = tint(ColorRGBa(r,g,b)) * grayscale()

                    videoPlayer.draw(drawer)
                }


                //drawer.background(ColorRGBa(1.0, 1.0, 0.8 ))
                drawer.fill = ColorRGBa.BLUE
                drawer.stroke = ColorRGBa.RED
                drawer.stroke = null


                for (i in 0 until 500) {
                    val x = Math.cos((i + seconds * 0.1) * 1.0) * brush.amplitude
                    val y = Math.sin((i * brush.hi + seconds * 0.1) * 1.0) * brush.amplitude
                    val r = Math.cos((i + seconds * brush.hi) * 0.25) * 20.0 + 20.0

                    val s = Math.cos(i * 0.1) * 0.5 + 0.5
                    drawer.fill = ColorRGBa.PINK.shade(s)

                    drawer.circle(x + width / 2.0 + x,
                            height / 2.0 + y, r)


                }


            }
        }
    }
}
