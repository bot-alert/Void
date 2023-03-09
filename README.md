# Void

Void is a Spring Boot application that implements public and private key based JWT authentication. This application was created as a learning project to explore JWT authentication and microservices.

## How it works

Void uses a model class called User to generate JWTs based on public and private key cryptography. When a user logs in, their credentials are verified and a JWT is generated using their user ID and role. The JWT is then signed using the private key and sent back to the client.

When a client makes a request to a protected endpoint, they must include the JWT in the Authorization header. The server verifies the JWT signature using the public key and grants access to the protected resource if the signature is valid.

By using public and private key cryptography to sign and verify JWTs, Void provides a more secure authentication mechanism compared to plain JWT authentication. It also makes it easier to implement JWT authentication in a microservices architecture, where multiple services may need to verify the same JWT.

Void uses MySQL as its database instead of H2. This allows for persistent data storage and the ability to handle larger data sets.

## Technologies used

- Spring Boot: A popular framework for building Java-based web applications.
- JWT: JSON Web Tokens are a compact, URL-safe means of representing claims to be transferred between two parties.
- Maven: A build automation tool used primarily for Java projects.
- MySQL: An open-source relational database management system.
- Hibernate: An object-relational mapping tool for Java.
- Flyway: A database migration tool that simplifies the process of managing database schema changes.

## Getting started

To get started with Void, follow these steps:

1. Clone the repository: `git clone https://github.com/your_username/void.git`
2. Install the necessary dependencies: `mvn clean install`
3. Start the server: `mvn spring-boot:run`

## Contributing

Contributions are welcome! If you find a bug or have an idea for a new feature, feel free to open an issue or submit a pull request.

## License

Void is licensed under the [MIT License](./LICENSE). Feel free to use, modify, and distribute the code however you like.
