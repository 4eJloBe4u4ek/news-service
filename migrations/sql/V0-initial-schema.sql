CREATE TABLE news
(
    id        BIGSERIAL PRIMARY KEY,
    time      TIMESTAMP NOT NULL,
    title     TEXT      NOT NULL,
    text      TEXT      NOT NULL,
    author_id BIGINT    NOT NULL
);
