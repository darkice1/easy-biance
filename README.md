# 币安JAVASDK
快速接入币安的开源sdk。目前实现了最实用的部分接口。

# 升级信息
1. 1.0.5支持多实例可以传入参数进行实例化
2. 1.0.4增加部分币安接口

# 初始化
建立放config.txt中的url与api信息。config.txt示例
```text
BIANCE_URL=https://api.binance.com
BIANCE_KEY=my key
BIANCE_SECRET=my secret
```
# maven
```xml
<!-- https://mvnrepository.com/artifact/com.github.darkice1/easy-biance -->
<dependency>
    <groupId>com.github.darkice1</groupId>
    <artifactId>easy-biance</artifactId>
    <version>1.0.5</version>
</dependency>
```

# 币安API文档
1. 英文 https://github.com/binance/binance-spot-api-docs/blob/master/rest-api.md
1. 中文比较老需要以英文为准 https://github.com/binance/binance-spot-api-docs/blob/master/rest-api_CN.md
# 服务器时间
BianceClient.time()

# 交易规范信息
BianceClient.exchangeInfo()

# 深度信息
BianceClient.depth()

# 近期成交
BianceClient.trades()

# 查询历史成交
BianceClient.historicalTrades()

# 近期成交(归集)
BianceClient.aggTrades()

# K线数据
BianceClient.klines()

# 24hr价格变动情况
BianceClient.hr24()

# 查询订单
BianceClient.findorder()

# 最优挂单接口
BianceClient.bookTicker()

# 当前平均价格
BianceClient.avgPrice()

# 最新价格接口
BianceClient.tickerPrice()

# 最优挂单接口
BianceClient.bookPrice()

# 下单接口
BianceClient.order()

# 测试下单接口
BianceClient.orderTest()

# 查看账户当前挂单
BianceClient.openOrders()

# 获取所有订单信息
BianceClient.allOrders()

# 获取账户信息
BianceClient.account()

# 账户成交历史
BianceClient.myTrades()

# 获取子账号列表
BianceClient.subAccountList()

# 币安宝获取活期产品列表
BianceClient.dailyProductList()

# 币安宝获取用户当日剩余活期可申购余额
BianceClient.dailyUserLeftQuota()

# 币安宝获取用户活期产品持仓
BianceClient.dailyPosition()

# 币安宝获取用户当日活期可赎回余额
BianceClient.dailyUserRedemptionQuota()