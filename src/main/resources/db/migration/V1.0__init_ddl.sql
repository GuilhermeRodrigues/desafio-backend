CREATE TABLE customer
(
    id          uuid PRIMARY KEY NOT NULL,
    name        varchar          NOT NULL,
    email       varchar          NOT NULL,
    created_at  timestamp        NOT NULL DEFAULT now(),
    updated_at  timestamp        NULL,
    deleted_at  timestamp        NULL
);

CREATE UNIQUE INDEX customer_id_unq ON customer (id);
CREATE UNIQUE INDEX customer_email_unq ON customer (email);
CREATE INDEX customer_id_idx ON customer (id);

CREATE TABLE favorites_list
(
    id          uuid PRIMARY KEY NOT NULL,
    customer_id uuid             NOT NULL,
    CONSTRAINT favorites_list_customer_fkey FOREIGN KEY (customer_id) REFERENCES customer (id)
);

CREATE UNIQUE INDEX favorites_list_id_unq ON favorites_list (id);
CREATE UNIQUE INDEX favorites_list_customer_id_unq ON favorites_list (customer_id);
CREATE INDEX favorites_list_id_idx ON favorites_list (id);

CREATE TABLE favorite
(
    id                  uuid PRIMARY KEY NOT NULL,
    favorites_list_id   uuid             NOT NULL,
    product             jsonb            NOT NULL,
    created_at          timestamp        NOT NULL DEFAULT now(),
    deleted_at          timestamp        NULL,
    CONSTRAINT favorite_favorites_list_fkey FOREIGN KEY (favorites_list_id) REFERENCES favorites_list (id)
);

CREATE UNIQUE INDEX favorite_id_unq ON favorite (id);
CREATE INDEX favorites_id_idx ON favorite (id);