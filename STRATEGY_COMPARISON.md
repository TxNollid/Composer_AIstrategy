# AI Infrastructure Stack - Strategy Comparison

## Drawdown Protection Analysis

### Problem: November 2025 Drawdown (~20%)
All three strategies aim to minimize extended drawdowns like the 20% drop in November 2025.

---

## Strategy 1: Original Risk-Adjusted Sector Rotation
**File:** `ai_stack_strategy.clj`

### Approach:
- Single regime filter: SPY vs 200-day MA
- RSI-based tactical switches per sector
- Leveraged ETFs for risk-on/risk-off

### Drawdown Vulnerabilities:
- ❌ **Whipsaw risk**: Single MA can flip frequently in choppy markets
- ❌ **Late exits**: 200-day MA is slow - could be 10-15% down before signal
- ❌ **Leveraged exposure**: 3x ETFs amplify losses during volatility
- ❌ **No cash option**: Always 100% invested (either long or short)

### Best For:
- Trending markets with clear direction
- Traders comfortable with leverage
- Quick recovery environments

---

## Strategy 2: Enhanced Drawdown Protection (RECOMMENDED)
**File:** `ai_stack_defensive.clj`

### Approach:
- **Multi-timeframe filter**: SPY vs BOTH 50-day AND 200-day MA
- **Volatility check**: UVXY/RSI to detect fear spikes
- **Individual stock momentum**: Each holding must be above 50-day MA
- **Cash allocation**: SHY (short-term bonds) instead of forced positions
- **Graduated risk levels**: 4 market states with different allocations

### Key Improvements:
✅ **Earlier exits**: 50-day MA breaks before 200-day (~5-10% faster signal)
✅ **Momentum confirmation**: Won't hold falling stocks in bull market
✅ **Volatility kill switch**: Moves to cash during VIX spikes
✅ **Capital preservation**: SHY allocation prevents forced losses
✅ **Tighter RSI bands**: 35-65 instead of 30-70 (earlier warnings)

### Market States:
1. **Strong Bull** (SPY > both MAs, VIX calm): Full sector rotation
2. **Bull + Vol Spike**: 100% SHY (wait for calm)
3. **Pullback** (SPY > 200 but < 50): 50% cash, 50% mega-caps
4. **Bear** (SPY < 200): Either 100% inverse or 100% SHY

### Best For:
- **Extended drawdown protection** ← YOUR NEED
- Risk-averse investors
- Preservation of capital during uncertainty

---

## Strategy 3: Trend Following with Cash Allocation
**File:** `ai_stack_trend_cash.clj`

### Approach:
- **Fixed cash buffers**: Always holds 20-60% cash depending on regime
- **Explicit weight allocation**: Uses `weight` function for precise control
- **Sector-specific MA lookbacks**: Longer MAs (100-day) for stable sectors
- **Gradual de-risking**: 80% → 40% → 0% equity allocation

### Key Improvements:
✅ **Permanent cash buffer**: 20% SHY even in bull markets
✅ **Smooth transitions**: Gradual position sizing prevents all-or-nothing
✅ **Flexible exposure**: Can be 40% invested during pullbacks
✅ **Downside asymmetry**: Max 30% inverse vs 70% cash in severe bear

### Market States:
1. **Strong Uptrend**: 80% equities (diversified), 20% cash
2. **Pullback**: 40% mega-caps + infrastructure, 60% cash
3. **Mild Bear**: 100% cash
4. **Severe Bear**: 30% inverse, 70% cash

### Best For:
- Conservative capital allocation
- Smooth equity curve preference
- Lower volatility tolerance

---

## Handling November 2025 Drawdown - Simulation

### Scenario: Market starts declining mid-November

**Strategy 1 (Original):**
- Day 1-10: SPY still above 200-day MA → Full sector exposure → Down 8-10%
- Day 11: SPY breaks 200-day → Flip to inverse ETFs → Whipsaw if market bounces
- **Estimated drawdown**: 12-15% before protection kicks in

**Strategy 2 (Defensive) - RECOMMENDED:**
- Day 1-3: SPY breaks 50-day MA → Individual stocks fail 50-day checks → Rotate to SHY
- Day 4-7: UVXY RSI spikes → Full portfolio to SHY → Preserving capital
- Day 8+: Below 200-day → Either SHY or inverse positions
- **Estimated drawdown**: 4-6% before moving to cash

**Strategy 3 (Trend/Cash):**
- Day 1: Starts with 20% cash buffer → Only 80% exposed
- Day 3-5: Drops from 80% to 40% equity as 50-day breaks → Half protected
- Day 6+: Moves to 100% cash
- **Estimated drawdown**: 6-8% with smooth transition

---

## Recommendation

For **extended drawdown protection** like November 2025:

🏆 **Use Strategy 2 (ai_stack_defensive.clj)**

### Why:
1. **Fastest response time**: 50-day MA + momentum filters catch trends early
2. **Volatility protection**: UVXY check prevents holding through fear spikes
3. **No forced positions**: Cash is always an option (not forced into inverse)
4. **Individual stock discipline**: Won't hold dead weight in sectors

### Alternative:
If you prefer **smoother transitions** and **permanent cash buffer**, use Strategy 3 (ai_stack_trend_cash.clj).

### Avoid:
Strategy 1 for drawdown-sensitive portfolios - it's built for momentum/trending, not preservation.

---

## Testing Recommendations

1. **Backtest all three** through November 2025 specifically
2. Check **maximum drawdown** metric (should be <10% target)
3. Verify **recovery time** after November drawdown
4. Examine **Sharpe ratio** (risk-adjusted returns)
5. Look at **total return** - sometimes lower drawdown = better long-term returns

## Notes on Composer Syntax

- `weight` function allows explicit percentage allocations
- `weight-equal` splits allocation evenly among children
- Nested `if` statements create decision trees
- `SHY` (1-3yr Treasuries) acts as "cash" with slight yield
- Consider `BIL` (1-3mo T-bills) if you want even more conservative cash
