# ATM CLI System

A complete Command-Line Interface for simulating ATM operations with multi-user support, debt tracking, and automatic debt settlement.

## ✅ Requirements Compliance

This submission fully complies with all challenge requirements:

- ✅ **Programming Language:** Java 11
- ✅ **Working Application:** CLI that handles all specified commands
- ✅ **Start Script:** Executable `start.sh` at root
- ✅ **Fresh Environment:** New data on each execution
- ✅ **Testing:** 34 comprehensive unit tests covering all functionality
- ✅ **Documentation:** 
  - `OPERATION_MANUAL.md` - Detailed usage instructions
  - `ASSUMPTIONS_AND_DEVIATIONS.md` - Design decisions
  - `TESTING_REPORT.md` - Complete test coverage report
- ✅ **Exception Handling:** Graceful error handling for all edge cases
- ✅ **No Third-party Frameworks:** Only standard Java and JUnit

## Project Structure

```
atm-java/
├── src/
│   ├── main/java/com/atm/
│   │   ├── model/Account.java              # Account entity
│   │   ├── repository/AccountRepository.java # Data access
│   │   ├── service/ATMService.java         # Business logic
│   │   ├── ui/ATMCLI.java                  # CLI interface
│   │   └── ATMApp.java                     # Entry point
│   └── test/java/com/atm/
│       ├── model/AccountTest.java          # Model tests
│       ├── service/ATMServiceTest.java     # Service tests
│       └── repository/AccountRepositoryTest.java
├── pom.xml                                  # Maven configuration
├── start.sh                                 # Start script
├── OPERATION_MANUAL.md                     # Usage guide
├── ASSUMPTIONS_AND_DEVIATIONS.md           # Design decisions
├── TESTING_REPORT.md                       # Test coverage
├── .gitignore                              # Git configuration
└── README.md                               # This file
```

## Quick Start

### Prerequisites
- Java 11 or higher
- Maven 3.6 or higher

### Run Application
```bash
./start.sh
```

### Run Tests
```bash
mvn test
```

## Commands

| Command | Usage | Example |
|---------|-------|---------|
| `login` | `login [name]` | `login Alice` |
| `logout` | `logout` | `logout` |
| `deposit` | `deposit [amount]` | `deposit 100` |
| `withdraw` | `withdraw [amount]` | `withdraw 50` |
| `transfer` | `transfer [target] [amount]` | `transfer Bob 25` |

## Features

### Core Functionality
- ✅ Multi-user accounts with automatic creation
- ✅ Deposit and withdrawal operations
- ✅ Money transfers between accounts
- ✅ Debt tracking and settlement
- ✅ Automatic debt payment on deposit

### Robustness
- ✅ BigDecimal for precise financial calculations
- ✅ Exception handling for all error cases
- ✅ Input validation and error messages
- ✅ Edge case handling (zero amounts, negative balance, etc.)

### Testing
- ✅ 34 unit tests with 100% pass rate
- ✅ Integration test covering full example session
- ✅ Edge case testing
- ✅ Error scenario testing

## Design Decisions

1. **BigDecimal for Money** - Ensures precision without floating-point errors
2. **Repository Pattern** - Separates data access from business logic
3. **Service Layer** - Encapsulates all business operations
4. **Single `owed` Field** - Simplified debt tracking sufficient for scope
5. **Automatic Debt Settlement** - Deposits automatically pay creditors

See `ASSUMPTIONS_AND_DEVIATIONS.md` for detailed design rationale.

## Testing

### Test Statistics
- **Total Tests:** 34
- **Pass Rate:** 100%
- **Coverage:** All major paths and edge cases

### Test Categories
- Model tests: 9 tests
- Service tests: 18 tests  
- Repository tests: 7 tests

Run all tests:
```bash
mvn test
```

See `TESTING_REPORT.md` for detailed test coverage analysis.

## Example Session

```bash
$ login Alice
Hello, Alice!
Your balance is $0

$ deposit 100
Your balance is $100

$ logout
Goodbye, Alice!

$ login Bob
Hello, Bob!
Your balance is $0

$ deposit 80
Your balance is $80

$ transfer Alice 50
Transferred $50 to Alice
Your balance is $30

$ transfer Alice 100
Transferred $30 to Alice
Owed $70 to Alice
Your balance is $0

$ deposit 30
Transferred $30 to Alice
Your balance is $0
Owed $40 to Alice

$ logout
Goodbye, Bob!
```

## Error Handling

Examples of graceful error handling:

```bash
$ deposit abc
Error: Invalid amount format. Please enter a valid number.

$ withdraw -50
Error: Amount must be non-negative

$ transfer Alice
Usage: transfer [target] [amount]

$ deposit 100
Not logged in
```

## Documentation Files

- **OPERATION_MANUAL.md** - Comprehensive usage guide with scenarios
- **ASSUMPTIONS_AND_DEVIATIONS.md** - Design decisions and assumptions
- **TESTING_REPORT.md** - Detailed test coverage and results
- **README.md** - This file

## Code Quality

- Clean separation of concerns (Model, Repository, Service, UI)
- Proper exception handling and validation
- Comprehensive unit tests
- Maintainable and extensible design
- Follows Java naming conventions
- Well-documented code

## Data Persistence

**Note:** Data is NOT persisted between application runs. This is by design:
- Each `./start.sh` execution starts fresh
- Suitable for simulation/testing purposes
- Production version would add database layer

## Future Enhancements

For production use, consider:
- Add persistent data storage (database)
- Add transaction history/audit log
- Implement concurrent access safety (threading)
- Add interest calculations
- Implement overdraft limits
- Add account types (checking, savings)

## Support

For detailed operation instructions, see `OPERATION_MANUAL.md`.
For design details, see `ASSUMPTIONS_AND_DEVIATIONS.md`.
For test coverage details, see `TESTING_REPORT.md`.
# atm_java_dkatalis
