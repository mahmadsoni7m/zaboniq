package tj.soni.englishgame.data.categories

import tj.soni.englishgame.data.Category
import tj.soni.englishgame.data.Word

object School {
    val category = Category(
        id = "school",
        title = "Ашё ва мактаб",
        englishTitle = "Objects & School",
        emoji = "🎒",
        description = "Ашёҳои ҳаррӯза ва мактаб",
        words = listOf(
            Word("school_1", "Book", "Китоб", "Бук", "I read a book.", "Ман китоб мехонам."),
            Word("school_2", "Pen", "Қалам", "Пен", "This is my pen.", "Ин қалами ман аст."),
            Word("school_3", "Pencil", "Қалами одӣ", "Пенсил", "I have a pencil.", "Ман қалами одӣ дорам."),
            Word("school_4", "Notebook", "Дафтар", "Ноутбук", "My notebook is new.", "Дафтари ман нав аст."),
            Word("school_5", "Bag", "Халта", "Бэг", "My bag is black.", "Халтаи ман сиёҳ аст."),
            Word("school_6", "Table", "Миз", "Тейбл", "The book is on the table.", "Китоб рӯйи миз аст."),
            Word("school_7", "Chair", "Курсӣ", "Чеэр", "Sit on the chair.", "Ба курсӣ шин."),
            Word("school_8", "Door", "Дар", "Дор", "Open the door.", "Дарро кушо."),
            Word("school_9", "Window", "Тиреза", "Уиндоу", "The window is open.", "Тиреза кушода аст."),
            Word("school_10", "Computer", "Компютер", "Компютэр", "I use a computer.", "Ман компютер истифода мебарам.")
        )
    )
}
