import easy.biance.JSON
import org.json.JSONObject

object TestJSON {
	@JvmStatic
	fun main(args: Array<String>) {
		val jo = JSONObject("{\"mins\":5,\"price\":\"2.35629123\",\"closeTime\":1748125634318}")
		val j = JSON(jo)
		println(j.asJSONObject())
	}
}