CREATE TABLE quote (
                        id BIGSERIAL PRIMARY KEY,
                        isin VARCHAR(12) NOT NULL,
                        bid NUMERIC,
                        ask NUMERIC NOT NULL,
                        version BIGINT NOT NULL,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE elvl (
                      id BIGSERIAL PRIMARY KEY,
                      isin VARCHAR(12) NOT NULL UNIQUE,
                      value NUMERIC NOT NULL,
                      version BIGINT NOT NULL,
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);