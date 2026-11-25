package tj.soni.englishgame.data.categories

import tj.soni.englishgame.data.Category
import tj.soni.englishgame.data.Word

object Transport {
    val category = Category(
        id = "transport",
        title = "Нақлиёт",
        englishTitle = "Transport",
        emoji = "🚗",
        description = "Намудҳои нақлиётро омӯз",
        words = listOf(
            Word("transport_1", "Car", "Мошин", "Кар", "I have a car.", "Ман мошин дорам."),
            Word("transport_2", "Bus", "Автобус", "Бас", "The bus is big.", "Автобус калон аст."),
            Word("transport_3", "Train", "Қатора", "Трейн", "The train is fast.", "Қатора тез аст."),
            Word("transport_4", "Airplane", "Ҳавопаймо", "Эйрплейн", "The airplane can fly.", "Ҳавопаймо парвоз мекунад."),
            Word("transport_5", "Bicycle", "Дучарха", "Байсикл", "I ride a bicycle.", "Ман дучарха меронам."),
            Word("transport_6", "Motorcycle", "Мотосикл", "Моутэрсайкл", "He has a motorcycle.", "Ӯ мотосикл дорад."),
            Word("transport_7", "Taxi", "Таксӣ", "Такси", "We take a taxi.", "Мо таксӣ мегирем."),
            Word("transport_8", "Truck", "Мошини боркаш", "Трак", "The truck is carrying food.", "Мошини боркаш хӯрок мебарад."),
            Word("transport_9", "Boat", "Қаиқ", "Боут", "The boat is on the water.", "Қаиқ дар рӯйи об аст."),
            Word("transport_10", "Ship", "Киштӣ", "Шип", "The ship is very large.", "Киштӣ хеле калон аст.")
        )
    )
}
