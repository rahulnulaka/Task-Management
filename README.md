# Task Management API

## Description

Task Management API is a RESTful service built with Java Spring Boot, utilizing PostgreSQL as the database. It facilitates CRUD operations (Create, Read, Update, Delete) on tasks. Each task includes attributes such as title, description, due date, and status.

## Features Added

### 1. User Authentication

- User authentication is implemented to ensure only authorized users can access and modify their tasks.
- Admin users have full access to all tasks.

### 2. Task Operations

#### Add Task

- Establish an API endpoint for adding new tasks.

#### Retrieve Tasks

- Create an API endpoint to retrieve a list of tasks for a specific user.
- Admin users can access all tasks.

#### Update and Delete Tasks

- Implemented features to update task details.
- Mark tasks as completed or delete them.

### 3. Database

- MySQL is used as the database to store task data.


### 5. Additional Features

#### Pagination

- Integrate pagination into the task list API endpoint.

#### Sorting and Filtering

- Include sorting and filtering options for the task list.

#### Error Handling

- Ensured proper error handling and use appropriate status codes in API responses.

#### Unit Testing

- Developed unit tests for critical components of  application.
