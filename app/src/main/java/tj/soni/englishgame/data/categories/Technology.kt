package tj.soni.englishgame.data.categories

import tj.soni.englishgame.data.Category
import tj.soni.englishgame.data.Word

object Technology {
    val category = Category(
        id = "technology",
        title = "Технология ва интернет",
        englishTitle = "Technology & Internet",
        emoji = "📱",
        description = "Калимаҳои ҷаҳони рақамӣ",
        words = listOf(
            Word("tech_1", "Phone", "Телефон", "Фоун", "My phone is new.", "Телефони ман нав аст."),
            Word("tech_2", "Internet", "Интернет", "Интэрнет", "The internet is useful.", "Интернет фоиданок аст."),
            Word("tech_3", "Website", "Вебсайт", "Вебсайт", "I have a website.", "Ман вебсайт дорам."),
            Word("tech_4", "Keyboard", "Клавиатура", "Киборд", "The keyboard is black.", "Клавиатура сиёҳ аст."),
            Word("tech_5", "Screen", "Экран", "Скрин", "Look at the screen.", "Ба экран нигоҳ кун."),
            Word("tech_6", "Password", "Рамз", "Пасворд", "My password is secret.", "Рамзи ман махфӣ аст."),
            Word("tech_7", "Email", "Почтаи электронӣ", "Имейл", "Send me an email.", "Ба ман паёми электронӣ фирист."),
            Word("tech_8", "Download", "Зеркашӣ кардан", "Даунлоуд", "Download the app.", "Барномаро зеркашӣ кун."),
            Word("tech_9", "Computer", "Компютер", "Компютэр", "The computer is fast.", "Компютер тез аст."),
            Word("tech_10", "Application", "Барнома", "Эпликейшн", "This application is useful.", "Ин барнома фоиданок аст.")
        )
    )
}
