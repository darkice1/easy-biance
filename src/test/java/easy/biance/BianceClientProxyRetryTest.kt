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
}
