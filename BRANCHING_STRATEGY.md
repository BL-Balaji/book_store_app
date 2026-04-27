# 🌿 Branching Strategy - BookStore Microservices

## Overview

This repository follows a **feature branch workflow** where each microservice has its own dedicated feature branch. This approach enables:

- ✅ **Isolated Development** - Work on services independently
- ✅ **Parallel Development** - Multiple teams can work simultaneously
- ✅ **Clean History** - Service-specific commits in dedicated branches
- ✅ **Easy Code Review** - Review changes per service via Pull Requests
- ✅ **Controlled Integration** - Merge to main only when ready

---

## 📋 Branch Structure

### Main Branch
- **Branch**: `main`
- **Purpose**: Integration branch with stable, tested code
- **Protection**: Should be protected (require PR reviews)
- **Merges**: Only from feature branches via Pull Requests

### Feature Branches (13 Total)

Each microservice has its own feature branch:

| # | Branch Name | Service | Port | Purpose |
|---|-------------|---------|------|---------|
| 1 | `feature/eureka-server` | Eureka Server | 8761 | Service discovery |
| 2 | `feature/config-server` | Config Server | 8888 | Configuration management |
| 3 | `feature/api-gateway` | API Gateway | 8080 | API routing & JWT validation |
| 4 | `feature/common-lib` | Common Library | - | Shared utilities & DTOs |
| 5 | `feature/user-service` | User Service | 8081 | Authentication & user management |
| 6 | `feature/admin-service` | Admin Service | 8082 | Admin operations |
| 7 | `feature/product-service` | Product Service | 8083 | Product catalog |
| 8 | `feature/cart-service` | Cart Service | 8084 | Shopping cart (Redis) |
| 9 | `feature/wishlist-service` | Wishlist Service | 8085 | Wishlist management |
| 10 | `feature/customer-details-service` | Customer Service | 8086 | Customer profiles |
| 11 | `feature/order-service` | Order Service | 8087 | Order processing |
| 12 | `feature/feedback-service` | Feedback Service | 8088 | Reviews & ratings |
| 13 | `feature/notification-service` | Notification Service | 8089 | Notifications (Kafka) |

---

## 🔄 Workflow

### 1. Working on a Feature Branch

```bash
# Switch to the service's feature branch
git checkout feature/user-service

# Make your changes
# ... edit files ...

# Stage and commit changes
git add .
git commit -m "feat(user-service): Add email verification feature"

# Push to remote
git push origin feature/user-service
```

### 2. Creating a Pull Request

1. Push your changes to the feature branch
2. Go to GitHub repository
3. Click "Compare & pull request"
4. Select: `feature/user-service` → `main`
5. Add description of changes
6. Request reviews
7. Wait for approval
8. Merge to main

### 3. Keeping Feature Branch Updated

```bash
# Switch to your feature branch
git checkout feature/user-service

# Fetch latest changes from main
git fetch origin main

# Merge main into your feature branch
git merge origin/main

# Resolve any conflicts if needed
# Push updated branch
git push origin feature/user-service
```

---

## 📝 Commit Message Convention

Follow **Conventional Commits** format:

```
<type>(<scope>): <subject>

<body>

<footer>
```

### Types
- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation changes
- `style`: Code style changes
- `refactor`: Code refactoring
- `test`: Adding tests
- `chore`: Maintenance tasks

### Examples

```bash
# Feature addition
git commit -m "feat(user-service): Add password reset functionality"

# Bug fix
git commit -m "fix(order-service): Resolve inventory check race condition"

# Documentation
git commit -m "docs(api-gateway): Update JWT configuration guide"

# Refactoring
git commit -m "refactor(product-service): Optimize database queries"
```

---

## 🎯 Branch Naming Convention

### Pattern
```
feature/<service-name>
```

### Examples
- `feature/user-service`
- `feature/api-gateway`
- `feature/common-lib`

### Additional Branch Types (if needed)

```
bugfix/<service-name>-<issue>     # Bug fixes
hotfix/<service-name>-<issue>     # Urgent production fixes
release/<version>                  # Release preparation
```

---

## 🔒 Branch Protection Rules

### Main Branch Protection (Recommended)

Enable these settings on GitHub:

1. **Require pull request reviews**
   - At least 1 approval required
   
2. **Require status checks to pass**
   - Build must succeed
   - Tests must pass
   
3. **Require branches to be up to date**
   - Must merge latest main before merging
   
4. **Include administrators**
   - Rules apply to everyone
   
5. **Restrict who can push**
   - Only via Pull Requests

---

## 🚀 Development Workflow

### Scenario 1: New Feature Development

```bash
# 1. Switch to feature branch
git checkout feature/user-service

# 2. Pull latest changes
git pull origin feature/user-service

# 3. Create a sub-branch (optional for large features)
git checkout -b feature/user-service-email-verification

# 4. Develop and commit
git add .
git commit -m "feat(user-service): Implement email verification"

# 5. Push to feature branch
git checkout feature/user-service
git merge feature/user-service-email-verification
git push origin feature/user-service

# 6. Create Pull Request on GitHub
# feature/user-service → main
```

