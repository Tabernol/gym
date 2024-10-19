
# Gym CRM System

This project is a Hibernate-based module designed to manage profiles for trainees and trainers, as well as training sessions within a gym CRM system. The system uses Liquibase for database management and features manual transaction and validation configurations. This project focuses on mastering Hibernate ORM, transaction management, and unit testing.

## Table of Contents

- [Project Overview](#project-overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Database Management](#database-management)
- [Setup Instructions](#setup-instructions)

## Project Overview

The Gym CRM system allows for the management of trainee and trainer profiles, as well as training sessions. It ensures proper username and password matching, profile activation/deactivation, and management of training assignments.

### Key Services

- **TrainerService**: Handles creating, updating, selecting, and managing trainer profiles.
- **TraineeService**: Manages creation, update, selection, and deletion of trainee profiles.
- **TrainingService**: Responsible for adding and retrieving training sessions based on criteria.

All services ensure imitate authentication, except creating new profiles.

### Main Concepts Implemented

- **One-to-One Relationship**: Users table has a parent-child relationship with both Trainee and Trainer tables.
- **Many-to-Many Relationship**: Trainees and trainers can be linked to multiple profiles.
- **Transactional Operations**: Important actions are executed in transactions to ensure data integrity.
- **Cascade Deletion**: Deleting a trainee profile results in the deletion of associated training sessions.

## Features

1. **Profile Management**
    - Create and update Trainer profiles, and delete Trainee profiles.
    - Password change, profile activation, and deactivation.
    - Hard delete operations with cascading deletion for associated trainings.

2. **Authentication and Security**
    - Username and password generation for both trainers and trainees.
    - Trainee and Trainer authentication based on username and password matching.

3. **Training Management**
    - Add new training sessions and retrieve them by criteria (date range, trainee/trainer name, type).
    - Manage trainees’ trainers list.

4. **Training Types Management**
    - Fixed list of training types (one-to-many relationship with trainings).

## Technologies Used

- **Spring Framework**: Core dependency injection and transaction management.
- **Hibernate**: ORM for managing the database and entity relations.
- **Liquibase**: Database migration and version control.
- **PostgreSQL**: Database.
- **JUnit & Mockito**: Unit testing.
- **Jackson**: For JSON processing.

## Database Management

- **Liquibase**: Used for managing database changes and applying migrations.
- **Hibernate Configuration**: Hibernate is manually configured to work with the PostgreSQL and transaction in declarative style.

### Validation and Integrity

- Validation checks ensure that required fields are populated before creating or updating profiles. Also uses custom validation for date.
- Authentication checks are required for most actions except for creating profiles.

## Setup Instructions

To set up the project locally, follow these steps:

1. Clone the repository:

   ```bash
   git clone https://github.com/Tabernol/gym
   cd gym
   ```

2. Install dependencies and configure the database using Liquibase.

3. Run the project using your preferred IDE.

4. Execute unit tests:

   ```bash
   ./gradlew test
   ```

