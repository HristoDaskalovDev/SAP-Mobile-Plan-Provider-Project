CREATE TABLE IF NOT EXISTS general_plan
(
  id         BIGSERIAL PRIMARY KEY,
  name       VARCHAR(128)             NOT NULL,
  minutes    INT                      NOT NULL DEFAULT 0,
  traffic    INT                      NOT NULL DEFAULT 0,
  sms        INT                      NOT NULL DEFAULT 0,
  price      DOUBLE PRECISION         NOT NULL,
  created_ts TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS role
(
  id         BIGSERIAL PRIMARY KEY,
  name       VARCHAR(128)             NOT NULL,
  created_ts TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS "user"
(
  id          BIGSERIAL PRIMARY KEY,
  name        VARCHAR(128)                   NOT NULL,
  username    VARCHAR(128) UNIQUE            NOT NULL,
  password    VARCHAR(128)                   NOT NULL,
  national_id VARCHAR(16) UNIQUE             NOT NULL,
  address     VARCHAR(128)                   NOT NULL,
  role_id     BIGINT                         NOT NULL REFERENCES role (id),
  created_ts  TIMESTAMP WITH TIME ZONE       NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS phone_plan
(
  id                BIGSERIAL PRIMARY KEY,
  phone_num         VARCHAR(16) UNIQUE       NOT NULL,
  remaining_minutes INT                      NOT NULL DEFAULT 0,
  remaining_traffic INT                      NOT NULL DEFAULT 0,
  remaining_sms     INT                      NOT NULL DEFAULT 0,
  paid              BOOLEAN                  NOT NULL DEFAULT FALSE,
  active            BOOLEAN                  NOT NULL DEFAULT TRUE,
  user_id           BIGINT                   NOT NULL REFERENCES "user" (id),
  general_plan_id   BIGINT                   NOT NULL REFERENCES general_plan (id),
  created_ts        TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);