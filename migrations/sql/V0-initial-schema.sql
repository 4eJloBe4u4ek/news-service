CREATE TABLE news
(
    id     BIGSERIAL PRIMARY KEY,
    time   TIMESTAMP NOT NULL,
    title  TEXT      NOT NULL,
    text   TEXT      NOT NULL,
    author TEXT      NOT NULL
);
