package tj.soni.englishgame.data.categories

import tj.soni.englishgame.data.Category
import tj.soni.englishgame.data.Word

object People {
    val category = Category(
        id = "people",
        title = "Одамон ва оила",
        englishTitle = "People & Family",
        emoji = "👨‍👩‍👧",
        description = "Аъзои оила ва одамон",
        words = listOf(
            Word("people_1", "Father", "Падар", "Фазэр", "My father is kind.", "Падари ман меҳрубон аст."),
            Word("people_2", "Mother", "Модар", "Мазэр", "My mother is happy.", "Модари ман хушҳол аст."),
            Word("people_3", "Brother", "Бародар", "Бразэр", "I have a brother.", "Ман бародар дорам."),
            Word("people_4", "Sister", "Хоҳар", "Систэр", "My sister is young.", "Хоҳари ман ҷавон аст."),
            Word("people_5", "Family", "Оила", "Фэмили", "I love my family.", "Ман оилаамро дӯст медорам."),
            Word("people_6", "Friend", "Дӯст", "Френд", "He is my friend.", "Ӯ дӯсти ман аст."),
            Word("people_7", "Child", "Кӯдак", "Чайлд", "The child is playing.", "Кӯдак бозӣ мекунад."),
            Word("people_8", "Man", "Мард", "Мэн", "The man is tall.", "Мард қадбаланд аст."),
            Word("people_9", "Woman", "Зан", "Вумэн", "The woman is a teacher.", "Зан омӯзгор аст."),
            Word("people_10", "Teacher", "Омӯзгор", "Тичэр", "My teacher is nice.", "Омӯзгори ман хуб аст.")
        )
    )
}
