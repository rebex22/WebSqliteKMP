import org.khronos.webgl.Uint8Array
import kotlin.js.Promise
import kotlinx.coroutines.await

// 1. Externí deklarace pro knihovnu idb-keyval
// Toto říká Kotlinu: "Existuje JS modul 'idb-keyval' s funkcemi get a set"
@JsModule("idb-keyval")
@JsNonModule
external object IdbLib {
    @OptIn(ExperimentalWasmJsInterop::class)
    fun get(key: String): Promise<JsAny?>
    fun set(key: String, value: JsAny): Promise<Unit>
}

// 2. Funkce pro NAČTENÍ databáze
@OptIn(ExperimentalWasmJsInterop::class)
suspend fun loadFromIndexedDb(dbName: String): Uint8Array? {
    return try {
        // Zavoláme JS funkci get() a počkáme na Promise
        val result = IdbLib.get(dbName).await()

        // Pokud je výsledek null nebo undefined, vrátíme null (první spuštění)
        if (result == null) return null

        // Přetypování JsAny na Uint8Array (což SQLDelight/sql.js potřebuje)
        // V Kotlin/Wasm musíme být opatrní s typy, zde předpokládáme, že jsme tam uložili Uint8Array
        result.unsafeCast<Uint8Array>()
    } catch (e: Exception) {
        println("Chyba při načítání z IndexedDB: $e")
        null
    }
}

// 3. Funkce pro ULOŽENÍ databáze
@OptIn(ExperimentalWasmJsInterop::class)
suspend fun saveToIndexedDb(dbName: String, data: Uint8Array) {
    try {
        // Jednoduše pošleme binární data do IndexedDB
        IdbLib.set(dbName, data).await()
        // println("Databáze uložena ($dbName)") // Odkomentovat pro debug
    } catch (e: Exception) {
        println("Chyba při ukládání do IndexedDB: $e")
    }
}

