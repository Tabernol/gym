# Gym CRM System

This project is a Spring-based module designed to handle a gym CRM (Customer Relationship Management) system. It leverages core Spring features such as Inversion of Control (IoC), Dependency Injection (DI), Aspect-Oriented Programming (AOP), and more. This project serves as a practical learning experience for mastering these concepts.

## Table of Contents

- [Project Overview](#project-overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Setup Instructions](#setup-instructions)
- [Usage](#usage)
- [Unit Testing](#unit-testing)
- [Logging](#logging)
- [Future Improvements](#future-improvements)

## Project Overview

The gym CRM system provides the ability to manage profiles for trainees and trainers, as well as training sessions. The project includes:

1. **Service Classes**:
    - **TraineeService**: Handles creation, updating, deletion, and selection of trainee profiles.
    - **TrainerService**: Manages creation, updating, and selection of trainer profiles.
    - **TrainingService**: Responsible for creating and selecting training profiles.

2. **Data Access Objects (DAOs)**: Each domain model entity (Trainer, Trainee, Training) is handled by a separate DAO that interacts with an in-memory storage (Java Map).

3. **Storage Initialization**: The system initializes storage with predefined data from a file at startup, using Spring's bean post-processing features.

4. **Dependency Injection**: Services are injected into the facade via constructor-based injection, while other dependencies are injected using setter-based methods.

## Features

- **Profile Management**: Create, update, delete, and select trainee and trainer profiles.
- **Training Management**: Create and select training profiles.
- **Username and Password Generation**:
    - Username is generated from the first and last names concatenated with a dot (e.g., `John.Smith`).
    - If a username already exists, a serial number is appended to ensure uniqueness.
    - Passwords are randomly generated as 10-character strings.
- **Aspect-Oriented Programming**: AOP is used for logging and monitoring the persistence layer operations.

## Technologies Used

- **Spring Framework**: For dependency injection and AOP.
- **AspectJ**: For aspect-oriented programming.
- **Lombok**: To reduce boilerplate code.
- **SLF4J**: For logging purposes.
- **JUnit & Mockito**: For unit testing.
- **Jackson**: For JSON processing.
- **Hutool**: For generating unique IDs.

## Setup Instructions

To set up the project locally, follow these steps:

1. Clone the repository:

   ```bash
   git clone https://github.com/Tabernol/gym/tree/dev-spring-core
   cd gym-crm-system
