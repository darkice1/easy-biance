package easy.biance

import easy.biance.enums.*
import easy.config.Config
import easy.io.EHttpClient
import easy.util.Format
import net.sf.json.JSON
import net.sf.json.JSONArray
import net.sf.json.JSONObject


/**
 * @Project：easy-biance
 * @Package：easy.biance
 * @Date：2021/3/13 14:09
 * @Author：Neo
 * @Address： starneo@gmail.com
 * @Description:
 * @Modified By:
 */
object BianceClient {
	private val url: String = Config.getProperty("BIANCE_URL")
	private val key = Config.getProperty("BIANCE_KEY")
	private val secret: String = Config.getProperty("BIANCE_SECRET")

	private val client = EHttpClient()

	private fun mapToUrl(map:HashMap<String,Any>):String{
		val buf = StringBuilder()
		map.forEach { (k, v) ->
			buf.append("$k=${java.net.URLEncoder.encode(v.toString(),"utf-8")}&")
		}
		if (buf.isNotEmpty())
		{
			buf.setLength(buf.length-1)
		}

		return buf.toString()
	}

	private fun request(funname:String, body:HashMap<String,Any>?,ishmac:Boolean=false,ispost:Boolean=false): JSON {

		val b = body ?: HashMap()
		if (ishmac)
		{
			b["timestamp"] =  System.currentTimeMillis()
		}

		val rbody = mapToUrl(b)

		val head = HashMap<String,String>()

		var surl = "$url$funname"

		if (ishmac)
		{
//			println(rbody)
//			surl = "$surl&signature=${signature(rbody)}"
			head["X-MBX-APIKEY"] = key
		}


//		val resp = client.get(surl,head,null)

		val resp = if(ispost)
		{
			val post = HashMap<String,String>()
			if (ishmac)
			{
//			println(rbody)
//			surl = "$surl&signature=${signature(rbody)}"
				head["X-MBX-APIKEY"] = key

				post[""] = "$rbody&signature=${signature(rbody)}"
			}

			client.postToString(surl,post,head)
		}
		else
		{
			if (rbody.isNotBlank())
			{
				surl = "$surl?$rbody"
			}
			println(surl)

			client.get(surl,head,null)
		}
/*		val resp = if (hmac){
			client.postToString(surl,post,head)
		}
		else
		{
			client.get(surl,head,null)
		}*/
//		println(resp)
		return if (resp.indexOf("{") == 0) {
			JSONObject.fromObject(resp)
		} else {
			JSONArray.fromObject(resp)
		}
	}

	/**
	 * 获取服务器时间
	 */
	fun time():JSON{
		return request("/api/v3/time",null)
	}

	/**
	 * 交易规范信息
	 */
	fun exchangeInfo():JSON{
		return request("/api/v3/exchangeInfo",null)
	}


	private fun signature(msg: String): String {
		return Format.byte2hex(Format.HMACSha256(secret,msg))
	}


	/**
	Name	Type	Mandatory	Description
	symbol	STRING	YES
	interval	ENUM	YES
	startTime	LONG	NO
	endTime	LONG	NO
	limit	INT	NO	Default 500; max 1000.
	 */
	fun klines(symbol:String, interval: Interval = Interval.DAY1, startTime:Long?=null, endTime:Long?=null, limit:Int=500):JSONArray{
		val map = HashMap<String,Any>()
		map["symbol"] = symbol
		map["interval"] = interval.valstr

		addToMap(map,"startTime,",startTime)
		addToMap(map,"endTime,",endTime)

		map["limit"] = limit

		return request("/api/v3/klines",map) as JSONArray
	}

	/**
	 * 当前平均价格
	 */
	fun avgPrice(symbol:String):JSON{
		val map = HashMap<String,Any>()
		map["symbol"] = symbol

		return request("/api/v3/avgPrice",map)
	}

	fun tickerPrice(symbol:String?=null):JSON{
		val map = HashMap<String,Any>()
		addToMap(map,"symbol,",symbol)

		return request("/api/v3/ticker/price",map)
	}

	fun bookPrice(symbol:String?=null):JSON{
		val map = HashMap<String,Any>()
		addToMap(map,"symbol,",symbol)

		return request("/api/v3/ticker/bookTicker",map)
	}

	private fun addToMap(map:HashMap<String,Any>, name:String, value:Any?)
	{
		if (value != null)
		{
			map[name] = value
		}
	}

	fun order(symbol:String, side:Side, type:Type, timeInForce:TimeInForce?=null,
	          quantity:Double,quoteOrderQty:Double?=null, price:Double?=null, newClientOrderId:String?=null,
	          stopPrice:Double?=null, icebergQty:Double?=null, newOrderRespType:NewOrderRespType?=null,
	          recvWindow:Long?=null):JSON{
		val map = HashMap<String,Any>()
		map["symbol"] = symbol
		map["side"] = side
		map["type"] = type

		addToMap(map,"timeInForce,",timeInForce)
		addToMap(map,"quantity",quantity)
		addToMap(map,"quoteOrderQty",quoteOrderQty)
		addToMap(map,"price",price)
		addToMap(map,"stopPrice",stopPrice)
		addToMap(map,"icebergQty",icebergQty)
		addToMap(map,"newOrderRespType",newOrderRespType)
		addToMap(map,"newClientOrderId",newClientOrderId)
		addToMap(map,"recvWindow",recvWindow)

		return request("/api/v3/order",map,true,ispost = true)
	}
}