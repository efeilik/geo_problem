# Problem Management System with RabbitMQ Integration

## Overview
This project implements a problem management system where problems can be claimed by users, with integration to RabbitMQ for message queueing. The system allows users to get mails asynchronously using RabbitMQ. When a problem is added, only users located near the problem's location will receive mail.

## Features
- **Problem Management**: Users can claim problems using a unique code.
- **Session-Based Authentication**: Secure API endpoints with session-based authentication.
- **Message Queueing with RabbitMQ**: When a problem is created, users within a defined radius of the problem's location are notified via RabbitMQ.
- **Database**: MySQL is used for storing user and problem information.

## Technologies Used
- **Spring Boot**: Framework for building the backend.
- **RabbitMQ**: Message broker for handling asynchronous communication.
- **MySQL**: Relational database for storing user and problem data.
- **Session-Based Authentication**: For securing endpoints and user authentication.

## Tools Required

- **Java JDK 19**: The application requires JDK 19. You can download it from [here](https://www.oracle.com/java/technologies/javase/jdk19-archive-downloads.html).
- **Gradle**: Gradle is used as the build tool. You can install it from [here](https://gradle.org/install/).
- **Docker**: Docker is required to run the application in containers. Download Docker from [here](https://www.docker.com/products/docker-desktop).

## Tools Suggested

- **Postman**: Postman is suggested for testing the API endpoints. You can download it from [here](https://www.postman.com/downloads/).

## Getting Started

This section will guide you through setting up and running the **Problem Management System** on your local machine. The steps include cloning the repository, building the Docker containers, and running the application.

### 1. Clone the Repository

To begin, clone the project repository from GitHub and navigate to directory

```console
git clone https://github.com/efeilik/geo_problem.git
cd geo_problem
```

### 2. Build and Run with Docker
Run the following command to build and start all the services:

```console
gradlew build
docker-compose up
```

### 3. Access the Application

After running the Docker containers, you can access the services as follows:

- MySQL: The MySQL database will be available at jdbc:mysql://localhost:3306/problem_management. You can interact with it using any MySQL client.
- RabbitMQ: The RabbitMQ management UI can be accessed at http://localhost:15672/. Login with the default username guest and password guest.
- Spring Boot Application: The backend application will be accessible at http://localhost:8080/. This is where the API endpoints will be exposed.

### 4. Stopping the Services
When you're done, you can stop the running containers with the following command:

```console
docker-compose down
```

## API End Points

## 1. Login

**URL**: `/login`

**Method**: `POST`

**Description**: Authenticates a user and returns a session token for subsequent requests.

### Request Parameters:
- **Body** (application/x-www-form-urlencoded):
    - `username`: _string_ (Required) - The user's email address.(admin)
    - `password`: _string_ (Required) - The user's password. (admin)

### Request Example:

```http
POST /login
Content-Type: application/x-www-form-urlencoded
username=admin&password=admin
```

# 2. Admin - Create a User

## URL
`/admin/users`

## Method
`POST`

## Description
Allows the admin to create a new user in the system.

### Request Body
The request body should be in JSON format with the following fields:

| Field      | Type   | Description                                                                           | Validation                                           |
|------------|--------|---------------------------------------------------------------------------------------|------------------------------------------------------|
| `name`     | string | The name of the user.                                                                 | Cannot be null or empty (`@NotBlank`)                |
| `email`    | string | The email address of the user.                                                        | Must be a valid email format (`@Email`, `@NotBlank`) |
| `password` | string | The password for the user.                                                            | Cannot be null or empty (`@NotBlank`)                |
| `address`  | string | The address of the user. Format: Street Address, District, City, Postal Code, Country | Cannot be null or empty (`@NotBlank`)                |
| `role`     | string | The role of the user. Possible values: `ROLE_USER`, `ROLE_ADMIN`.                     | Optional field. Default: `ROLE_USER`                 |

### Request Example
```http
POST /admin/users
Content-Type: application/json
{
  "name": "John Doe",
  "email": "johndoe@example.com",
  "password": "123",
  "address": "İstiklal Caddesi No:123, Beyoğlu, İstanbul, 34433, Türkiye",
  "role": "ROLE_USER"
}
```
### Authentication
This endpoint requires session-based authentication.

# 3. Admin - Get All Users

## URL
`/admin/users`

## Method
`GET`

## Description
Allows the admin to view all users in the system.


### Request Example
```http
GET /admin/users
```
### Authentication
This endpoint requires session-based authentication.

# 4. Admin - Create a Problem

## URL
`/admin/problems`

## Method
`POST`

## Description
Allows the admin to create a new problem in the system. A mail containing a unique code will be sent to users 5 km away from the problem.

## Request Parameters

### Request Body
The request body should be in JSON format with the following fields:

| Field         | Type   | Description                             |
|---------------|--------|-----------------------------------------|
| `description` | string | A detailed description of the problem.  |
| `address`     | string | The address where the problem occurred. |

### Request Example
```http
POST /admin/problems
Content-Type: application/json
{
  "description": "Su klor dengesi bozuk.",
  "address": "Sincan/Ankara"
}
```

### Authentication
This endpoint requires session-based authentication.

# 5. Admin - Get All Problems

## URL
`/admin/problems`

## Method
`GET`

## Description
Allows the admin to view all problems in the system.

### Request Example
```http
GET /admin/problems
```

### Authentication
This endpoint requires session-based authentication.

# 6. Claim a Problem by Unique Code

## URL
`/problems/{uniqueCode}`

## Method
`POST`

## Description
Allows a user to claim a problem using a unique code.

## Request Parameters

### Path Parameter
| Parameter    | Type   | Description                               |
|--------------|--------|-------------------------------------------|
| `uniqueCode` | string | The unique code for the problem to be claimed. |

### Request Example
```http
POST /problems/54736770-7120-485c-ace1-d1d64c3e326b
```
### Authentication
This endpoint requires session-based authentication.

# 7. Get My Problems (Problems Claimed by User)

## URL
`/problems/myProblems`

## Method
`GET`

## Description
Allows a user to retrieve all the problems they have claimed.

### Request Example
```http
GET /problems/myProblems
```
### Authentication
This endpoint requires session-based authentication.



