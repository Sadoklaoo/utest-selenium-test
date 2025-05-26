# uTest Selenium Test Suite

This project is an automated end-to-end test suite for the uTest platform, implemented using Selenium WebDriver, TestNG, and the Page Object Model pattern. It covers common user flows such as login/logout, profile editing, static page verification, and UI interactions like dropdowns and hovers.

---

## Table of Contents

* [Features](#features)
* [Prerequisites](#prerequisites)
* [Installation](#installation)
* [Configuration](#configuration)
* [Project Structure](#project-structure)
* [Running Tests](#running-tests)
* [Writing New Tests](#writing-new-tests)
* [Contributing](#contributing)
* [License](#license)

---

## Features

* **Login & Logout** flow verification
* **Profile Editing**: text inputs, text areas, radio buttons, multi-select dropdowns
* **Static Page Tests**: verify headings on Terms & Conditions, Privacy Policy, Community Guidelines
* **UI Interactions**: explicit waits, hovers, JavaScript-driven clicks, cookie banner dismissal
* **Configuration-driven**: timeouts, URLs and credentials managed via `config.properties`

---

## Prerequisites

* Java Development Kit (JDK) 11 or higher
* [Maven](https://maven.apache.org/) or [Gradle](https://gradle.org/) (this project uses Gradle)
* Google Chrome browser (compatible with the ChromeDriver version)
* ChromeDriver on your PATH or managed by [WebDriverManager](https://github.com/bonigarcia/webdrivermanager)

---

## Installation

1. Clone this repository:

   ```bash
   git clone https://github.com/your-org/utest-selenium-test.git
   cd utest-selenium-test
   ```

2. Ensure you have ChromeDriver installed and available in your PATH.

3. (Optional) Configure environment variables or use the `config.properties` file as described below.

---

## Configuration

The project reads runtime settings from `src/test/resources/config.properties`. You must create or update this file with the following keys:

```properties
# Timeout settings (in seconds)
timeout.default=10

# Base URLs
guidelines.url=https://www.utest.com/utest-guidelines
home.url=https://www.utest.com/

# Credentials (use a test user with limited privileges)
credentials.username=your.email@example.com
credentials.password=YourSecurePassword
```

> **Tip:** For sensitive data (passwords, API keys), consider using environment variables or a secrets manager rather than checking them into version control.

---

## Project Structure

```
utest-selenium-test
├── src
│   ├── main
│   │   └── java
│   │       └── pages         # Page Object classes
│   │           ├── BasePage.java
│   │           ├── LoginPage.java
│   │           ├── HomePage.java
│   │           └── ProfilePage.java
│   └── test
│       ├── java
│       │   └── tests         # TestNG test classes
│       │       ├── LoginTest.java
│       │       ├── ProfilePageTest.java
│       │       ├── StaticPagesTest.java
│       │       └── GuidelinesPageTest.java
│       └── resources
│           └── config.properties
└── build.gradle              # Gradle build file
```

---

## Running Tests

Execute the full suite with Gradle:

```bash
./gradlew test
```

To run a specific test class:

```bash
./gradlew test --tests "*LoginTest*"
```

Test reports will be generated under `build/reports/tests/`.

---

## Writing New Tests

1. **Create a new Page Object** under `src/main/java/pages` that encapsulates element locators and interactions.
2. **Write a TestNG class** under `src/test/java/tests`, import your Page class, and implement test methods with clear `@Test` descriptions.
3. **Use configuration** for any URLs, timeouts, or credentials.
4. **Leverage helper methods** in `BasePage` for common operations (e.g., dismissing the cookie banner).
5. **Follow the Page Object Model**: keep locators and actions in Page classes, and assertions in Test classes.

---

## Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/YourFeature`
3. Commit your changes: \`git commit -m "Add some feature"
4. Push to your branch: `git push origin feature/YourFeature`
5. Open a Pull Request

Please ensure all tests pass locally before submitting.

---

## License

This project is licensed under the [MIT License](LICENSE).
