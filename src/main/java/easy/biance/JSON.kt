package easy.biance

import org.json.JSONArray
import org.json.JSONObject

sealed class JSON(open val raw: Any) {

    /** JSONObject 封装 */
    data class Obj(val obj: JSONObject) : JSON(obj)

    /** JSONArray 封装 */
    data class Arr(val arr: JSONArray) : JSON(arr)

    /* ---------- 类型安全的访问函数 ---------- */

    /** 转成 JSONObject；若不是则抛异常 */
    fun asJSONObject(): JSONObject =
        (this as? Obj)?.obj ?: error("$raw 不是 JSONObject")

    /** 转成 JSONArray；若不是则抛异常 */
    fun asJSONArray(): JSONArray =
        (this as? Arr)?.arr ?: error("$raw 不是 JSONArray")

    /* ---------- 便捷属性 ---------- */

    /** 判断是否为 JSONObject */
    val isObj get() = this is Obj

    /** 判断是否为 JSONArray */
    val isArr get() = this is Arr

    /* ---------- 统一工厂 ---------- */

    companion object {
        operator fun invoke(any: Any): JSON =
            when (any) {
                is JSONObject -> Obj(any)
                is JSONArray -> Arr(any)
                else -> error("$any 既不是 JSONObject 也不是 JSONArray")
                }
        }

}
