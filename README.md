# 币安JAVASDK
快速接入币安的开源sdk。目前实现了部分接口。如果需要增加可以联系作者或者直接增加。

# 初始化
建立放config.txt中的url与api信息。config.txt示例
```text
BIANCE_URL=https://api.binance.com
BIANCE_KEY=my key
BIANCE_SECRET=my secret
```

# 币安API文档
1. 英文 https://github.com/binance/binance-spot-api-docs/blob/master/rest-api_CN.md
1. 中文比较老需要以英文为准 https://github.com/binance/binance-spot-api-docs/blob/master/rest-api.md

# 服务器时间
BianceClient.time()

# 交易规范信息
BianceClient.exchangeInfo()

# 当前平均价格
BianceClient.avgPrice()

# 最新价格接口
BianceClient.tickerPrice()

# 最优挂单接口