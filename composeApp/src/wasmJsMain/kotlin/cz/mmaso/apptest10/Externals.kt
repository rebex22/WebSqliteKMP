package cz.mmaso.apptest10

// Toto říká Kotlinu, jak vypadá JS objekt Date
@OptIn(ExperimentalWasmJsInterop::class)
external class Date : JsAny {
    // --- Konstruktory ---

    // 1. Prázdný (aktuální čas)
    constructor()

    // 2. Z čísla (milisekundy od 1.1.1970)
    constructor(milliseconds: Double)

    // 3. Z řetězce (např. "2023-12-24")
    constructor(dateString: String)

    // 4. Po složkách (měsíc je 0-indexed: Leden = 0)
    constructor(year: Int, month: Int)
    constructor(year: Int, month: Int, day: Int)
    constructor(year: Int, month: Int, day: Int, hour: Int)
    constructor(year: Int, month: Int, day: Int, hour: Int, minute: Int)
    constructor(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int)
    constructor(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int, millisecond: Int)

    // --- Gettery (Lokální čas) ---
    fun getDate(): Int          // Den v měsíci (1-31)
    fun getDay(): Int           // Den v týdnu (0-6, Neděle=0)
    fun getFullYear(): Int      // Rok (např. 2023)
    fun getHours(): Int         // (0-23)
    fun getMilliseconds(): Int  // (0-999)
    fun getMinutes(): Int       // (0-59)
    fun getMonth(): Int         // Měsíc (0-11, POZOR: 0 je Leden)
    fun getSeconds(): Int       // (0-59)
    fun getTime(): Double       // Timestamp (milisekundy od epochy)
    fun getTimezoneOffset(): Int // Rozdíl v minutách oproti UTC

    // --- Gettery (UTC) ---
    fun getUTCDate(): Int
    fun getUTCDay(): Int
    fun getUTCFullYear(): Int
    fun getUTCHours(): Int
    fun getUTCMilliseconds(): Int
    fun getUTCMinutes(): Int
    fun getUTCMonth(): Int
    fun getUTCSeconds(): Int

    // --- Settery (Lokální čas) ---
    // Vrací Double (nový timestamp), ale v Kotlinu návratovou hodnotu u setterů často ignorujeme
    fun setDate(day: Int): Double
    fun setFullYear(year: Int): Double
    fun setFullYear(year: Int, month: Int): Double
    fun setFullYear(year: Int, month: Int, day: Int): Double
    fun setHours(hour: Int): Double
    fun setHours(hour: Int, min: Int): Double
    fun setHours(hour: Int, min: Int, sec: Int): Double
    fun setHours(hour: Int, min: Int, sec: Int, ms: Int): Double
    fun setMilliseconds(ms: Int): Double
    fun setMinutes(min: Int): Double
    fun setMinutes(min: Int, sec: Int): Double
    fun setMinutes(min: Int, sec: Int, ms: Int): Double
    fun setMonth(month: Int): Double
    fun setMonth(month: Int, day: Int): Double
    fun setSeconds(sec: Int): Double
    fun setSeconds(sec: Int, ms: Int): Double
    fun setTime(time: Double): Double

    // --- Settery (UTC) ---
    fun setUTCDate(day: Int): Double
    fun setUTCFullYear(year: Int): Double
    fun setUTCFullYear(year: Int, month: Int): Double
    fun setUTCFullYear(year: Int, month: Int, day: Int): Double
    fun setUTCHours(hour: Int): Double
    fun setUTCHours(hour: Int, min: Int): Double
    fun setUTCHours(hour: Int, min: Int, sec: Int): Double
    fun setUTCHours(hour: Int, min: Int, sec: Int, ms: Int): Double
    fun setUTCMilliseconds(ms: Int): Double
    fun setUTCMinutes(min: Int): Double
    fun setUTCMinutes(min: Int, sec: Int): Double
    fun setUTCMinutes(min: Int, sec: Int, ms: Int): Double
    fun setUTCMonth(month: Int): Double
    fun setUTCMonth(month: Int, day: Int): Double
    fun setUTCSeconds(sec: Int): Double
    fun setUTCSeconds(sec: Int, ms: Int): Double

    // --- Konverze na String ---
    fun toDateString(): String      // Např. "Thu Dec 24 2023"
    fun toISOString(): String       // Např. "2023-12-24T12:00:00.000Z" (ISO 8601)
    fun toJSON(): String            // Stejné jako toISOString
    fun toLocaleDateString(): String
    fun toLocaleDateString(locales: String): String
    // Pro options argument by bylo nutné definovat další JsAny, pro jednoduchost vynecháno

    fun toLocaleString(): String
    fun toLocaleString(locales: String): String

    fun toLocaleTimeString(): String
    fun toLocaleTimeString(locales: String): String

    override fun toString(): String // Celý řetězec data a času
    fun toTimeString(): String      // Pouze čas
    fun toUTCString(): String       // UTC formát

    fun valueOf(): Double           // Primitivní hodnota (timestamp)

    // --- Statické metody (v JS volané jako Date.now()) ---
    // V Kotlin Wasm se externí statické metody definují v companion objectu
    companion object {
        fun now(): Double
        fun parse(dateString: String): Double
        fun UTC(year: Int, month: Int): Double
        fun UTC(year: Int, month: Int, day: Int): Double
        fun UTC(year: Int, month: Int, day: Int, hour: Int): Double
        fun UTC(year: Int, month: Int, day: Int, hour: Int, minute: Int): Double
        fun UTC(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int): Double
        fun UTC(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int, ms: Int): Double
    }
}

