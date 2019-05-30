import org.openrndr.animatable.Animatable
import org.openrndr.animatable.easing.Easing
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.FontImageMap
import org.openrndr.draw.grayscale
import org.openrndr.draw.isolated
import org.openrndr.draw.tint
import org.openrndr.extra.noclear.NoClear
import org.openrndr.ffmpeg.FFMPEGVideoPlayer

fun main() {
    application {
        configure {
            windowResizable = true
        }
        program {
            val videoPlayer = FFMPEGVideoPlayer.fromDevice()
            videoPlayer.start()
            extend(NoClear())
            var imageColor = ColorRGBa.WHITE
            var scale = 0.0
            var clear = false

            class Brush: Animatable(){
                var scale = 1.0
                var rotation = 0.0
            }
            val brush = Brush()



            mouse.clicked.listen{
                imageColor = ColorRGBa(Math.random(),Math.random(),Math.random())
            }
            keyboard.character.listen{
                if (it.character == 'a'){
                    scale = Math.random()
                }
                if (it.character == 's'){
                    clear = true
                }
                if (it.character == 'd'){
                    brush.cancel()
                    brush.animate("scale",Math.random(),1000,Easing.CubicInOut)
                }
                if (it.character == 'r'){
                    brush.cancel()
                    brush.animate("rotation",Math.random()*360.0,1000,Easing.CubicInOut)
                }


            }
            extend {
                //                drawer.background(ColorRGBa.BLACK)
                videoPlayer.next()
                brush.updateAnimation()
                drawer.translate(mouse.position)

                val font = FontImageMap.fromUrl("file:data/fonts/IBMPlexMono-Regular.ttf", 32.0)
                if (clear){
                    drawer.background(ColorRGBa.BLACK)
                    clear = false
                }
                drawer.fontMap = font

                drawer.isolated {
                    drawer.translate(0.0, 40.0)

                    drawer.drawStyle.colorMatrix = tint(imageColor) * grayscale()
                    drawer.scale(brush.scale)
                    drawer.rotate(brush.rotation)
                    drawer.translate(-videoPlayer.width/2.0, -videoPlayer.height/2.0)

                    videoPlayer.draw(drawer)
                    drawer.fill = ColorRGBa.RED
                    drawer.text("REC",10.0,10.0)
                    drawer.circle(100.0,0.0,20.0)
                }

//                val r = Math.cos(seconds) * 0.5 + 0.5
//                val g = Math.cos(seconds) * 0.5 + 0.5
//                val b = Math.cos(seconds * 1.32)  * 0.5 + 0.5

//                drawer.drawStyle.colorMatrix = tint(ColorRGBa(r,g,b))

            }
        }
    }
}