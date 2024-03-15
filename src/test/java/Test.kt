import easy.biance.BianceClient
import easy.biance.enums.Account

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

//		println(b.time())
//		println(b.exchangeInfo())
//		println(b.klines("BTCUSDT"))
// 		println(BianceClient.avgPrice("BTCUSDT"))
//		println(BianceClient.tickerPrice())
//		println(BianceClient.bookPrice())
/*
		println(BianceClient.order(symbol = "BTCUSDT",side = Side.BUY,type=Type.LIMIT,
			timeInForce=TimeInForce.GTC,price=1.0,quantity = 1.0))
*/
//		println(BianceClient.order(symbol = "BTCUSDT",side = Side.BUY,type=Type.MARKET, quantity = 1.0))

//		println(b.allOrders("BTCUSDT"))
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
		val num = 100.0
		val usd = num*0.149479
//		val snum = getCnyToSymbolPrint("BTCUSDT",num)
//		println(snum)

//		val newClientOrderId = "TEST_${EDate().toString().replace(":","").replace(" ","")}"
//		val json = b.orderTest(symbol = "BTCUSDT",side = Side.BUY,type= Type.MARKET,newClientOrderId=newClientOrderId,
//			quantity = 0.1)
//		println(json)

//		println(BianceClient.subaccountlist())
//		println(BianceClient.dailyproductlist())
//		println(b.dailyUserLeftQuota("USDT001"))
//		println(BianceClient.dailyPosition("USDT"))
//		println(b.dailyUserRedemptionQuota("USDT001",DailyRedemptionType.FAST))
//		println(b.simpleEarnFlexibleList("USDT"))
//		println(b.dailyPurchase("USDT001",0.01))
		println(b.simpleEarnFlexiblePosition("USDT"))
////		println(b.simpleEarnAccount())
////		sleep(5000)
		println(b.simpleEarnFlexibleSubscribe("USDT001",1.0, autoSubscribe = false, sourceAccount = Account.SPOT))
		println(b.simpleEarnFlexiblePosition("USDT"))
		println(b.simpleEarnFlexibleRedeem("USDT001",false,1.0, Account.SPOT))
		println(b.simpleEarnFlexiblePosition("USDT"))
//		val logger = LoggerFactory.getLogger("ss")
//		println(Test::class.java)
//		val logger = LoggerFactory.getLogger(Test::class.java)
//		println("Before logging")
//		logger.info("Test log")
//		println("After logging")


	}
}