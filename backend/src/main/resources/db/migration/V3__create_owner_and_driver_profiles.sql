CREATE TABLE owner_profiles (
    user_id UUID PRIMARY KEY REFERENCES users(id),
    contact_name VARCHAR(100) NOT NULL,
    contact_phone VARCHAR(20) NOT NULL,
    verification_status VARCHAR(20) NOT NULL
);

CREATE TABLE driver_profiles (
    user_id UUID PRIMARY KEY REFERENCES users(id),
    full_name VARCHAR(100) NOT NULL,
    license_number VARCHAR(50) NOT NULL,
    status VARCHAR(20) NOT NULL
);
