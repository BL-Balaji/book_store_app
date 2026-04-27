# Contributing to BookStore Microservices

Thank you for your interest in contributing to the BookStore Microservices project! This document provides guidelines and instructions for contributing.

## Table of Contents

- [Code of Conduct](#code-of-conduct)
- [Getting Started](#getting-started)
- [Development Workflow](#development-workflow)
- [Coding Standards](#coding-standards)
- [Testing Guidelines](#testing-guidelines)
- [Commit Message Guidelines](#commit-message-guidelines)
- [Pull Request Process](#pull-request-process)

## Code of Conduct

This project adheres to a code of conduct. By participating, you are expected to uphold this code. Please be respectful and constructive in all interactions.

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- Docker & Docker Compose
- Git
- IDE (IntelliJ IDEA, Eclipse, or VS Code recommended)

### Fork and Clone

1. Fork the repository on GitHub
2. Clone your fork locally:
   ```bash
   git clone https://github.com/YOUR_USERNAME/book_store_app.git
   cd book_store_app
   ```

3. Add upstream remote:
   ```bash
   git remote add upstream https://github.com/BL-Balaji/book_store_app.git
   ```

### Build the Project

```bash
mvn clean install
```

## Development Workflow

### 1. Create a Feature Branch

```bash
git checkout -b feature/your-feature-name
```

Branch naming conventions:
- `feature/` - New features
- `bugfix/` - Bug fixes
- `hotfix/` - Urgent fixes
- `refactor/` - Code refactoring
- `docs/` - Documentation updates

### 2. Make Your Changes

- Write clean, readable code
- Follow the coding standards (see below)
- Add tests for new functionality
- Update documentation as needed

### 3. Test Your Changes

```bash
# Run unit tests
mvn test

# Run integration tests
mvn verify

# Run specific service tests
cd user-service
mvn test
```

### 4. Commit Your Changes

```bash
git add .
git commit -m "feat: add user profile update feature"
```

See [Commit Message Guidelines](#commit-message-guidelines) below.

### 5. Push to Your Fork

```bash
git push origin feature/your-feature-name
```

### 6. Create a Pull Request

- Go to the original repository on GitHub
- Click "New Pull Request"
- Select your fork and branch
- Fill in the PR template
- Submit the pull request

## Coding Standards

### Java Code Style

- Follow standard Java naming conventions
- Use meaningful variable and method names
- Keep methods small and focused (Single Responsibility Principle)
- Maximum line length: 120 characters
- Use 4 spaces for indentation (no tabs)

### Package Structure

Each service should follow this structure:

```
com.bookstore.{service-name}/
├── controller/      # REST controllers
├── service/         # Business logic
├── repository/      # Data access
├── entity/          # JPA entities
├── dto/             # Data Transfer Objects
├── config/          # Configuration classes
├── security/        # Security configurations
├── exception/       # Custom exceptions
└── util/            # Utility classes
```

### Annotations

Use appropriate Spring annotations:
- `@RestController` for REST controllers
- `@Service` for service classes
- `@Repository` for repository interfaces
- `@Component` for other Spring-managed beans
- `@Transactional` for transactional methods

### Lombok

Use Lombok to reduce boilerplate:
- `@Data` for DTOs
- `@Builder` for builder pattern
- `@RequiredArgsConstructor` for constructor injection
- `@Slf4j` for logging

### Exception Handling

- Use custom exceptions from `common-lib`
- Handle exceptions in `@RestControllerAdvice`
- Provide meaningful error messages
- Log exceptions appropriately

### Logging

```java
@Slf4j
public class UserService {
    public void someMethod() {
        log.info("Processing user request");
        log.debug("User details: {}", user);
        log.error("Error occurred", exception);
    }
}
```

## Testing Guidelines

### Unit Tests

- Write unit tests for all service methods
- Use JUnit 5 and Mockito
- Aim for >80% code coverage
- Test both success and failure scenarios

Example:
```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserService userService;
    
    @Test
    void shouldRegisterUser() {
        // Given
        RegisterRequest request = new RegisterRequest();
        // ... setup
        
        // When
        AuthResponse response = userService.register(request);
        
        // Then
        assertNotNull(response);
        verify(userRepository).save(any(User.class));
    }
}
```

### Integration Tests

- Test API endpoints end-to-end
- Use `@SpringBootTest` and `TestRestTemplate`
- Use Testcontainers for database tests

### Test Naming

Use descriptive test names:
- `shouldReturnUserWhenValidIdProvided()`
- `shouldThrowExceptionWhenUserNotFound()`
- `shouldUpdateUserSuccessfully()`

## Commit Message Guidelines

Follow the Conventional Commits specification:

### Format

```
<type>(<scope>): <subject>

<body>

<footer>
```

### Types

- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation changes
- `style`: Code style changes (formatting, etc.)
- `refactor`: Code refactoring
- `test`: Adding or updating tests
- `chore`: Maintenance tasks

### Examples

```
feat(user-service): add email verification feature

Implement email verification using JWT tokens sent via email.
Users must verify their email before accessing protected resources.

Closes #123
```

```
fix(order-service): resolve inventory check race condition

Add pessimistic locking to prevent overselling when multiple
orders are placed simultaneously.

Fixes #456
```

## Pull Request Process

### Before Submitting

1. Ensure all tests pass
2. Update documentation if needed
3. Add/update tests for your changes
4. Rebase on latest main branch
5. Ensure no merge conflicts

### PR Description

Include:
- **What**: Brief description of changes
- **Why**: Reason for the changes
- **How**: Technical approach
- **Testing**: How you tested the changes
- **Screenshots**: If UI changes (N/A for this project)

### PR Template

```markdown
## Description
Brief description of changes

## Type of Change
- [ ] Bug fix
- [ ] New feature
- [ ] Breaking change
- [ ] Documentation update

## Testing
- [ ] Unit tests added/updated
- [ ] Integration tests added/updated
- [ ] Manual testing performed

## Checklist
- [ ] Code follows project style guidelines
- [ ] Self-review completed
- [ ] Comments added for complex code
- [ ] Documentation updated
- [ ] No new warnings generated
- [ ] Tests pass locally
```

### Review Process

- At least one approval required
- All CI checks must pass
- Address all review comments
- Maintainers will merge approved PRs

## Additional Guidelines

### API Changes

- Maintain backward compatibility when possible
- Document breaking changes clearly
- Update API documentation
- Update Swagger annotations

### Database Changes

- Use Liquibase or Flyway for migrations (if implemented)
- Test migrations on clean database
- Provide rollback scripts
- Document schema changes

### Configuration Changes

- Update `application.yml` files
- Document new configuration properties
- Provide sensible defaults
- Update Docker Compose if needed

### Security

- Never commit sensitive data (passwords, keys, tokens)
- Use environment variables for secrets
- Follow OWASP security guidelines
- Report security vulnerabilities privately

## Questions?

- Open an issue for bugs or feature requests
- Start a discussion for questions
- Contact maintainers for sensitive issues

## License

By contributing, you agree that your contributions will be licensed under the MIT License.

---

Thank you for contributing to BookStore Microservices! 🚀
