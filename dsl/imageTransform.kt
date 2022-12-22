package imagetransform

import java.io.File
import javax.imageio.ImageIO
import java.awt.Color

/** Définir un mini-DSL pour la transformation d'images */
class ImageTransform(val inFileName: String, val outFileName: String) {
    val image = File(inFileName).let { ImageIO.read(it) }
    val width = image.width
    val height = image.height

    /** les propriétés qui suivent seront initialisées par l'utilisateur */
    private lateinit var pixelFilter: (Pair<Int, Int>) -> Boolean
    private lateinit var transformRGB: (Int) -> Int
    fun selectPixels(condition: (Pair<Int, Int>) -> Boolean) { pixelFilter = condition }
    fun transform(t: (Int) -> Int) { transformRGB = t }

    fun execute() {
        if (!::pixelFilter.isInitialized || !::transformRGB.isInitialized) {
            println("ImageTransform not completely initialized. Aborting execution.")
            return
        }
        image.apply {
            for ((x, y) in xyRectangleSeq(width, height).filter(pixelFilter)) {
                setRGB(x, y, transformRGB(getRGB(x, y)))
            }
        }.also {
            ImageIO.write(it, "png", File(outFileName))
            println("transformed $inFileName into $outFileName")
        }
    }

    /* Les propriétés qui suivent sont disponibles pour aider à choisir des pixels */
    val shortestDimension: Int
        get() = if (width < height) width else height

    val Pair<Int, Int>.centered
        get() = Pair(first - width/2, second - height/2)
}

/** Fonctions auxiliares pour choisir des pixels */

/** Séquence des nombres entiers naturels. */
private val naturels = generateSequence(0) { it + 1 }

/** Séquence (= "stream") de coordonnées (x, y) dans un rectangle. */
private fun xyRectangleSeq(width: Int, height: Int): Sequence<Pair<Int, Int>> =
    naturels.take(width).flatMap { x ->
        naturels.take(height).map { y ->
            x to y
        }
    }

/** Vérifier si un point est dans un cercle centré sur l'origine */
infix fun Pair<Int, Int>.inCircle(r: Int) = first*first + second*second < r*r


/** Fonctions auxiliaires pour transformer un pixel */

/** Inverser les bits d'un nombre dont la taille est un octet
 * si ce n'est pas le cas, ne rien faire
 * NB: le masquage est nécessaire pour ne pas obtenir un négatif
 */
fun Int.invertOctet() =
    if (0 <= this && this <= 255) this.inv() and 0b11111111 else this

/* Retourner le "négatif" d'une couleur */
fun Color.negative() = Color(
    getRed().invertOctet(),
    getGreen().invertOctet(),
    getBlue().invertOctet(),
    getAlpha()
)

fun negativeRGB(rgb: Int) = Color(rgb).negative().getRGB()
