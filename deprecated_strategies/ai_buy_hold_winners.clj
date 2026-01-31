(defsymphony
 "AI Infrastructure - Buy & Hold Winners with Tactical Overlay"
 {:asset-class "EQUITIES", :rebalance-threshold 0.12}

 ;; Philosophy: The 2020s are the AI decade. Stay invested aggressively.
 ;; Only reduce exposure in CONFIRMED bear markets (both indices down).
 ;; Concentrate in proven winners, not equal weight laggards.

 ;; Layer 1: Extreme bear check (both SPY AND QQQ below 200-day)
 (if
  (and
   (< (current-price "SPY") (moving-average-price "SPY" {:window 200}))
   (< (current-price "QQQ") (moving-average-price "QQQ" {:window 200})))

  ;; === CONFIRMED BEAR MARKET ===
  ;; Even here, maintain 50% exposure to AI leaders (they recover first)
  [(weight
    [;; 50% in AI mega-caps (these survive and thrive)
     [(weight-equal
       [(asset "NVDA" "NVIDIA Corp")
        (asset "MSFT" "Microsoft Corp")
        (asset "GOOGL" "Alphabet Inc")
        (asset "META" "Meta Platforms Inc")])]
     0.50

     ;; 30% cash
     [(asset "SHY" "iShares 1-3 Year Treasury Bond ETF")]
     0.30

     ;; 20% opportunistic inverse (only in extreme weakness)
     [(if
       (and
        (< (current-price "QQQ") (moving-average-price "QQQ" {:window 50}))
        (> (rsi "UVXY" {:window 14}) 65))
       [(asset "SQQQ" "ProShares UltraPro Short QQQ")]
       [(asset "SHY" "iShares 1-3 Year Treasury Bond ETF")])]
     0.20])]

  ;; === EVERYTHING ELSE: Stay fully invested ===
  ;; This captures ALL of: bull markets, pullbacks, sector rotations, etc.
  [(weight
    [;; 45% CORE AI COMPUTE (The foundation - picks and shovels)
     ;; Weight toward proven winners, not equal allocation
     [(if
       ;; NVDA showing extreme strength (RSI > 60)
       (> (rsi "NVDA" {:window 14}) 60)

       ;; NVDA momentum: Overweight it + add leverage
       [(weight
         [(asset "NVDA" "NVIDIA Corp")
          0.50
          (asset "AMD" "Advanced Micro Devices")
          0.20
          (asset "AVGO" "Broadcom Inc")
          0.20
          (asset "SOXL" "Direxion Daily Semiconductor Bull 3X")
          0.10])]

       ;; Normal allocation: Still heavy NVDA
       [(weight
         [(asset "NVDA" "NVIDIA Corp")
          0.40
          (asset "AMD" "Advanced Micro Devices")
          0.30
          (asset "AVGO" "Broadcom Inc")
          0.30])])]
     0.45

     ;; 30% AI SOFTWARE & PLATFORMS (Building on the infrastructure)
     [(if
       ;; Check if software is leading (META RSI > NVDA RSI)
       (> (rsi "META" {:window 14}) (rsi "NVDA" {:window 14}))

       ;; Software leadership: Overweight high-growth
       [(weight
         [(asset "META" "Meta Platforms Inc")
          0.25
          (asset "GOOGL" "Alphabet Inc")
          0.20
          (asset "MSFT" "Microsoft Corp")
          0.20
          (asset "PLTR" "Palantir Technologies")
          0.15
          (asset "SNOW" "Snowflake Inc")
          0.10
          (asset "TECL" "Direxion Daily Technology Bull 3X")
          0.10])]

       ;; Hardware leading: Mega-cap safety
       [(weight
         [(asset "MSFT" "Microsoft Corp")
          0.35
          (asset "GOOGL" "Alphabet Inc")
          0.35
          (asset "META" "Meta Platforms Inc")
          0.30])])]
     0.30

     ;; 10% NETWORKING (Critical connective tissue)
     [(weight-equal
       [(asset "AVGO" "Broadcom Inc")
        (asset "ANET" "Arista Networks Inc")])]
     0.10

     ;; 10% POWER & INFRASTRUCTURE (Secular AI demand driver)
     [(if
       (> (current-price "CEG") (moving-average-price "CEG" {:window 200}))
       ;; Nuclear theme working: Pure play
       [(weight-equal
         [(asset "CEG" "Constellation Energy Corp")
          (asset "VST" "Vistra Corp")
          (asset "SMR" "NuScale Power Corp")])]
       ;; Weak: Rotate to data center REITs (defensive)
       [(weight-equal
         [(asset "EQIX" "Equinix Inc")
          (asset "DLR" "Digital Realty Trust")])])]
     0.10

     ;; 5% TACTICAL MOMENTUM (Chase what's working)
     [(if
       ;; Check which subsector has strongest momentum
       (> (rsi "PLTR" {:window 14}) (rsi "COPX" {:window 14}))

       ;; Software momentum: Add high-beta AI
       [(weight-equal
         [(asset "PLTR" "Palantir Technologies")
          (asset "AI" "C3.ai Inc")
          (asset "PATH" "UiPath Inc")])]

       ;; Materials momentum: Add commodities
       [(weight-equal
         [(asset "COPX" "Global X Copper Miners ETF")
          (asset "URNM" "Sprott Uranium Miners ETF")
          (asset "MP" "MP Materials Corp")])])]
     0.05])])
)