### Scenario 2: Bug Fix

```bash
# 1. Switch to affected service branch
git checkout feature/order-service

# 2. Fix the bug
# ... make changes ...

# 3. Commit with fix type
git commit -m "fix(order-service): Resolve payment processing error"

# 4. Push and create PR
git push origin feature/order-service
```

### Scenario 3: Working on Multiple Services

```bash
# Work on User Service
git checkout feature/user-service
# ... make changes ...
git commit -m "feat(user-service): Add profile update"
git push origin feature/user-service

# Switch to Product Service
git checkout feature/product-service
# ... make changes ...
git commit -m "feat(product-service): Add category filter"
git push origin feature/product-service

# Both can be merged independently via PRs
```

---

## 📊 Branch Status

### Current Branches

All feature branches are created and pushed to GitHub:

✅ `feature/eureka-server`  
✅ `feature/config-server`  
✅ `feature/api-gateway`  
✅ `feature/common-lib`  
✅ `feature/user-service`  
✅ `feature/admin-service`  
✅ `feature/product-service`  
✅ `feature/cart-service`  
✅ `feature/wishlist-service`  
✅ `feature/customer-details-service`  
✅ `feature/order-service`  
✅ `feature/feedback-service`  
✅ `feature/notification-service`  

### View All Branches

```bash
# List local branches
git branch

# List all branches (local + remote)
git branch -a

# List remote branches only
git branch -r
```

---

## 🔍 Checking Branch Status

### View Current Branch
```bash
git branch
# or
git status
```

### View Branch Differences
```bash
# Compare feature branch with main
git diff main..feature/user-service

# View commits in feature branch not in main
git log main..feature/user-service
```

### View Branch History
```bash
# View commit history
git log --oneline --graph --all

# View specific branch history
git log feature/user-service --oneline
```

---

## 🔄 Syncing Branches

### Update Feature Branch from Main

```bash
# Method 1: Merge
git checkout feature/user-service
git merge main
git push origin feature/user-service

# Method 2: Rebase (cleaner history)
git checkout feature/user-service
git rebase main
git push origin feature/user-service --force-with-lease
```

### Update All Feature Branches

```bash
# Script to update all feature branches
git checkout main
git pull origin main

for branch in $(git branch | grep feature/); do
    git checkout $branch
    git merge main
    git push origin $branch
done

git checkout main
```

---

## 📦 Pull Request Template

When creating a PR, include:

```markdown
## Description
Brief description of changes

## Type of Change
- [ ] New feature
- [ ] Bug fix
- [ ] Breaking change
- [ ] Documentation update

## Service
- [ ] Eureka Server
- [ ] Config Server
- [ ] API Gateway
- [ ] User Service
- [ ] (other services...)

## Testing
- [ ] Unit tests added/updated
- [ ] Integration tests added/updated
- [ ] Manual testing performed

## Checklist
- [ ] Code follows style guidelines
- [ ] Self-review completed
- [ ] Documentation updated
- [ ] No new warnings
- [ ] Tests pass locally
```

---

## 🎓 Best Practices

### DO ✅

1. **Keep feature branches focused** - One service per branch
2. **Commit frequently** - Small, logical commits
3. **Write descriptive commit messages** - Follow conventions
4. **Pull before push** - Stay updated with remote changes
5. **Create PRs for merging** - Enable code review
6. **Delete merged branches** - Keep repository clean
7. **Test before merging** - Ensure code works

### DON'T ❌

1. **Don't commit directly to main** - Always use feature branches
2. **Don't mix services** - Keep service changes separate
3. **Don't force push** - Unless absolutely necessary
4. **Don't commit sensitive data** - Use .gitignore
5. **Don't merge without review** - Get approval first
6. **Don't leave branches stale** - Keep updated with main

---

## 🛠️ Useful Git Commands

### Branch Management
```bash
# Create new branch
git checkout -b feature/new-service

# Switch branches
git checkout feature/user-service

# Delete local branch
git branch -d feature/old-branch

# Delete remote branch
git push origin --delete feature/old-branch

# Rename branch
git branch -m old-name new-name
```

### Viewing Changes
```bash
# View uncommitted changes
git diff

# View staged changes
git diff --staged

# View changes between branches
git diff main..feature/user-service
```

### Undoing Changes
```bash
# Discard local changes
git checkout -- filename

# Unstage files
git reset HEAD filename

# Undo last commit (keep changes)
git reset --soft HEAD~1

# Undo last commit (discard changes)
git reset --hard HEAD~1
```

---

## 📞 Support

For questions about branching strategy:
- Check this document
- Review Git documentation
- Ask in team discussions
- Open an issue on GitHub

---

## 🔗 Quick Links

- **Repository**: https://github.com/BL-Balaji/book_store_app
- **All Branches**: https://github.com/BL-Balaji/book_store_app/branches
- **Pull Requests**: https://github.com/BL-Balaji/book_store_app/pulls
- **Reference Repo**: https://github.com/SKarthik12321/BookStore-Microservices

---

**Last Updated**: 2024  
**Total Branches**: 14 (1 main + 13 feature branches)  
**Status**: ✅ All branches created and pushed
