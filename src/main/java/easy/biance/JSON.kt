package easy.biance

import org.json.JSONArray
import org.json.JSONObject

/** 所有 JSON 封装的共同接口 */
sealed interface JSON {
	/** 原生对象，方便偶尔直接透传 */
	val raw: Any
}

@JvmInline     // Kotlin >= 1.5，可省掉 data-class 的额外开销
value class ToJSONObject(val value: JSONObject) : JSON {
	override val raw get() = value
}

@JvmInline
value class ToJSONArray(val value: JSONArray) : JSON {
	override val raw get() = value
}

/**
 * 尝试将封装类型转为 `JSONArray`。
 * 仅当自身就是 `ToJSONArray` 时返回对应值；否则返回 null。
 */
fun JSON.asJSONArray(): JSONArray? = when (this) {
    is ToJSONArray -> this.value
    else           -> null
}

/**
 * 尝试将封装类型转为 `JSONObject`。
 * 仅当自身就是 `ToJSONObject` 时返回对应值；否则返回 null。
 */
fun JSON.asJSONObject(): JSONObject? = when (this) {
    is ToJSONObject -> this.value
    else            -> null
}