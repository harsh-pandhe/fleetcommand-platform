CREATE TABLE equipment (
    id UUID PRIMARY KEY,
    owner_id UUID NOT NULL REFERENCES owner_profiles(user_id),
    type VARCHAR(100) NOT NULL,
    rate NUMERIC(12, 2) NOT NULL,
    location VARCHAR(255) NOT NULL,
    photos_reference TEXT
);

CREATE TABLE equipment_availability (
    id UUID PRIMARY KEY,
    equipment_id UUID NOT NULL REFERENCES equipment(id),
    available_from TIMESTAMP WITH TIME ZONE NOT NULL,
    available_to TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX idx_equipment_owner ON equipment(owner_id);
CREATE INDEX idx_equipment_availability_window ON equipment_availability(equipment_id, available_from, available_to);
