package br.com.ruanperondi.webhooks.domain.entity;

import br.com.ruanperondi.webhooks.domain.exceptions.WebhookException;
import lombok.*;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Builder
@ToString
public final class WebhookClient {

    private static final long DEFAULT_MAX_AGE = 300L;
    private static final String DEFAULT_TIMESTAMP_KEY = "t";
    private static final String DEFAULT_SIGNATURE_KEY = "v1";
    private static final String DEFAULT_SEPARATOR = ";";

    private final UUID id;
    private final String name;
    private final String headerKey;
    private final String secretKey;
    private final long maxAge;
    private final String timestampHeaderKey;
    private final String signatureHeaderKey;
    private final String separator;

    @Builder
    public WebhookClient(
            UUID id,
            String name,
            String headerKey,
            String secretKey,
            Long maxAge,
            String timestampHeaderKey,
            String signatureHeaderKey,
            String separator
    ) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("name must not be blank");
        }
        if (StringUtils.isBlank(headerKey)) {
            throw new IllegalArgumentException("headerKey must not be blank");
        }
        if (StringUtils.isBlank(secretKey)) {
            throw new IllegalArgumentException("secretKey must not be blank");
        }

        this.id = Objects.requireNonNullElse(id, UUID.randomUUID());
        this.name = name;
        this.headerKey = headerKey;
        this.secretKey = secretKey;
        this.maxAge = (maxAge != null) ? maxAge : DEFAULT_MAX_AGE;
        this.timestampHeaderKey = StringUtils.defaultIfBlank(timestampHeaderKey, DEFAULT_TIMESTAMP_KEY);
        this.signatureHeaderKey = StringUtils.defaultIfBlank(signatureHeaderKey, DEFAULT_SIGNATURE_KEY);
        this.separator = StringUtils.defaultIfBlank(separator, DEFAULT_SEPARATOR);
    }

    public void ensureRequestIsAuthentic(Map<String, Object> headers, String payload) {
        if (MapUtils.isEmpty(headers)) {
            throw new WebhookException("Headers cannot be null or empty");
        }

        if (StringUtils.isBlank(payload)) {
            throw new WebhookException("Payload cannot be null or empty");
        }

        if (!headers.containsKey(headerKey)) {
            throw new WebhookException("Header key not found in headers");
        }

        Object headerValue = headers.get(headerKey);
        if (!(headerValue instanceof String header)) {
            throw new WebhookException("Header value must be a string");
        }

        Map<String, String> parsed = parseHeader(header);

        String timestamp = parsed.get(timestampHeaderKey);
        String signature = parsed.get(signatureHeaderKey);

        if (StringUtils.isBlank(timestamp) || StringUtils.isBlank(signature)) {
            throw new WebhookException("Missing timestamp or signature");
        }

        if (!isValidTimestamp(timestamp)) {
            throw new WebhookException("Invalid timestamp format or expired");
        }

        if (!isValidSignature(signature, payload, timestamp)) {
            throw new WebhookException("Signature verification failed");
        }
    }

    private Map<String, String> parseHeader(String header) {
        try {
            return Stream.of(header.split(separator))
                    .map(String::trim)
                    .map(kv -> kv.split("=", 2))
                    .filter(kv -> kv.length == 2)
                    .collect(Collectors.toMap(kv -> kv[0], kv -> kv[1]));
        } catch (Exception e) {
            throw new WebhookException("Invalid header format", e);
        }
    }

    private boolean isValidSignature(String signatureHeader, String payload, String timestampHeader) {
        String expectedSignature = generateExpectedSignature(payload, timestampHeader);
        return MessageDigest.isEqual(expectedSignature.getBytes(StandardCharsets.UTF_8), signatureHeader.getBytes(StandardCharsets.UTF_8));
    }

    private String generateExpectedSignature(String payload, String timestampHeader) {
        try {
            String data = timestampHeader + "." + payload;
            Mac hmacSha256 = Mac.getInstance("HmacSHA256");
            SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            hmacSha256.init(keySpec);
            byte[] hash = hmacSha256.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Hex.encodeHexString(hash);
        } catch (Exception e) {
            throw new WebhookException("Failed to generate HMAC signature", e);
        }
    }

    private boolean isValidTimestamp(String timestampHeader) {
        if (!StringUtils.isNumeric(timestampHeader)) {
            return false;
        }

        long timestamp = Long.parseLong(timestampHeader);
        long currentTime = System.currentTimeMillis() / 1000L;

        return Math.abs(currentTime - timestamp) <= maxAge;
    }
}