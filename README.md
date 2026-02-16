# 🚀 Spring Boot 4 API Versioning Demo

Welcome to the **Spring Boot 4 API Versioning Masterclass**! This repository demonstrates the powerful capabilities of API versioning in modern Spring Boot applications. It covers comprehensive strategies to manage your REST API lifecycle, ensuring backward compatibility and a smooth evolution of your services.

> **Note:** This project is designed for the educational purpose of a YouTube tutorial, showcasing advanced versioning patterns and OpenAPI (Swagger) integration.

## 🌟 Features

This project implements the four industry-standard strategies for API versioning:

1.  **Path-Based Versioning** (URI Versioning)
    *   Explicit and easy to cache.
    *   **Endpoint**: `GET /api/v1/products` vs `GET /api/v2/products`
    *   *Implementation*: `PathBasedVersioning.java`

2.  **Query Parameter Versioning**
    *   Simple to implement and debug.
    *   **Endpoint**: `GET /query/products?version=1`
    *   *Implementation*: `QueryParameterVersioning.java`

3.  **Header Versioning** (Custom Request Header)
    *   Keeps URIs clean and semantically correct.
    *   **Header**: `X-API-Version: 1`
    *   *Implementation*: `HeaderVersioning.java`

4.  **Content Negotiation Versioning** (Media Type / Accept Header)
    *   The most RESTful approach, versioning the resource representation.
    *   **Header**: `Accept: application/vnd.company.app-v1+json`
    *   *Implementation*: `ContentNegotiationVersioning.java`

---

## 🛠️ Technology Stack

*   **Java 21**: Leveraging the latest LTS features.
*   **Spring Boot 4.x** (Preview/Concept): Utilizing next-gen framework capabilities.
*   **SpringDoc OpenAPI**: Automated Swagger documentation with robust version grouping.
*   **Lombok**: Reducing boilerplate code.
*   **Maven**: Dependency management.

---

## 🏃‍♂️ Getting Started

### Prerequisites
*   JDK 21 installed.
*   Maven installed.

### Installation

1.  **Clone the repository**
    ```bash
    git clone https://github.com/anbuzhobbiez/apiversion.git
    cd apiversion
    ```

2.  **Build the project**
    ```bash
    mvn clean install
    ```

3.  **Run the application**
    ```bash
    mvn spring-boot:run
    ```

### 📖 Accessing Swagger UI
Once the application is running, you can explore and test all versioning strategies through the interactive Swagger UI:

👉 **[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)**

*   Use the **"Select a definition"** dropdown in the top-right corner to switch between **`v1`** and **`v2`** API groups.
*   The system automatically filters endpoints and updates media types based on the selected version.

---

## 📚 Project Structure & Versioning Logic

### 1. Unified Configuration (`VersioningConfig.java`)
The project uses a unified configuration approach to register strategies.
```java
// Example configuration logic
configurer.useVersionResolver(new CustomApiVersionResolver())
          .addSupportedVersions("1.0", "2.0")
          .useRequestHeader("X-API-Version")
          .useQueryParam("version");
```

### 2. Custom Resolution Strategy (`CustomApiVersionResolver.java`)
We implement a smart `ApiVersionResolver` that intelligently determines the requested version by checking multiple sources in priority order:
1.  **Query Parameter**: `?version=...`
2.  **Custom Header**: `X-API-Version`
3.  **Accept Header**: Parsing `app-vX+json` MIME types.
4.  **Path Pattern**: Extracting `v1` or `v2` segments from the URL.

### 3. Advanced OpenAPI Grouping (`OpenApiConfig.java`)
To prevent documentation clutter, we separate V1 and V2 APIs into distinct groups using `GroupedOpenApi` and `OperationCustomizer`.
*   **V1 Group**: shows only endpoints compatible with version 1.
*   **V2 Group**: shows only endpoints compatible with version 2.
*   **Media Type rewriting**: Dynamically updates the Swagger "Media Type" dropdown to reflect custom types (e.g., `application/vnd.company.app-v2+json`) or versioned JSON (`application/json;version=2`).

---

## 🧪 Testing

The project comes with a comprehensive suite of Integration Tests using `MockMvc` to verify that each strategy routes correctly and returns the expected data structure (e.g., `price` field existence in V2).

Run the tests with:
```bash
mvn test
```

### Test Coverage:
*   **`PathBasedVersioningTest`**: Verifies `/api/v1` vs `/api/v2` routing.
*   **`QueryParameterVersioningTest`**: Verifies `?version=1` vs `?version=2`.
*   **`HeaderVersioningTest`**: Verifies `X-API-Version` behavior.
*   **`ContentNegotiationVersioningTest`**: Verifies `Accept` header content negotiation.

---

## 📝 Usage Examples

### Fetching V1 Product (Legacy)
```http
GET /api/v1/products HTTP/1.1
Host: localhost:8080
# Returns: { "id": 1, "name": "Laptop", "description": "..." } (No Price)
```

### Fetching V2 Product (Latest)
```http
GET /api/v2/products HTTP/1.1
Host: localhost:8080
# Returns: { "id": 1, "name": "Laptop", "description": "...", "price": 1200.0 }
```

---

Created by **Anbu** for the [Your Channel Name] Community.
Please **Like, Share, and Subscribe** for more Spring Boot content! 🔔
