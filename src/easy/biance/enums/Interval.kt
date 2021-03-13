package easy.biance.enums

/**
 * @Project：easy-biance
 * @Package：easy.biance
 * @Date：2021/3/13 15:08
 * @Author：Neo
 * @Address： starneo@gmail.com
 * @Description:
 * @Modified By:
 */
enum class Interval(val valstr:String) {
	MINUTES1("1m"),
	MINUTES3("3m"),
	MINUTES5("5m"),
	MINUTES15("15m"),
	MINUTES30("30m"),

	HOUR1("1h"),
	HOUR2("2h"),
	HOUR4("4h"),
	HOUR6("6h"),
	HOUR8("8h"),
	HOUR12("12h"),

	DAY1("1d"),
	DAY3("3d"),

	WEEK1("1w"),
	MONTH1("1M")

}