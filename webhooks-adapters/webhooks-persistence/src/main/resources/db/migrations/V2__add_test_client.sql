INSERT INTO webhook_client (id, name, header_key, secret_key, max_age, timestamp_header_key, signature_header_key, separator)
VALUES (
    '123e4567-e89b-12d3-a456-426614174000',
    'Test Client',
    'x-webhook-signature',
    'test_secret_key_123',
    31536000, -- 1 ano em segundos para testes
    't',
    'v1',
    ';'
); 