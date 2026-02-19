-- =========================
-- INSERT CATEGORIES
-- =========================
INSERT INTO category (id, name) VALUES (1, 'FRAMEWORK');
INSERT INTO category (id, name) VALUES (2, 'DATABASE');
INSERT INTO category (id, name) VALUES (3, 'SECURITY');
INSERT INTO category (id, name) VALUES (4, 'TESTING');
INSERT INTO category (id, name) VALUES (5, 'CLOUD');


-- =========================
-- INSERT PACKAGES
-- =========================
INSERT INTO library (id, group_id, artifac_id, version)
VALUES (1, 'org.springframework', 'spring-boot-starter-web', '3.2.0');

INSERT INTO library (id, group_id, artifac_id, version)
VALUES (2, 'org.hibernate', 'hibernate-core', '6.4.0');

INSERT INTO library (id, group_id, artifac_id, version)
VALUES (3, 'org.junit.jupiter', 'junit-jupiter', '5.10.1');

INSERT INTO library (id, group_id, artifac_id, version)
VALUES (4, 'org.keycloak', 'keycloak-spring-boot', '22.0.0');

INSERT INTO library (id, group_id, artifac_id, version)
VALUES (5, 'com.amazonaws', 'aws-java-sdk-s3', '1.12.600');


-- =========================
-- INSERT MANY TO MANY RELATION
-- TABLE: PACKAGE_CATEGORY
-- =========================

-- Spring Boot Web → Framework
INSERT INTO library_category (library_id, category_id) VALUES (1, 1);

-- Hibernate → Framework, Database
INSERT INTO library_category (library_id, category_id) VALUES (2, 1);
INSERT INTO library_category (library_id, category_id) VALUES (2, 2);

-- JUnit → Testing
INSERT INTO library_category (library_id, category_id) VALUES (3, 4);

-- Keycloak → Security
INSERT INTO library_category (library_id, category_id) VALUES (4, 3);

-- AWS SDK → Cloud
INSERT INTO library_category (library_id, category_id) VALUES (5, 5);
