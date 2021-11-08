Expected

1. Create Address DAO model

Fields:

    id (auto-generated)
    associated profile id
    profile picture
    last updated timestamp

Notes:

    Include Hibernate annotations for this DAO model.

    Use Lombok to keep the app consistent.

2. Create Address DTO model

Fields:

    id (auto-generated)
    associated profile id
    profile picture
    last updated timestamp

3. Create mappings code in ResourceMapper to convert between profile image DAO and DTO models

4. Create Hibernate mappings between the User and the profile image as a 1-to-1 relationship e.g. 1 "User" can have only one profile picture

Changes:

    Add "profile image" as an optional field to the User model

Notes:

    Delete to User should cascade to Addresses

    Bi-directional mapping is not required

5. Update h2 database seed file to create the profile image table on app start

6. Update h2 database seed file to populate the profile image table on app start

    at least 50% of users should have at a profile image

Not Expected

1. Do not create the CRUD APIs **
