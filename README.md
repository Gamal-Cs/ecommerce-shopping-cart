# eCommerce Shopping Cart with JWT Authentication

This is an eCommerce shopping cart application built using Spring Boot and MySQL. It allows users to manage their shopping cart, add items to it, and place orders. The application also supports category management, image uploading, and JWT-based authentication for securing the endpoints.

## Features

- **JWT Authentication**: Secure user login and access control using JWT (JSON Web Token).
- **Product Management**: Admins can manage products, including uploading images.
- **Cart Management**: Users can add products to their cart, adjust quantities, and view the cart's total.
- **Category Management**: Admins can create categories to organize products.
- **Order Management**: Users can place orders, which contain a list of products, and track order status.
- **Role-Based Access**: Users and admins have different access rights to perform specific actions within the application.

## Technologies Used

- **Backend**: Spring Boot
- **Database**: MySQL
- **Authentication**: JWT (JSON Web Token)
- **Frontend**: RESTful API (No front-end, purely backend service)
- **Image Handling**: Support for image upload and download, associated with products

## Entities

1. **Cart**: Represents a shopping cart for each user.
2. **CartItem**: Represents an individual item in a user's cart.
3. **Product**: Represents products available for purchase.
4. **User**: Represents a customer or user of the system.
5. **Role**: Manages user roles for access control.
6. **Order**: Represents an order placed by a user.
7. **OrderItem**: Represents products within an order.
8. **Category**: Represents product categories for better organization.
9. **Image**: Handles product images, allowing for upload and download.

## Setup

### Prerequisites

- **Java 23.0.1** or higher
- **MySQL** or other compatible relational database
- **Spring Boot** environment set up with Maven or Gradle
- **Postman** for testing API endpoints

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/yourusername/ecommerce-shopping-cart.git
   cd ecommerce-shopping-cart
