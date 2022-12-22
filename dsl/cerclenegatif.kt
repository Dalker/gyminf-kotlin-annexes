import imagetransform.*

/** test: rendre "négatifs" les pixels dans un certain cercle */
fun main(args: Array<String>) {
    val source = if (args.size < 2) "vim-tux.png" else args[0]
    val dest = if (args.size < 2) "vim-tux2.png" else args[0]
    ImageTransform(source, dest).apply {
        selectPixels { it.centered inCircle shortestDimension / 5 }
        transform { negativeRGB(it) }
    }.execute()
}
