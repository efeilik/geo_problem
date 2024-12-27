# Problem Management System with RabbitMQ Integration

## Overview
This project implements a problem management system where problems can be claimed by users, with integration to RabbitMQ for message queueing. The system allows users to claim problems and processes these actions asynchronously using RabbitMQ. When a problem is added, only users located near the problem's location will receive notifications.

## Features
- **Problem Management**: Users can claim problems using a unique code.
- **JWT Authentication**: Secure API endpoints with JWT-based authentication.
- **Message Queueing with RabbitMQ**: When a problem is created, users within a defined radius of the problem's location are notified via RabbitMQ.
- **Database**: MySQL is used for storing user and problem information.

## Technologies Used
- **Spring Boot**: Framework for building the backend.
- **RabbitMQ**: Message broker for handling asynchronous communication.
- **MySQL**: Relational database for storing user and problem data.
- **JWT**: JSON Web Tokens for securing endpoints and user authentication.