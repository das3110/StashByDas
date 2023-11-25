
import android.content.Context
import com.example.stashbydas.Clases.Producto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object StorageUtil {

    private const val SHARED_PREFS_FILE = "ProductosPref"
    private const val PRODUCTOS_KEY = "listaProductos"

    fun guardarListaProductos(context: Context, listaProductos: List<Producto>) {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val json = Gson().toJson(listaProductos)
        editor.putString(PRODUCTOS_KEY, json)
        editor.apply()
    }

    fun obtenerListaProductos(context: Context): List<Producto> {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE)
        val json = sharedPreferences.getString(PRODUCTOS_KEY, null)
        return if (json != null) {
            val type = object : TypeToken<List<Producto>>() {}.type
            Gson().fromJson(json, type)
        } else {
            emptyList()
        }
    }
}