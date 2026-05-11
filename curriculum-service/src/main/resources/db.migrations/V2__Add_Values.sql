INSERT INTO area (name)
VALUES
('projects'),
('technology'),
('theory history and investigation'),
('urban environmental'),
('university extension');

INSERT INTO career (
    key,
    name,
    created_at,
    updated_at,
    title_awarded,
    modality,
    semester_duration,
    total_career_credits,
    total_obligatory_credits,
    total_elective_credits,
    career_director_id
) VALUES
(
    'architecture',
    'Architecture',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    'Bachelor of Architecture',
    'On-site',
    '10',
    400,
    360,
    40,
    1
),
(
    'landscape-architecture',
    'Landscape Architecture',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    'Bachelor of Landscape Architecture',
    'On-site',
    '8',
    350,
    310,
    40,
    2
),
(
    'industrial-design',
    'Industrial Design',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    'Bachelor of Industrial Design',
    'On-site',
    '8',
    360,
    320,
    40,
    3
),
(
    'urban-planning',
    'Urban Planning',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    'Bachelor of Urban Planning',
    'On-site',
    '8',
    380,
    340,
    40,
    4
);


INSERT INTO professional_line (
    name,
    created_at,
    updated_at,
    area_id
) VALUES
(
    'Criticism and Reflection',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    3
),
(
    'Culture and Heritage Conservation',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    3
),
(
    'Habitat Design and Environment',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    4
),
(
    'Structural and Construction Technologies',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    4
),
(
    'Architectural Expressiveness',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    1
),
(
    'Project Management',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    2
),
(
    'Habitat Production Management',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    4
),
(
    'Project Process',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    1
);

INSERT INTO obligatory_subject(
    name,
    serial_number,
    key,
    credits,
    area_id,
    career_id,
) VALUES (
    'architecture workshop'
    'I',
    '1101',
    8,
    1,
    1
),
(
 'architectural expression'
    'I',
    '1102',
    8,
    1,
    1
),
(
 'architectural theory'
    'I',
    '1103',
    8,
    3,
    1
),
(
 'geometry'
    'I',
    '1104',
    8,
    2,
    1
),
(
 'mathematics'
    '1105',
    8,
    2,
    1
),
(
 'architecture history',
    'I'
    '1106',
    8,
    3,
    1
),
(
    'urban environmental systems',
    'I'
    '1106',
    8,
    3,
    1
),
(
    'architecture workshop'
    'II',
    '1201',
    8,
    1,
    1
),
(
    'architectural expression'
    'II',
    '1202',
    8,
    1,
    1
),
(
    'architectural theory'
    'II',
    '1203',
    8,
    3,
    1
),
(
    'geometry'
    'II',
    '1204',
    8,
    2,
    1
),
(
    'basic structural systems',
    'I'
    '1205',
    8,
    2,
    1
),
(
    'architecture history'
    'II',
    '1206',
    8,
    3,
    1
),
(
    'architecture workshop'
    'III',
    '1301',
    8,
    1,
    1
),
(
    'architectural expression'
    'III',
    '1302',
    8,
    1,
    1
),
(
    'architectural theory'
    'III',
    '1303',
    8,
    3,
    1
),
(
    'geometry'
    'III',
    '1304',
    8,
    2,
    1
),
(
    'basic structural systems',
    'II'
    '1305',
    8,
    2,
    1
),
(
    'architecture history'
    'III',
    '1306',
    8,
    3,
    1
),
(
    'university extension'
    'I',
    '1307',
    8,
    4,
    1
),
(
    'urban environmental systems',
    'II'
    '1308',
    8,
    3,
    1
),
(
    'architecture workshop'
    'IV',
    '1401',
    8,
    1,
    1
),
(
    'architectural expression'
    'IV',
    '1402',
    8,
    1,
    1
),
(
    'architectural theory'
    'IV',
    '1403',
    8,
    3,
    1
),
(
    'installation systems'
    'I',
    '1404',
    8,
    2,
    1
),
(
    'basic structural systems',
    'III'
    '1405',
    8,
    2,
    1
),
(
    'architecture history'
    'IV',
    '1406',
    8,
    3,
    1
),
(
    'university extension'
    'II',
    '1407',
    8,
    4,
    1
),
(
    'urban environmental systems',
    'III'
    '1408',
    8,
    3,
    1
),
(
    'architecture workshop'
    'V',
    '1501',
    8,
    1,
    1
),
(
    'architectural expression'
    'V',
    '1502',
    8,
    1,
    1
),
(
    'architectural theory'
    'V',
    '1503',
    8,
    3,
    1
),
(
    'installation systems'
    'II',
    '1504',
    8,
    2,
    1
),
(
    'structural systems',
    'I'
    '1505',
    8,
    2,
    1
),
(
    'architecture history'
    'V',
    '1506',
    8,
    3,
    1
),
(
    'university extension'
    'III',
    '1508',
    8,
    3,
    1
),
(
    'urban environmental systems',
    'IV'
    '1508',
    8,
    3,
    1
),
(
    'architecture workshop'
    'VI',
    '1601',
    8,
    1,
    1
),
(
    'installation systems'
    'III',
    '1602',
    8,
    2,
    1
),
(
    'structural systems',
    'II'
    '1603',
    8,
    2,
    1
),
(
    'administration in architecture'
    'I',
    '1604',
    8,
    3,
    1
),
(
    'university extension'
    'IV',
    '1605',
    8,
    3,
    1
),
(
    'architecture workshop'
    'VI',
    '1601',
    8,
    1,
    1
),
(
    'installation systems'
    'III',
    '1602',
    8,
    2,
    1
),
(
    'structural systems',
    'II'
    '1603',
    8,
    2,
    1
),
(
    'university extension'
    'IV',
    '1607',
    8,
    3,
    1
),
(
    'architecture workshop'
    'VI',
    '1601',
    8,
    1,
    1
),
(
    'installation systems'
    'III',
    '1602',
    8,
    2,
    1
),
(
    'structural systems',
    'II'
    '1603',
    8,
    2,
    1
),
(
    'university extension'
    'IV',
    '1507',
    8,
    3,
    1
),
(
    'architecture workshop'
    'VII',
    '1701',
    8,
    1,
    1
),
(
    'structural systems',
    'III'
    '1702',
    8,
    2,
    1
),
(
    'university extension'
    'V',
    '1703',
    8,
    3,
    1
),
(
    'architecture workshop'
    'VIII',
    '1801',
    8,
    1,
    1
),
(
    'graduation seminar'
    'I',
    '1901',
    12,
    1,
    1
),
(
    'graduation seminar'
    'II',
    '1902',
    12,
    1,
    1
);






