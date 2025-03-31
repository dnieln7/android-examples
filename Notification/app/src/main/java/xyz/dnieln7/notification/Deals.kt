package xyz.dnieln7.notification

import java.io.Serializable

data class Deal(
    val id: Int,
    val name: String,
    val oldPrice: Double,
    val newPrice: Double,
    val description: String,
    val image: String,
) : Serializable {
    companion object {
        const val INTENT_KEY = "DEAL_DATA"
    }
}

data class DealsData(
    val positionToShow: Int,
    val deals: List<Deal>,
) : Serializable

fun generateDeals(): DealsData {
    val deals = listOf(
        Deal(
            id = 1,
            name = "Nike Air Max 90 SE",
            oldPrice = 2799.0,
            newPrice = 1679.0,
            description = "Inheritors of great style, the Nike Air Max 90 SE stays true to its original roots with the iconic waffle outsole, stitched overlays and classic plastic details.",
            image = "https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/7f3980ba-981e-4e3f-9e38-37d6bc6d8d46/calzado-air-max-90-se-ckZLXS.png"
        ),
        Deal(
            id = 2,
            name = "Nike Venture Runner",
            oldPrice = 1699.0,
            newPrice = 1019.0,
            description = "The Nike Venture Runner pays homage to the iconic '80s shoe that started the running revolution.",
            image = "https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/d148dcb5-10b1-411f-a848-52f0dc36113f/calzado-venture-runner-DcBq1N.png"
        ),
        Deal(
            id = 3,
            name = "Nike Air Zoom SuperRep 3",
            oldPrice = 2999.0,
            newPrice = 1799.0,
            description = "Endless power on every rep with a shoe redesigned to provide stability and support with every move.",
            image = "https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/45b7bc03-4e5a-4dff-8680-d37692d48787/calzado-de-entrenamiento-hiit-air-zoom-superrep-3-WWc7rC.png"
        ),
        Deal(
            id = 4,
            name = "Nike Court Legacy Canvas",
            oldPrice = 999.0,
            newPrice = 799.0,
            description = "The Nike Court Legacy Canvas pays tribute to a history rooted in tennis culture, bringing you a classic in a modern, urban design.",
            image = "https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/45e5f978-0269-447b-b2e0-758432329e16/calzado-court-legacy-canvas-NKB2ZP.png"
        ),
        Deal(
            id = 5,
            name = "Nike Air Max 2021",
            oldPrice = 3599.0,
            newPrice = 2159.0,
            description = "Tie up your shoelaces and enjoy the new era of Air design.",
            image = "https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/85b23f1a-bc07-438b-9bc0-cd318cb8ba90/calzado-air-max-200-6DBBZd.png"
        ),
        Deal(
            id = 6,
            name = "Nike Air Max Dawn",
            oldPrice = 2599.0,
            newPrice = 1559.0,
            description = "The Air Max Dawn stays true to its roots and is made from at least 20% recycled material by weight.",
            image = "https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/cd24d530-e885-4b32-a912-777898b763a2/calzado-air-max-dawn-bsg6cw.png"
        ),
        Deal(
            id = 7,
            name = "Nike Air Zoom Tempo NEXT",
            oldPrice = 899.0,
            newPrice = 699.99,
            description = "While this speed shoe would easily pass the test on race day, it's twice as good for your training routine.",
            image = "https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/3dd3fc28-7cd4-475b-b3f0-a302b16c1d3e/calzado-de-running-en-carretera-air-zoom-tempo-next-zwkW5t.png"
        )
    )

    return DealsData(0, deals.shuffled())
}