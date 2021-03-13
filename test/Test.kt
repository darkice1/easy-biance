import easy.biance.BianceClient
import easy.biance.enums.Side
import easy.biance.enums.Type

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
		println(BianceClient.order(symbol = "BTCUSDT",side = Side.BUY,type=Type.MARKET, quantity = 1.0))
	}
}