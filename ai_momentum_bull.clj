(defsymphony
 "AI Infrastructure - Momentum-Driven Secular Bull"
 {:asset-class "EQUITIES", :rebalance-threshold 0.08}

 ;; Layer 1: Only exit AI exposure in SEVERE bear markets
 ;; Using 200-day MA only (not 50-day to avoid premature exits)
 (if
  (> (current-price "SPY") (moving-average-price "SPY" {:window 200}))

  ;; === BULL MARKET: Stay invested in AI with momentum-based sector rotation ===
  ;; Layer 2: Allocate based on sector momentum strength
  [(weight
    [;; 35% PROCESSORS & NETWORKING (Core AI Compute)
     ;; These are the picks-and-shovels of AI - they win regardless
     [(if
       ;; Only exit if NVDA breaks its own 200-day MA (secular trend break)
       (> (current-price "NVDA") (moving-average-price "NVDA" {:window 200}))

       ;; Stay invested - check relative momentum for allocation
       [(if
         ;; Strong momentum: Use leveraged exposure
         (> (rsi "NVDA" {:window 14}) 55)
         [(weight-equal
           [(asset "NVDA" "NVIDIA Corp")
            (asset "AMD" "Advanced Micro Devices")
            (asset "AVGO" "Broadcom Inc")
            (asset "SOXL" "Direxion Daily Semiconductor Bull 3X")])]
         ;; Moderate momentum: Quality stocks only
         [(weight-equal
           [(asset "NVDA" "NVIDIA Corp")
            (asset "AMD" "Advanced Micro Devices")
            (asset "AVGO" "Broadcom Inc")
            (asset "ANET" "Arista Networks Inc")])])]

       ;; NVDA secular trend broken: Rotate to mega-cap tech
       [(weight-equal
         [(asset "MSFT" "Microsoft Corp")
          (asset "GOOGL" "Alphabet Inc")
          (asset "AAPL" "Apple Inc")])])]
     0.35

     ;; 30% MODELS & APPLICATIONS (AI Software Layer)
     ;; The companies building on top of the infrastructure
     [(if
       ;; Check if mega-cap tech is in uptrend
       (> (current-price "MSFT") (moving-average-price "MSFT" {:window 200}))

       [(if
         ;; Strong momentum: Include high-beta AI plays
         (> (rsi "META" {:window 14}) 50)
         [(weight-equal
           [(asset "META" "Meta Platforms Inc")
            (asset "GOOGL" "Alphabet Inc")
            (asset "MSFT" "Microsoft Corp")
            (asset "PLTR" "Palantir Technologies")
            (asset "SNOW" "Snowflake Inc")])]
         ;; Moderate: Stick to mega-caps
         [(weight-equal
           [(asset "META" "Meta Platforms Inc")
            (asset "GOOGL" "Alphabet Inc")
            (asset "MSFT" "Microsoft Corp")
            (asset "AMZN" "Amazon.com Inc")])])]

       ;; Mega-cap tech weak: Use diversified tech ETF
       [(asset "QQQ" "Invesco QQQ Trust")])]
     0.30

     ;; 15% DATA CENTERS & INFRASTRUCTURE (Essential AI backbone)
     ;; These benefit from AI compute demand regardless of who wins
     [(weight-equal
       [(asset "EQIX" "Equinix Inc")
        (asset "DLR" "Digital Realty Trust")
        (asset "AMT" "American Tower Corp")])]
     0.15

     ;; 10% ENERGY (Nuclear/Power for AI)
     ;; Secular theme: AI needs massive power
     [(if
       (> (current-price "CEG") (moving-average-price "CEG" {:window 200}))
       [(weight-equal
         [(asset "CEG" "Constellation Energy Corp")
          (asset "VST" "Vistra Corp")
          (asset "SMR" "NuScale Power Corp")])]
       ;; If nuclear weak, use utility ETF
       [(asset "XLU" "Utilities Select Sector SPDR Fund")])]
     0.10

     ;; 10% RAW MATERIALS (Commodities for AI infrastructure)
     ;; Copper, rare earths, uranium needed for buildout
     [(if
       ;; Check if commodities are in uptrend
       (> (rsi "COPX" {:window 14}) 40)
       [(weight-equal
         [(asset "COPX" "Global X Copper Miners ETF")
          (asset "REMX" "VanEck Rare Earth/Strategic Metals ETF")
          (asset "URNM" "Sprott Uranium Miners ETF")])]
       ;; Commodities weak: Use broad materials
       [(asset "XLB" "Materials Select Sector SPDR Fund")])]
     0.10])]

  ;; === BEAR MARKET: SPY below 200-day MA ===
  ;; Layer 3: Defensive positioning - but don't abandon AI thesis entirely
  [(if
    ;; Check if it's a shallow bear or deep bear
    (> (current-price "QQQ") (moving-average-price "QQQ" {:window 200}))

    ;; Shallow bear: SPY weak but QQQ still strong (sector rotation)
    ;; This handles scenarios where tech outperforms in uncertain times
    [(weight
      [;; 50% in mega-cap tech (they have pricing power)
       [(weight-equal
         [(asset "MSFT" "Microsoft Corp")
          (asset "GOOGL" "Alphabet Inc")
          (asset "AAPL" "Apple Inc")
          (asset "NVDA" "NVIDIA Corp")])]
       0.50

       ;; 30% in defensive infrastructure
       [(weight-equal
         [(asset "EQIX" "Equinix Inc")
          (asset "AMT" "American Tower Corp")])]
       0.30

       ;; 20% cash
       [(asset "SHY" "iShares 1-3 Year Treasury Bond ETF")]
       0.20])]

    ;; Deep bear: Both SPY and QQQ below 200-day MA
    [(if
      ;; Check if volatility is extreme (capitulation opportunity)
      (> (rsi "UVXY" {:window 14}) 70)

      ;; Extreme fear: This might be capitulation - start buying
      ;; Use 50% cash, 50% mega-cap quality for potential recovery
      [(weight
        [(weight-equal
          [(asset "MSFT" "Microsoft Corp")
           (asset "GOOGL" "Alphabet Inc")
           (asset "NVDA" "NVIDIA Corp")])
         0.50
         (asset "SHY" "iShares 1-3 Year Treasury Bond ETF")
         0.50])]

      ;; Normal bear: Full defense
      [(weight
        [;; 70% cash preservation
         [(asset "SHY" "iShares 1-3 Year Treasury Bond ETF")]
         0.70

         ;; 30% in defensive mega-caps (they survive downturns)
         [(weight-equal
           [(asset "MSFT" "Microsoft Corp")
            (asset "GOOGL" "Alphabet Inc")])]
         0.30])])])])
)
