package tj.soni.englishgame.data.categories

import tj.soni.englishgame.data.Category
import tj.soni.englishgame.data.Word

object Jobs {
    val category = Category(
        id = "jobs",
        title = "Касбу корҳо",
        englishTitle = "Jobs & Professions",
        emoji = "💼",
        description = "Номи касбу ҳунарҳои гуногун",
        words = listOf(
            Word("job_1", "Doctor", "Духтур", "Доктэр", "The doctor helps people.", "Духтур ба одамон ёрӣ медиҳад."),
            Word("job_2", "Driver", "Ронанда", "Драйвэр", "My father is a driver.", "Падари ман ронанда аст."),
            Word("job_3", "Engineer", "Муҳандис", "Энҷиниэр", "She is an engineer.", "Ӯ муҳандис аст."),
            Word("job_4", "Farmer", "Деҳқон", "Фармэр", "The farmer grows food.", "Деҳқон маҳсулот парвариш мекунад."),
            Word("job_5", "Cook", "Ошпаз", "Кук", "The cook makes dinner.", "Ошпаз хӯроки шом мепазад."),
            Word("job_6", "Police officer", "Корманди полис", "Пэлис офисэр", "The police officer helps us.", "Корманди полис ба мо ёрӣ медиҳад."),
            Word("job_7", "Nurse", "Ҳамшира", "Нёрс", "The nurse works in a hospital.", "Ҳамшира дар беморхона кор мекунад."),
            Word("job_8", "Farmer", "Деҳқон", "Фармэр", "The farmer works on a farm.", "Деҳқон дар ферма кор мекунад."),
            Word("job_9", "Artist", "Рассом", "Артист", "The artist paints pictures.", "Рассом расм мекашад."),
            Word("job_10", "Programmer", "Барномасоз", "Программэр", "The programmer writes code.", "Барномасоз код менависад.")
        )
    )
}
