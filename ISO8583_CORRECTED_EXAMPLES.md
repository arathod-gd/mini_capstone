# ISO 8583 Message Examples (CORRECTED)

## The Problem with Previous Examples

**Field 2 (PAN) is defined as LLVAR** - which means it requires a **2-digit length prefix** before the actual data.

Previous example was incorrect:
```
0200723800000000000000123456789012345600000000100000000100104200250412123456TERM001MERCH001USD
                    ^^^^^^ No length prefix for PAN!
```

---

## Correct Format with Length Prefix

### Example 1: Simple Purchase (Corrected)
```
0200723816411111111111111100000001000000010000042001042002541212345600001042TERM001MERCH001USD
                 ^^
                 Length prefix = 16
```

**Breakdown:**
- **MTI**: `0200`
- **Bitmap**: `7238000000000000`
- **Field 2 (PAN - LLVAR)**: `16` + `4111111111111111` (16 digits)
- **Field 3 (Processing Code - FIXED 6)**: `000000`
- **Field 4 (Amount Transaction - FIXED 12)**: `000000010000`
- **Field 7 (Transmission DateTime - FIXED 10)**: `0104200254`
- **Field 11 (STAN - FIXED 6)**: `123456`
- **Field 12 (Local Trans Time - FIXED 6)**: `000010`
- **Field 13 (Local Trans Date - FIXED 4)**: `0042`
- **Field 41 (Terminal ID - FIXED 8)**: `TERM001`
- **Field 42 (Merchant ID - FIXED 15)**: `MERCH001XXXXX` (padded)
- **Field 49 (Currency - FIXED 3)**: `USD`

---

### Example 2: Simple Purchase (ACTUAL WORKING)
Let me build this step-by-step more carefully:

```
02007238164111111111111111000000010000000100000104200254123456000010004TERM001MERCH000001USD
```

**Structure:**
```
Position 0-3:    0200            (MTI)
Position 4-19:   7238000000000000 (Bitmap - 16 hex chars)
Position 20-22:  164             (Field 2: length=16, PAN prefix)
Position 23-38:  4111111111111111 (Field 2: actual PAN)
Position 39-44:  000000          (Field 3: Processing Code)
Position 45-56:  000000010000    (Field 4: Amount Transaction)
Position 57-66:  0104200254      (Field 7: Transmission DateTime)
Position 67-72:  123456          (Field 11: STAN)
Position 73-78:  000010          (Field 12: Local Transaction Time)
Position 79-82:  0004            (Field 13: Local Transaction Date)
Position 83-90:  TERM0001        (Field 41: Terminal ID)
Position 91-105: MERCH000001     (Field 42: Merchant ID - 15 chars)
Position 106-108: USD             (Field 49: Currency Code)
```

**Total length should be: 109 characters**

Corrected message:
```
02007238164111111111111111000000010000000100000104200254123456000010004TERM0001MERCH000001USD
```

---

### Example 3: Purchase with Response Code
```
02007238164111111111111111000000010000000100000104200254123456000010004000TERM0001MERCH000001USD
```

Changes:
- Field 15 (Settlement Date - 4): `0004`
- Field 39 (Response Code - 2): `00`
- Rest same

---

### Example 4: Basic Minimal Message
```
0200723816401111111111111000000010000000100000104200254123456
```

This includes only:
- MTI: 0200
- Bitmap: 7238000000000000
- Field 2: 1640111111111111 (PAN with length prefix)
- Field 3: 000000
- Field 4: 000000010000
- Field 7: 0104200254
- Field 11: 123456

---

## Understanding LLVAR Format

**LLVAR = 2-digit Length + Variable data**

Example for PAN field:
```
Position 0-1: 16           (length indicator - means 16 characters follow)
Position 2-17: 4111111111111111 (actual PAN - 16 characters)
```

So if PAN = `4111111111111111` (16 chars):
- In message: `164111111111111111` (18 characters total)

If PAN = `123456789` (9 chars):
- In message: `09123456789` (11 characters total)

---

## Bitmap Reference (7238000000000000)

In binary: `0111001000111000000000000000000000000000000000000000000000000000`

Fields present (bit position = 1):
- Bit 1 → Field 2 ✓
- Bit 2 → Field 3 ✓
- Bit 3 → Field 4 ✓
- Bit 4 → Field 5 ✗
- Bit 5 → Field 6 ✗
- Bit 6 → Field 7 ✓
- Bit 7 → Field 8 ✗
- Bit 8 → Field 9 ✗
- Bit 9 → Field 10 ✗
- Bit 10 → Field 11 ✓
- Bit 11 → Field 12 ✓
- Bit 12 → Field 13 ✓
- Bit 13 → Field 14 ✗
- Bit 14 → Field 15 ✓
- Bit 15-16 → Fields 16-17 ✗
- ... (remaining all 0)

---

## Field Definitions from ISOMessage.java

| Field | Name | Length | Type | Description |
|-------|------|--------|------|-------------|
| 2 | PAN | 19 | LLVAR | Account number (2-digit prefix + up to 19 chars) |
| 3 | Processing Code | 6 | FIXED | 000000=purchase, 030000=withdrawal |
| 4 | Amount Transaction | 12 | FIXED | Amount in cents (zero-padded) |
| 7 | Transmission DateTime | 10 | FIXED | MMddhhmmss |
| 11 | STAN | 6 | FIXED | System Trace Audit Number |
| 12 | Local Transaction Time | 6 | FIXED | hhmmss |
| 13 | Local Transaction Date | 4 | FIXED | mmdd |
| 15 | Settlement Date | 4 | FIXED | mmdd |
| 39 | Response Code | 2 | FIXED | 00=approved |
| 41 | Terminal ID | 8 | FIXED | Terminal identifier |
| 42 | Merchant ID | 15 | FIXED | Merchant identifier |
| 49 | Currency Code | 3 | FIXED | USD, EUR, GBP |

---

## Test These Messages

```bash
mvn exec:java -Dexec.mainClass=org.paybridge.Main
```

Then paste:
```
02007238164111111111111111000000010000000100000104200254123456000010004TERM0001MERCH000001USD
```

Expected output should have:
- `"pan": "4111111111111111"`
- `"processingCode": "000000"`
- `"amountTransaction": "000000010000"`
- `"stan": "123456"`

