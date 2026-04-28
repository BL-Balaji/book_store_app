# GitHub Push Guide

This guide will help you push the BookStore Microservices project to your GitHub repository.

## Prerequisites

1. **Git installed** on your system
2. **GitHub account** created
3. **Repository created** at: https://github.com/BL-Balaji/book_store_app

## Step-by-Step Instructions

### Step 1: Initialize Git Repository

Open your terminal in the project root directory and run:

```bash
git init
```

### Step 2: Add All Files

```bash
git add .
```

### Step 3: Create Initial Commit

```bash
git commit -m "Initial commit: Complete BookStore Microservices application

- Implemented 12 microservices (Eureka, Config, Gateway, User, Admin, Product, Cart, Wishlist, Customer, Order, Feedback, Notification)
- Added Spring Boot 3.x with Spring Cloud
- Configured JWT authentication
- Integrated PostgreSQL, Redis, and Kafka
- Added Docker Compose for deployment
- Included comprehensive documentation"
```

### Step 4: Add Remote Repository

```bash
git remote add origin https://github.com/BL-Balaji/book_store_app.git
```

### Step 5: Verify Remote

```bash
git remote -v
```

You should see:
```
origin  https://github.com/BL-Balaji/book_store_app.git (fetch)
origin  https://github.com/BL-Balaji/book_store_app.git (push)
```

### Step 6: Push to GitHub

```bash
git branch -M main
git push -u origin main
```

If you encounter authentication issues, you may need to use a Personal Access Token (PAT) instead of your password.

### Step 7: Verify on GitHub

Visit https://github.com/BL-Balaji/book_store_app and verify all files are uploaded.

## Alternative: Using SSH

If you prefer SSH authentication:

### 1. Generate SSH Key (if you don't have one)

```bash
ssh-keygen -t ed25519 -C "your-email@example.com"
```

### 2. Add SSH Key to GitHub

```bash
# Copy your public key
cat ~/.ssh/id_ed25519.pub
```

Go to GitHub → Settings → SSH and GPG keys → New SSH key → Paste the key

### 3. Change Remote to SSH

```bash
git remote set-url origin git@github.com:BL-Balaji/book_store_app.git
```

### 4. Push

```bash
git push -u origin main
```

## Troubleshooting

### Issue: "Repository not found"

**Solution:** Verify the repository exists and you have access:
```bash
git remote -v
```

### Issue: "Authentication failed"

**Solution:** Use a Personal Access Token:
1. Go to GitHub → Settings → Developer settings → Personal access tokens
2. Generate new token with `repo` scope
3. Use token as password when pushing

### Issue: "Large files"

**Solution:** If you have large JAR files, add them to `.gitignore`:
```bash
echo "*.jar" >> .gitignore
echo "target/" >> .gitignore
git rm -r --cached target/
git commit -m "Remove target directories"
git push
```

### Issue: "Permission denied"

**Solution:** Check your GitHub permissions and ensure you're the repository owner or have write access.

## Post-Push Checklist

After successfully pushing to GitHub:

- [ ] Verify README.md displays correctly on GitHub
- [ ] Check all directories are present
- [ ] Ensure .gitignore is working (no target/ or .class files)
- [ ] Verify LICENSE file is recognized by GitHub
- [ ] Check that badges in README are displaying
- [ ] Test cloning the repository to ensure it works

## Updating the Repository

After making changes:

```bash
# Check status
git status

# Add changes
git add .

# Commit with meaningful message
git commit -m "feat: add new feature description"

# Push to GitHub
git push origin main
```

## Creating Branches

For feature development:

```bash
# Create and switch to new branch
git checkout -b feature/new-feature

# Make changes and commit
git add .
git commit -m "feat: implement new feature"

# Push branch to GitHub
git push -u origin feature/new-feature
```

Then create a Pull Request on GitHub.

## Best Practices

1. **Commit Often**: Make small, focused commits
2. **Write Clear Messages**: Use conventional commit format
3. **Pull Before Push**: Always pull latest changes first
4. **Use Branches**: Don't commit directly to main for new features
5. **Review Changes**: Use `git diff` before committing

## GitHub Repository Settings

### Recommended Settings

1. **Add Description**: "Production-grade Spring Boot Microservices Book Store application"
2. **Add Topics**: 
   - spring-boot
   - microservices
   - spring-cloud
   - java
   - docker
   - kafka
   - postgresql
   - redis
   - jwt
   - rest-api

3. **Enable Issues**: For bug tracking and feature requests
4. **Enable Discussions**: For community questions
5. **Add README**: Already included
6. **Add LICENSE**: Already included (MIT)

### Branch Protection (Optional)

For production repositories:
1. Go to Settings → Branches
2. Add rule for `main` branch
3. Enable:
   - Require pull request reviews
   - Require status checks to pass
   - Require branches to be up to date

## Continuous Integration (Optional)

Add GitHub Actions workflow for CI/CD:

Create `.github/workflows/maven.yml`:

```yaml
name: Java CI with Maven

on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]

jobs:
  build:
    runs-on: ubuntu-latest
    
    steps:
    - uses: actions/checkout@v3
    - name: Set up JDK 17
      uses: actions/setup-java@v3
      with:
        java-version: '17'
        distribution: 'temurin'
    - name: Build with Maven
      run: mvn clean install -DskipTests
    - name: Run tests
      run: mvn test
```

## Support

If you encounter any issues:
1. Check the error message carefully
2. Search GitHub documentation
3. Ask in GitHub Discussions
4. Open an issue in the repository

---

**Congratulations!** Your BookStore Microservices project is now on GitHub! 🎉

Repository URL: https://github.com/BL-Balaji/book_store_app
