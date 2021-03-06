import easy.biance.BianceClient
import easy.biance.enums.DailyRedemptionType
import easy.biance.enums.Side
import easy.biance.enums.Type
import easy.util.EDate
import net.sf.json.JSONObject

/**
 * @Project：easy-biance
 * @Package：
 * @Date：2021/3/13 14:13
 * @Author：Neo
 * @Address： starneo@gmail.com
 * @Description:
 * @Modified By:
 */
object Test {
	@JvmStatic
	fun main(args: Array<String>) {

		val b = BianceClient()

//		println(BianceClient.time())
//		println(BianceClient.exchangeInfo())
//		println(BianceClient.klines("BTCUSDT"))
// 		println(BianceClient.avgPrice("BTCUSDT"))
//		println(BianceClient.tickerPrice())
//		println(BianceClient.bookPrice())
/*
		println(BianceClient.order(symbol = "BTCUSDT",side = Side.BUY,type=Type.LIMIT,
			timeInForce=TimeInForce.GTC,price=1.0,quantity = 1.0))
*/
//		println(BianceClient.order(symbol = "BTCUSDT",side = Side.BUY,type=Type.MARKET, quantity = 1.0))

//		println(BianceClient.allOrders("BTCUSDT"))
//		println(BianceClient.account())
//		println(BianceClient.myTrades("BTCUSDT"))
//		println(BianceClient.orderTest(symbol = "BTCUSDT",side = Side.BUY,type= Type.MARKET, quantity = 1.0))
//		println(BianceClient.openOrders())
//		println(BianceClient.depth("BTCUSDT"))
//		println(BianceClient.trades("BTCUSDT"))
//		println(BianceClient.historicalTrades("BTCUSDT"))
//		println(BianceClient.aggTrades("BTCUSDT"))
//		println(BianceClient.hr24("BTCUSDT"))
//		println(BianceClient.findorder("BTCUSDT",1))
//		val num = 100.0
//		val usd = num*0.149479
//		val snum = getCnyToSymbolPrint("BTCUSDT",num)
//		println(snum)

//		val newClientOrderId = "TEST_${EDate().toString().replace(":","").replace(" ","")}"
//		val json = BianceClient.orderTest(symbol = "BTCUSDT",side = Side.BUY,type= Type.MARKET,newClientOrderId=newClientOrderId,
//			quantity = snum)

//		println(BianceClient.subaccountlist())
//		println(BianceClient.dailyproductlist())
//		println(BianceClient.dailyUserLeftQuota("USDT001"))
//		println(BianceClient.dailyPosition("USDT"))
		println(b.dailyUserRedemptionQuota("USDT001",DailyRedemptionType.FAST))
//		println(b.dailyPurchase("USDT001",0.01))

	}
}