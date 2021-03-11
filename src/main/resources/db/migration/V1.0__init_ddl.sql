CREATE TABLE customer
(
    id          uuid PRIMARY KEY NOT NULL,
    name        varchar          NOT NULL,
    email       varchar          NOT NULL,
    created_at  timestamp        NOT NULL DEFAULT now(),
    updated_at  timestamp        NULL
);

CREATE UNIQUE INDEX customer_id_unq ON customer (id);
CREATE UNIQUE INDEX customer_email_unq ON customer (email);
CREATE INDEX customer_id_idx ON customer (id);