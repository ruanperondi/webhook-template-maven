package br.com.ruanperondi.webhooks.domain.entity;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.ruanperondi.webhooks.domain.exceptions.WebhookException;

class WebhookClientTest {

    private static final String SECRET_KEY = "super-secret";
    private static final String HEADER_KEY = "X-Webhook-Signature";
    private static final String TIMESTAMP_KEY = "t";
    private static final String SIGNATURE_KEY = "v1";
    private static final String SEPARATOR = ",";
    private static final String PAYLOAD = "{\"message\":\"hello\"}";
    private final WebhookClient client = WebhookClient.builder()
            .id(UUID.randomUUID())
            .name("Test Client")
            .headerKey(HEADER_KEY)
            .secretKey(SECRET_KEY)
            .timestampHeaderKey(TIMESTAMP_KEY)
            .signatureHeaderKey(SIGNATURE_KEY)
            .separator(SEPARATOR)
            .build();

    private String generateSignature(String timestamp, String payload, String secret) {
        try {
            String data = timestamp + "." + payload;
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            return Hex.encodeHexString(mac.doFinal(data.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate signature", e);
        }
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when name is blank")
    void shouldThrowWhenNameIsBlank() {
        assertThatThrownBy(() -> WebhookClient.builder()
                .id(UUID.randomUUID())
                .name(" ")
                .headerKey(HEADER_KEY)
                .secretKey(SECRET_KEY)
                .build()
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("name must not be blank");
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when headerKey is blank")
    void shouldThrowWhenHeaderKeyIsBlank() {
        assertThatThrownBy(() -> WebhookClient.builder()
                .id(UUID.randomUUID())
                .name("Test Client")
                .headerKey("")
                .secretKey(SECRET_KEY)
                .build()
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("headerKey must not be blank");
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when secretKey is blank")
    void shouldThrowWhenSecretKeyIsBlank() {
        assertThatThrownBy(() -> WebhookClient.builder()
                .id(UUID.randomUUID())
                .name("Test Client")
                .headerKey(HEADER_KEY)
                .secretKey(" ")
                .build()
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("secretKey must not be blank");
    }

    @Test
    @DisplayName("Should throw when headers are null or empty")
    void shouldThrowWhenHeadersAreEmpty() {
        assertThatThrownBy(() -> client.ensureRequestIsAuthentic(null, PAYLOAD))
                .isInstanceOf(WebhookException.class)
                .hasMessageContaining("Headers cannot be null or empty");
    }

    @Test
    @DisplayName("Should throw when payload is blank")
    void shouldThrowWhenPayloadIsBlank() {
        Map<String, Object> headers = Map.of(HEADER_KEY, "t=123,v1=abc");
        assertThatThrownBy(() -> client.ensureRequestIsAuthentic(headers, " "))
                .isInstanceOf(WebhookException.class)
                .hasMessageContaining("Payload cannot be null or empty");
    }

    @Test
    @DisplayName("Should throw when header key is missing")
    void shouldThrowWhenHeaderKeyIsMissing() {
        Map<String, Object> headers = Map.of("Other-Header", "t=123,v1=abc");
        assertThatThrownBy(() -> client.ensureRequestIsAuthentic(headers, PAYLOAD))
                .isInstanceOf(WebhookException.class)
                .hasMessageContaining("Header key not found in headers");
    }

    @Test
    @DisplayName("Should throw when header value is not a string")
    void shouldThrowWhenHeaderValueIsNotString() {
        Map<String, Object> headers = Map.of(HEADER_KEY, 123);
        assertThatThrownBy(() -> client.ensureRequestIsAuthentic(headers, PAYLOAD))
                .isInstanceOf(WebhookException.class)
                .hasMessageContaining("Header value must be a string");
    }

    @Test
    @DisplayName("Should throw when header is missing timestamp or signature")
    void shouldThrowWhenHeaderMissingParts() {
        Map<String, Object> headers = Map.of(HEADER_KEY, "t=123");
        assertThatThrownBy(() -> client.ensureRequestIsAuthentic(headers, PAYLOAD))
                .isInstanceOf(WebhookException.class)
                .hasMessageContaining("Missing timestamp or signature");
    }

    @Test
    @DisplayName("Should throw when timestamp is invalid (non-numeric)")
    void shouldThrowWhenTimestampIsInvalid() {
        Map<String, Object> headers = Map.of(HEADER_KEY, "t=abc,v1=xyz");
        assertThatThrownBy(() -> client.ensureRequestIsAuthentic(headers, PAYLOAD))
                .isInstanceOf(WebhookException.class)
                .hasMessageContaining("Invalid timestamp format or expired");
    }

    @Test
    @DisplayName("Should throw when timestamp is expired")
    void shouldThrowWhenTimestampIsExpired() {
        long expiredTimestamp = (System.currentTimeMillis() / 1000L) - 1000;
        Map<String, Object> headers = Map.of(HEADER_KEY, "t=" + expiredTimestamp + ",v1=xyz");
        assertThatThrownBy(() -> client.ensureRequestIsAuthentic(headers, PAYLOAD))
                .isInstanceOf(WebhookException.class)
                .hasMessageContaining("Invalid timestamp format or expired");
    }

    @Test
    @DisplayName("Should throw when signature is invalid")
    void shouldThrowWhenSignatureIsInvalid() {
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000L); // timestamp válido
        Map<String, Object> headers = Map.of(HEADER_KEY, "t=" + timestamp + ",v1=wrong-signature");

        assertThatThrownBy(() -> client.ensureRequestIsAuthentic(headers, PAYLOAD))
                .isInstanceOf(WebhookException.class)
                .hasMessageContaining("Signature verification failed");
    }

    @Test
    @DisplayName("Should pass with valid timestamp and signature")
    void shouldPassWithValidSignature() {
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000L); // timestamp válido
        String signature = generateSignature(timestamp, PAYLOAD, SECRET_KEY);
        Map<String, Object> headers = Map.of(HEADER_KEY, "t=" + timestamp + ",v1=" + signature);

        assertThatCode(() -> client.ensureRequestIsAuthentic(headers, PAYLOAD))
                .doesNotThrowAnyException();
    }
}
