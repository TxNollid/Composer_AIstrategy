(defsymphony
 "AI Infrastructure - Momentum-Driven Secular Bull"
 {:asset-class "EQUITIES", :rebalance-threshold 0.08}

 ;; Layer 1: Only exit AI in severe bear (SPY below 200-day MA)
 (if
  (> (current-price "SPY") (moving-average-price "SPY" {:window 200}))

  ;; BULL MARKET: Stay invested with momentum rotation
  [(weight-equal
    [;; PROCESSORS & NETWORKING (Core AI)
     (if
      (> (current-price "NVDA") (moving-average-price "NVDA" {:window 200}))

      ;; NVDA secular trend intact: Check momentum
      [(if
        (> (rsi "NVDA" {:window 14}) 55)
        ;; Strong momentum: Include leverage
        [(weight-equal
          [(asset "NVDA" "NVIDIA Corp")
           (asset "AMD" "Advanced Micro Devices")
           (asset "AVGO" "Broadcom Inc")
           (asset "SOXL" "Direxion Daily Semiconductor Bull 3X")])]
        ;; Moderate: Quality only
        [(weight-equal
          [(asset "NVDA" "NVIDIA Corp")
           (asset "AMD" "Advanced Micro Devices")
           (asset "AVGO" "Broadcom Inc")
           (asset "ANET" "Arista Networks Inc")])])]

      ;; NVDA weak: Rotate to mega-caps
      [(weight-equal
        [(asset "MSFT" "Microsoft Corp")
         (asset "GOOGL" "Alphabet Inc")
         (asset "AAPL" "Apple Inc")])])

     ;; MODELS & APPLICATIONS
     (if
      (> (current-price "MSFT") (moving-average-price "MSFT" {:window 200}))

      [(if
        (> (rsi "META" {:window 14}) 50)
        ;; Strong: Include high-beta
        [(weight-equal
          [(asset "META" "Meta Platforms Inc")
           (asset "GOOGL" "Alphabet Inc")
           (asset "MSFT" "Microsoft Corp")
           (asset "PLTR" "Palantir Technologies")
           (asset "SNOW" "Snowflake Inc")])]
        ;; Moderate: Mega-caps
        [(weight-equal
          [(asset "META" "Meta Platforms Inc")
           (asset "GOOGL" "Alphabet Inc")
           (asset "MSFT" "Microsoft Corp")
           (asset "AMZN" "Amazon.com Inc")])])]

      ;; Weak: QQQ
      [(asset "QQQ" "Invesco QQQ Trust")])

     ;; DATA CENTERS (Always allocated)
     (weight-equal
      [(asset "EQIX" "Equinix Inc")
       (asset "DLR" "Digital Realty Trust")
       (asset "AMT" "American Tower Corp")])

     ;; ENERGY
     (if
      (> (current-price "CEG") (moving-average-price "CEG" {:window 200}))
      [(weight-equal
        [(asset "CEG" "Constellation Energy Corp")
         (asset "VST" "Vistra Corp")
         (asset "SMR" "NuScale Power Corp")])]
      [(asset "XLU" "Utilities Select Sector SPDR Fund")])

     ;; RAW MATERIALS
     (if
      (> (rsi "COPX" {:window 14}) 40)
      [(weight-equal
        [(asset "COPX" "Global X Copper Miners ETF")
         (asset "REMX" "VanEck Rare Earth/Strategic Metals ETF")
         (asset "URNM" "Sprott Uranium Miners ETF")])]
      [(asset "XLB" "Materials Select Sector SPDR Fund")])])]

  ;; BEAR MARKET: SPY below 200-day
  [(if
    (> (current-price "QQQ") (moving-average-price "QQQ" {:window 200}))

    ;; QQQ still strong: Tech leadership
    [(weight-equal
      [(weight-equal
        [(asset "MSFT" "Microsoft Corp")
         (asset "GOOGL" "Alphabet Inc")
         (asset "AAPL" "Apple Inc")
         (asset "NVDA" "NVIDIA Corp")])
       (weight-equal
        [(asset "EQIX" "Equinix Inc")
         (asset "AMT" "American Tower Corp")])
       (asset "SHY" "iShares 1-3 Year Treasury Bond ETF")])]

    ;; Both weak: Deep bear
    [(if
      (> (rsi "UVXY" {:window 14}) 70)

      ;; Extreme fear: Buy opportunity
      [(weight-equal
        [(weight-equal
          [(asset "MSFT" "Microsoft Corp")
           (asset "GOOGL" "Alphabet Inc")
           (asset "NVDA" "NVIDIA Corp")])
         (asset "SHY" "iShares 1-3 Year Treasury Bond ETF")])]

      ;; Normal bear: Mostly cash
      [(weight-equal
        [(asset "SHY" "iShares 1-3 Year Treasury Bond ETF")
         (asset "SHY" "iShares 1-3 Year Treasury Bond ETF")
         (weight-equal
          [(asset "MSFT" "Microsoft Corp")
           (asset "GOOGL" "Alphabet Inc")])])])])])
)
