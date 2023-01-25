package wangdaye.com.geometricweather.common.basic.models.options.appearance

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.text.TextUtils
import wangdaye.com.geometricweather.R
import wangdaye.com.geometricweather.common.basic.models.options._basic.BaseEnum
import wangdaye.com.geometricweather.common.basic.models.options._basic.Utils
import java.util.*

enum class Language(
    override val id: String,
    val locale: Locale
): BaseEnum {

    FOLLOW_SYSTEM(
        "follow_system",
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Resources.getSystem().configuration.locales[0]
        } else {
            Resources.getSystem().configuration.locale
        }
    ),
    CATALAN("catalan", Locale("ca")),
    CZECH("czech", Locale("cs")),
    GERMAN("german", Locale("de")),
    ENGLISH_AU("english_australia", Locale("en", "AU")),
    ENGLISH_UK("english_britain", Locale("en", "GB")),
    ENGLISH_US("english_america", Locale("en", "US")),
    SPANISH("spanish", Locale("es")),
    FRENCH("french", Locale("fr")),
    INDONESIAN("indonesian", Locale("in")),
    ITALIAN("italian", Locale("it")),
    HUNGARIAN("hungarian", Locale("hu")),
    DUTCH("dutch", Locale("nl")),
    POLISH("polish", Locale("pl")),
    PORTUGUESE("portuguese", Locale("pt")),
    PORTUGUESE_BR("portuguese_brazilian", Locale("pt", "BR")),
    ROMANIAN("romanian", Locale("ro")),
    SLOVENIAN("slovenian", Locale("sl", "SI")),
    TURKISH("turkish", Locale("tr")),
    GREEK("greek", Locale("el")),
    RUSSIAN("russian", Locale("ru")),
    SERBIAN("serbian", Locale("sr")),
    ARABIC("arabic", Locale("ar")),
    KOREAN("korean", Locale("ko")),
    JAPANESE("japanese", Locale("ja")),
    CHINESE("chinese", Locale("zh", "CN")),
    UNSIMPLIFIED_CHINESE("unsimplified_chinese", Locale("zh", "TW"));

    val code: String
        get() {
            val locale = locale
            val language = locale.language
            val country = locale.country
            return if (!TextUtils.isEmpty(country)
                && (country.lowercase() == "tw" || country.lowercase() == "hk")
            ) {
                language.lowercase() + "-" + country.lowercase()
            } else {
                language.lowercase()
            }
        }

    val isChinese: Boolean
        get() = code.startsWith("zh")

    companion object {

        fun getInstance(
            value: String
        ) = when (value) {
            "catalan" -> CATALAN
            "czech" -> CZECH
            "german" -> GERMAN
            "english_australia" -> ENGLISH_AU
            "english_britain" -> ENGLISH_UK
            "english_america" -> ENGLISH_US
            "spanish" -> SPANISH
            "french" -> FRENCH
            "indonesian" -> INDONESIAN
            "italian" -> ITALIAN
            "hungarian" -> HUNGARIAN
            "dutch" -> DUTCH
            "polish" -> POLISH
            "portuguese" -> PORTUGUESE
            "portuguese_brazilian" -> PORTUGUESE_BR
            "romanian" -> ROMANIAN
            "slovenian" -> SLOVENIAN
            "turkish" -> TURKISH
            "greek" -> GREEK
            "russian" -> RUSSIAN
            "serbian" -> SERBIAN
            "arabic" -> ARABIC
            "korean" -> KOREAN
            "japanese" -> JAPANESE
            "chinese" -> CHINESE
            "unsimplified_chinese" -> UNSIMPLIFIED_CHINESE
            else -> FOLLOW_SYSTEM
        }
    }

    override val valueArrayId = R.array.language_values
    override val nameArrayId = R.array.languages

    override fun getName(context: Context) = Utils.getName(context, this)
}