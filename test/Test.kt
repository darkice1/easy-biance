import easy.biance.BianceClient

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

		println(BianceClient.time())
//		println(BianceClient.exchangeInfo())
//		println(BianceClient.klines("BTCUSDT"))
		println(BianceClient.avgPrice("BTCUSDT"))
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
		println(BianceClient.findorder("BTCUSDT",1))


	}
}