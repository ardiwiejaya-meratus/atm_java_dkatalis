# ATM System - Operation Manual

## Quick Start

```bash
./start.sh
```

This will compile and run the ATM CLI application.

## Commands Reference

### Authentication

#### `login [name]`
Logs in as a customer and creates the account if it doesn't exist.

**Example:**
```
$ login Alice
Hello, Alice!
Your balance is $0
```

#### `logout`
Logs out of the current account.

**Example:**
```
$ logout
Goodbye, Alice!
```

### Operations

#### `deposit [amount]`
Deposits money to the logged-in account.

**Example:**
```
$ deposit 100
Your balance is $100
```

**Note:** If account has outstanding debt, deposit automatically settles part of it.

#### `withdraw [amount]`
Withdraws money from the logged-in account. Negative balance is allowed (creates debt).

**Example:**
```
$ withdraw 50
Your balance is $50
```

#### `transfer [target] [amount]`
Transfers money to another account. Target account is created if it doesn't exist.

If insufficient balance, creates a debt obligation.

**Example - Sufficient Balance:**
```
$ transfer Bob 50
Transferred $50 to Bob
Your balance is $50
```

**Example - Insufficient Balance:**
```
$ transfer Bob 100
Transferred $30 to Bob
Owed $70 to Bob
Your balance is $0
```

## Usage Scenarios

### Scenario 1: Basic Deposit and Withdrawal
```
$ login Alice
Hello, Alice!
Your balance is $0

$ deposit 100
Your balance is $100

$ withdraw 30
Your balance is $70
```

### Scenario 2: Transfer with Sufficient Balance
```
$ login Alice
Hello, Alice!
Your balance is $100

$ transfer Bob 50
Transferred $50 to Bob
Your balance is $50
```

### Scenario 3: Transfer with Insufficient Balance (Creates Debt)
```
$ login Bob
Hello, Bob!
Your balance is $0

$ transfer Alice 100
Transferred $0 to Alice
Owed $100 to Alice
Your balance is $0

$ deposit 50
Transferred $50 to Alice
Your balance is $0
Owed $50 to Alice
```

### Scenario 4: Multi-user Interactions
```
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
```

## Error Handling

### Invalid Amount Format
```
$ deposit abc
Error: Invalid amount format. Please enter a valid number.
```

### Negative Amount
```
$ withdraw -50
Error: Amount must be non-negative
```

### Not Logged In
```
$ deposit 100
Not logged in
```

### Missing Arguments
```
$ transfer Alice
Usage: transfer [target] [amount]
```

## Data Reset

Each time you run `./start.sh`, all accounts are reset to fresh state with zero balance and no debt. This is by design to ensure a clean environment for each session.

## Precision

All monetary amounts use BigDecimal for precise calculations, avoiding floating-point rounding errors.

## Examples from Requirements

Full example session from the specification:

```
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

$ login Alice
Hello, Alice!
Your balance is $210
Owed $40 from Bob

$ transfer Bob 30
Your balance is $180
Owed $10 from Bob

$ logout
Goodbye, Alice!

$ login Bob
Hello, Bob!
Your balance is $0
Owed $10 to Alice

$ deposit 100
Transferred $10 to Alice
Your balance is $90

$ logout
Goodbye, Bob!
```
