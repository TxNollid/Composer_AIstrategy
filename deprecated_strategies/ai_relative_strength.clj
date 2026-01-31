(defsymphony
 "AI Infrastructure - Relative Strength Rotation"
 {:asset-class "EQUITIES", :rebalance-threshold 0.10}

 ;; Core Philosophy: ALWAYS stay invested in AI, but rotate to strongest sectors
 ;; Never go to cash in bull markets - the secular AI trend is too powerful

 ;; Layer 1: Market regime determines risk appetite (leverage vs normal)
 (if
  (and
   (> (current-price "SPY") (moving-average-price "SPY" {:window 200}))
   (> (current-price "QQQ") (moving-average-price "QQQ" {:window 200})))

  ;; === BULL MARKET: Both SPY and QQQ above 200-day ===
  ;; Rotate based on relative strength, use leverage on strongest
  [(weight
    [;; Sector 1: PROCESSORS - Check momentum
     [(if
       (and
        (> (rsi "NVDA" {:window 14}) 50)
        (> (current-price "NVDA") (moving-average-price "NVDA" {:window 100})))
       ;; Strong: Overweight with leverage
       [(weight
         [(weight-equal
           [(asset "NVDA" "NVIDIA Corp")
            (asset "AMD" "Advanced Micro Devices")
            (asset "AVGO" "Broadcom Inc")])
          0.70
          (asset "SOXL" "Direxion Daily Semiconductor Bull 3X")
          0.30])]
       ;; Weak: Normal weight, no leverage
       [(weight-equal
         [(asset "NVDA" "NVIDIA Corp")
          (asset "AMD" "Advanced Micro Devices")])])]
     0.30

     ;; Sector 2: MODELS & APPLICATIONS - Relative strength check
     [(if
       (> (rsi "META" {:window 14}) (rsi "NVDA" {:window 14}))
       ;; Software stronger than hardware: Overweight models
       [(weight
         [(weight-equal
           [(asset "META" "Meta Platforms Inc")
            (asset "GOOGL" "Alphabet Inc")
            (asset "MSFT" "Microsoft Corp")
            (asset "PLTR" "Palantir Technologies")
            (asset "SNOW" "Snowflake Inc")
            (asset "CRM" "Salesforce Inc")])
          0.80
          (asset "TECL" "Direxion Daily Technology Bull 3X")
          0.20])]
       ;; Hardware stronger: Normal weight
       [(weight-equal
         [(asset "META" "Meta Platforms Inc")
          (asset "GOOGL" "Alphabet Inc")
          (asset "MSFT" "Microsoft Corp")])])]
     0.30

     ;; Sector 3: INFRASTRUCTURE (Data Centers + Energy)
     ;; These are steady growers - always allocate
     [(if
       (> (rsi "EQIX" {:window 14}) 45)
       ;; Strong infrastructure: Include growth names
       [(weight-equal
         [(asset "EQIX" "Equinix Inc")
          (asset "DLR" "Digital Realty Trust")
          (asset "CEG" "Constellation Energy Corp")
          (asset "VST" "Vistra Corp")])]
       ;; Weak: Stick to quality REITs
       [(weight-equal
         [(asset "EQIX" "Equinix Inc")
          (asset "DLR" "Digital Realty Trust")])])]
     0.20

     ;; Sector 4: NETWORKING - Critical AI infrastructure
     [(weight-equal
       [(asset "AVGO" "Broadcom Inc")
        (asset "ANET" "Arista Networks Inc")
        (asset "CSCO" "Cisco Systems Inc")])]
     0.10

     ;; Sector 5: MATERIALS - Tactical allocation based on momentum
     [(if
       (> (rsi "COPX" {:window 14}) 50)
       ;; Strong commodities: Full exposure
       [(weight-equal
         [(asset "COPX" "Global X Copper Miners ETF")
          (asset "REMX" "VanEck Rare Earth/Strategic Metals ETF")
          (asset "URNM" "Sprott Uranium Miners ETF")])]
       ;; Weak commodities: Rotate to large-cap miners
       [(weight-equal
         [(asset "FCX" "Freeport-McMoRan Inc")
          (asset "MP" "MP Materials Corp")])])]
     0.10])]

  ;; === MIXED MARKET: SPY or QQQ below 200-day (but not both) ===
  [(if
   (> (current-price "QQQ") (moving-average-price "QQQ" {:window 200}))

   ;; QQQ strong, SPY weak: Tech leadership - go all-in on tech
   ;; This is actually BULLISH for AI thesis
   [(weight
     [;; 40% Mega-cap AI leaders
      [(weight-equal
        [(asset "NVDA" "NVIDIA Corp")
         (asset "MSFT" "Microsoft Corp")
         (asset "GOOGL" "Alphabet Inc")
         (asset "META" "Meta Platforms Inc")
         (asset "AMZN" "Amazon.com Inc")])]
      0.40

      ;; 30% High-growth AI plays
      [(weight-equal
        [(asset "AMD" "Advanced Micro Devices")
         (asset "AVGO" "Broadcom Inc")
         (asset "PLTR" "Palantir Technologies")
         (asset "ANET" "Arista Networks Inc")])]
      0.30

      ;; 20% Infrastructure (defensive within tech)
      [(weight-equal
        [(asset "EQIX" "Equinix Inc")
         (asset "DLR" "Digital Realty Trust")])]
      0.20

      ;; 10% Leveraged tech (taking advantage of leadership)
      [(asset "TQQQ" "ProShares UltraPro QQQ")]
      0.10])]

   ;; SPY strong, QQQ weak: Broader market leadership
   ;; Reduce tech exposure slightly, add materials/energy
   [(weight
     [;; 35% Mega-cap quality tech
      [(weight-equal
        [(asset "MSFT" "Microsoft Corp")
         (asset "GOOGL" "Alphabet Inc")
         (asset "AAPL" "Apple Inc")
         (asset "NVDA" "NVIDIA Corp")])]
      0.35

      ;; 25% Infrastructure (benefits from broader economy)
      [(weight-equal
        [(asset "EQIX" "Equinix Inc")
         (asset "DLR" "Digital Realty Trust")
         (asset "AMT" "American Tower Corp")
         (asset "CEG" "Constellation Energy Corp")])]
      0.25

      ;; 25% Materials (reflation/growth theme)
      [(weight-equal
        [(asset "COPX" "Global X Copper Miners ETF")
         (asset "REMX" "VanEck Rare Earth/Strategic Metals ETF")
         (asset "FCX" "Freeport-McMoRan Inc")
         (asset "URNM" "Sprott Uranium Miners ETF")])]
      0.25

      ;; 15% Defensive tech
      [(asset "QQQ" "Invesco QQQ Trust")]
      0.15])])]

  ;; === BEAR MARKET: Both SPY and QQQ below 200-day ===
  ;; Still stay invested but rotate to quality + opportunistic inverse
  [(if
   ;; Check if mega-caps are holding up (they often do)
   (> (current-price "MSFT") (moving-average-price "MSFT" {:window 200}))

   ;; Mega-caps resilient: Focus on them
   [(weight
     [;; 60% FANG+ quality
      [(weight-equal
        [(asset "MSFT" "Microsoft Corp")
         (asset "GOOGL" "Alphabet Inc")
         (asset "NVDA" "NVIDIA Corp")
         (asset "AAPL" "Apple Inc")
         (asset "AMZN" "Amazon.com Inc")])]
      0.60

      ;; 25% Infrastructure (recession-resistant)
      [(weight-equal
        [(asset "EQIX" "Equinix Inc")
         (asset "AMT" "American Tower Corp")])]
      0.25

      ;; 15% Bonds
      [(asset "SHY" "iShares 1-3 Year Treasury Bond ETF")]
      0.15])]

   ;; Everything weak: Defensive + small inverse for downside capture
   [(weight
     [;; 40% Bonds
      [(asset "SHY" "iShares 1-3 Year Treasury Bond ETF")]
      0.40

      ;; 30% Mega-cap quality (long-term holds)
      [(weight-equal
        [(asset "MSFT" "Microsoft Corp")
         (asset "GOOGL" "Alphabet Inc")])]
      0.30

      ;; 20% Defensive infrastructure
      [(asset "EQIX" "Equinix Inc")]
      0.20

      ;; 10% Inverse for downside capture
      [(asset "SQQQ" "ProShares UltraPro Short QQQ")]
      0.10])])])
)
