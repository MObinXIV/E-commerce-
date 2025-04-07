CREATE TABLE app_user (
                          id UUID PRIMARY KEY,
                          first_name TEXT NOT NULL,
                          last_name TEXT NOT NULL,
                          email TEXT NOT NULL,
                          password TEXT NOT NULL,
                          created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP WITHOUT TIME ZONE,
                          account_locked BOOLEAN NOT NULL DEFAULT FALSE,
                          account_enabled BOOLEAN NOT NULL DEFAULT FALSE,
                          phone_number VARCHAR(15) NOT NULL,
                          CONSTRAINT user_unique_email UNIQUE (email),
                          CONSTRAINT user_unique_phone_number UNIQUE (phone_number)
);

CREATE TABLE role (
                      id UUID PRIMARY KEY ,
                      name VARCHAR(50) NOT NULL,
                      CONSTRAINT role_unique_name UNIQUE (name)
);

CREATE TABLE user_role (
                           user_id UUID NOT NULL,
                           role_id UUID NOT NULL,
                           created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           PRIMARY KEY (user_id, role_id),
                           CONSTRAINT userRole_user_id_fk FOREIGN KEY (user_id) REFERENCES app_user(id) ON DELETE CASCADE,
                           CONSTRAINT userRole_role_id_fk FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE
);

CREATE TABLE product (
                         id UUID PRIMARY KEY ,
                         product_name TEXT NOT NULL,
                         product_description TEXT NOT NULL,
                         price DECIMAL(10,2) NOT NULL,
                         stock INTEGER NOT NULL,
                         created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP WITHOUT TIME ZONE,
                         user_id UUID NOT NULL,
                         CONSTRAINT user_product_fk FOREIGN KEY (user_id) REFERENCES app_user(id) ON DELETE CASCADE
);

CREATE TABLE orders (
                        id UUID PRIMARY KEY ,
                        status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
                        payment_method VARCHAR(50) NOT NULL,
                        total_price DECIMAL(10,2) NOT NULL DEFAULT 0.00,
                        created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP WITHOUT TIME ZONE,
                        shipping_fee FLOAT,
                        phone_number TEXT NOT NULL,
                        shipping_address TEXT,
                        is_locked BOOLEAN NOT NULL DEFAULT FALSE,
                        user_id UUID NOT NULL,
                        CONSTRAINT user_order_fk FOREIGN KEY (user_id) REFERENCES app_user(id) ON DELETE CASCADE
);

CREATE TABLE order_product (
                               order_id UUID NOT NULL,
                               product_id UUID NOT NULL,
                               created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               updated_at TIMESTAMP WITHOUT TIME ZONE,
                               total_price DECIMAL(10,2) NOT NULL,
                               quantity INTEGER,
                               PRIMARY KEY (order_id, product_id),
                               CONSTRAINT orderProduct_order_id_fk FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
                               CONSTRAINT orderProduct_product_id_fk FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
);