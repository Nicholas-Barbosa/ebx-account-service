# Banking Transactions Service

REST API built with Java and Spring Boot for processing banking transactions, including deposits, withdrawals, and transfers between accounts.

This project was developed as part of a technical assessment and demonstrates clean code practices, layered architecture, concurrency handling, automated testing, and the use of design patterns to keep the solution extensible and maintainable.

---

## Features

- Balance inquiry
- Deposit operation
- Withdrawal operation
- Transfer operation
- Automatic account creation through deposits
- Validation of non-existent accounts
- Validation of insufficient funds
- Thread-safe transaction processing
- Automated tests

---

## Technologies

- Java 21
- Spring Boot
- Maven
- JUnit 5

---

## Architecture

The application follows a simple layered architecture with clear separation of responsibilities.

```text
src/main/java
├── controller
├── service
├── repository
├── service/strategy
├── dto
├── model
├── exception
```

### Layers

#### Controller

Responsible for exposing REST endpoints and handling HTTP requests and responses.

#### Service

Contains the application's business logic and coordinates transaction execution.

#### Repository

Provides access to account storage.

#### Strategy

Implements the Strategy Pattern, where each transaction type has its own dedicated implementation.

#### DTO

Contains request and response objects used by the API.

#### Model

Represents the application's domain entities.

#### Exception

Contains custom exceptions and error handling logic.

#### Config

Contains application configuration and bean registration.

---

## Strategy Pattern

Transaction processing is implemented using the Strategy Pattern.

Each event type is handled by a dedicated strategy implementation:

- `TransferProcessor`
- `WithdrawProcessor`
- `DepositProcessor`

The appropriate strategy is selected dynamically based on the event type received in the request.

### Benefits

- Low coupling
- High cohesion
- Easier maintenance
- Improved extensibility
- Compliance with the Open/Closed Principle (OCP)

---

## Concurrency and Thread Safety

Account data is stored in memory using a `ConcurrentHashMap`.

To ensure consistency in concurrent scenarios:

- Deposits are performed atomically.
- Withdrawals are protected against race conditions.
- Transfers use locking mechanisms to guarantee atomic debit and credit operations.
- Account balances remain consistent under concurrent access.

This approach allows multiple requests to be processed safely without compromising data integrity.

---

## API

### Get Balance

#### Request

```http
GET /balance?account_id=100
```

#### Response

```json
100
```

#### Non-existent Account

```http
404 Not Found
```

```json
0
```

---

### Process Event

#### Request

```http
POST /event
```

---

### Deposit

#### Request

```json
{
  "type": "deposit",
  "destination": "100",
  "amount": 10
}
```

#### Response

```json
{
  "destination": {
    "id": "100",
    "balance": 10
  }
}
```

---

### Withdrawal

#### Request

```json
{
  "type": "withdraw",
  "origin": "100",
  "amount": 5
}
```

#### Response

```json
{
  "origin": {
    "id": "100",
    "balance": 5
  }
}
```

---

### Transfer

#### Request

```json
{
  "type": "transfer",
  "origin": "100",
  "destination": "200",
  "amount": 15
}
```

#### Response

```json
{
  "origin": {
    "id": "100",
    "balance": 85
  },
  "destination": {
    "id": "200",
    "balance": 15
  }
}
```

---

## Running the Application

Clone the repository:

```bash
git clone <repository-url>
```

Navigate to the project directory:

Start the application:

```bash
./mvnw spring-boot:run
```

or

```bash
mvn spring-boot:run
```

The application will be available at:

```text
http://localhost:8080
```

---

## Running Tests

Execute all tests:

```bash
mvn test
```

---

## Design Decisions

### In-Memory Storage

The application uses an in-memory repository backed by `ConcurrentHashMap`, which provides efficient and thread-safe access to account data.

### Concurrency Control

Locks are used where necessary to ensure transaction atomicity and prevent race conditions during withdrawals and transfers.

### Strategy Pattern

Business rules for each transaction type are encapsulated in separate strategy implementations, making the solution easier to extend and maintain.

### Layered Architecture

Responsibilities are clearly separated across layers, improving readability, maintainability, and testability.

### Automated Testing

Unit tests cover both successful and failure scenarios, ensuring correctness of the business rules.

---

## Supported Operations

| Operation | Description |
|------------|------------|
| Deposit | Adds funds to an account |
| Withdrawal | Removes funds from an account |
| Transfer | Moves funds between accounts |
| Balance Inquiry | Returns the current account balance |

---

## Author

Developed as a technical assessment project using Java and Spring Boot by Nicholas Barbosa.