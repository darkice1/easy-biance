package easy.biance

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class BianceClientProxyRetryTest {
	private val client = BianceClient(
		url = "https://api.binance.com",
		key = "test-key",
		secret = "test-secret"
	)

	@Test
	fun shouldRetryWithProxyWhenCodeIsMinus2015() {
		val response = """{"msg":"Invalid API-key, IP, or permissions for action.","code":-2015}"""
		assertTrue(client.shouldRetryWithProxy(response))
	}

	@Test
	fun shouldNotRetryWithProxyWhenCodeIsNotMinus2015() {
		val response = """{"msg":"Timestamp for this request is outside of the recvWindow.","code":-1021}"""
		assertFalse(client.shouldRetryWithProxy(response))
	}

	@Test
	fun parseProxyEndpointSupportsSocks5h() {
		val endpoint = client.parseProxyEndpoint("socks5h://127.0.0.1:2080")
		assertNotNull(endpoint)
		assertEquals("socks5h", endpoint.scheme)
		assertEquals("127.0.0.1", endpoint.host)
		assertEquals(2080, endpoint.port)
	}

	@Test
	fun parseProxyEndpointUsesDefaultSocksPortWhenMissingPort() {
		val endpoint = client.parseProxyEndpoint("127.0.0.1")
		assertNotNull(endpoint)
		assertEquals("socks5h", endpoint.scheme)
		assertEquals("127.0.0.1", endpoint.host)
		assertEquals(1080, endpoint.port)
	}

	@Test
	fun parseProxyEndpointReturnsNullForOffValue() {
		assertNull(client.parseProxyEndpoint("off"))
	}

	@Test
	fun endpointCacheKeyShouldIgnoreQueryString() {
		val first = client.endpointCacheKey(
			requestUrl = "https://api.binance.com/api/v3/account?timestamp=1&recvWindow=5000",
			isPost = false
		)
		val second = client.endpointCacheKey(
			requestUrl = "https://api.binance.com/api/v3/account?timestamp=2&recvWindow=1000",
			isPost = false
		)

		assertEquals(first, second)
		assertTrue(first.endsWith("GET /api/v3/account"))
	}

	@Test
	fun cacheProxyPreferredEndpointShouldBeMethodAwareAndPathIsolated() {
		val requestUrl = "https://api.binance.com/api/v3/account?timestamp=1"
		val otherPathUrl = "https://api.binance.com/api/v3/openOrders?timestamp=1"
		client.clearProxyPreferredEndpointCache()
		client.cacheProxyPreferredEndpoint(requestUrl, isPost = false)

		assertTrue(client.hasCachedProxyPreferredEndpoint("https://api.binance.com/api/v3/account?timestamp=9", isPost = false))
		assertFalse(client.hasCachedProxyPreferredEndpoint("https://api.binance.com/api/v3/account?timestamp=9", isPost = true))
		assertFalse(client.hasCachedProxyPreferredEndpoint(otherPathUrl, isPost = false))
	}

	@Test
	fun cacheProxyPreferredEndpointShouldSurviveClientRebuild() {
		val firstClient = BianceClient(
			url = "https://api.binance.com",
			key = "same-key",
			secret = "secret-a"
		)
		val secondClient = BianceClient(
			url = "https://api.binance.com",
			key = "same-key",
			secret = "secret-b"
		)
		val requestUrl = "https://api.binance.com/api/v3/account?timestamp=1"

		firstClient.clearProxyPreferredEndpointCache()
		firstClient.cacheProxyPreferredEndpoint(requestUrl, isPost = false)
		assertTrue(secondClient.hasCachedProxyPreferredEndpoint(requestUrl, isPost = false))
	}

	@Test
	fun cacheProxyPreferredEndpointShouldNotLeakAcrossApiKeys() {
		val firstClient = BianceClient(
			url = "https://api.binance.com",
			key = "key-a",
			secret = "secret-a"
		)
		val secondClient = BianceClient(
			url = "https://api.binance.com",
			key = "key-b",
			secret = "secret-b"
		)
		val requestUrl = "https://api.binance.com/api/v3/account?timestamp=1"

		firstClient.clearProxyPreferredEndpointCache()
		firstClient.cacheProxyPreferredEndpoint(requestUrl, isPost = false)
		assertFalse(secondClient.hasCachedProxyPreferredEndpoint(requestUrl, isPost = false))
	}

	@Test
	fun cacheProxyPreferredEndpointShouldRespectTtl() {
		val requestUrl = "https://api.binance.com/api/v3/account?timestamp=1"
		client.clearProxyPreferredEndpointCache()
		client.cacheProxyPreferredEndpoint(requestUrl, isPost = false, ttlMillis = 0)

		assertFalse(client.hasCachedProxyPreferredEndpoint(requestUrl, isPost = false))
	}
}
