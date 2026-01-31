(defsymphony
 "AI Infrastructure - Relative Strength Rotation"
 {:asset-class "EQUITIES", :rebalance-threshold 0.10}

 ;; Core: ALWAYS stay invested, rotate to strongest sectors
 ;; Check if both SPY and QQQ above 200-day (bull market)
 (if
  (> (current-price "SPY") (moving-average-price "SPY" {:window 200}))

  [(if
    (> (current-price "QQQ") (moving-average-price "QQQ" {:window 200}))

    ;; BULL MARKET: Both above 200-day - full sector rotation
    [(weight-equal
      [;; PROCESSORS - Check momentum for leverage
       (if
        (> (rsi "NVDA" {:window 14}) 50)
        [(if
          (> (current-price "NVDA") (moving-average-price "NVDA" {:window 100}))
          ;; Strong: Add leverage
          [(weight-equal
            [(weight-equal
              [(asset "NVDA" "NVIDIA Corp")
               (asset "AMD" "Advanced Micro Devices")
               (asset "AVGO" "Broadcom Inc")])
             (weight-equal
              [(asset "NVDA" "NVIDIA Corp")
               (asset "AMD" "Advanced Micro Devices")
               (asset "AVGO" "Broadcom Inc")])
             (asset "SOXL" "Direxion Daily Semiconductor Bull 3X")])]
          ;; Above 50 but not 100: Normal
          [(weight-equal
            [(asset "NVDA" "NVIDIA Corp")
             (asset "AMD" "Advanced Micro Devices")
             (asset "AVGO" "Broadcom Inc")])])]
        ;; Weak: No leverage
        [(weight-equal
          [(asset "NVDA" "NVIDIA Corp")
           (asset "AMD" "Advanced Micro Devices")])])

       ;; MODELS & APPLICATIONS - Relative strength vs hardware
       (if
        (> (rsi "META" {:window 14}) (rsi "NVDA" {:window 14}))
        ;; Software stronger: Overweight
        [(weight-equal
          [(weight-equal
            [(asset "META" "Meta Platforms Inc")
             (asset "GOOGL" "Alphabet Inc")
             (asset "MSFT" "Microsoft Corp")
             (asset "PLTR" "Palantir Technologies")
             (asset "SNOW" "Snowflake Inc")
             (asset "CRM" "Salesforce Inc")])
           (weight-equal
            [(asset "META" "Meta Platforms Inc")
             (asset "GOOGL" "Alphabet Inc")
             (asset "MSFT" "Microsoft Corp")
             (asset "PLTR" "Palantir Technologies")])
           (asset "TECL" "Direxion Daily Technology Bull 3X")])]
        ;; Hardware stronger: Normal
        [(weight-equal
          [(asset "META" "Meta Platforms Inc")
           (asset "GOOGL" "Alphabet Inc")
           (asset "MSFT" "Microsoft Corp")])])

       ;; INFRASTRUCTURE
       (if
        (> (rsi "EQIX" {:window 14}) 45)
        [(weight-equal
          [(asset "EQIX" "Equinix Inc")
           (asset "DLR" "Digital Realty Trust")
           (asset "CEG" "Constellation Energy Corp")
           (asset "VST" "Vistra Corp")])]
        [(weight-equal
          [(asset "EQIX" "Equinix Inc")
           (asset "DLR" "Digital Realty Trust")])])

       ;; NETWORKING
       (weight-equal
        [(asset "AVGO" "Broadcom Inc")
         (asset "ANET" "Arista Networks Inc")
         (asset "CSCO" "Cisco Systems Inc")])

       ;; MATERIALS
       (if
        (> (rsi "COPX" {:window 14}) 50)
        [(weight-equal
          [(asset "COPX" "Global X Copper Miners ETF")
           (asset "REMX" "VanEck Rare Earth/Strategic Metals ETF")
           (asset "URNM" "Sprott Uranium Miners ETF")])]
        [(weight-equal
          [(asset "FCX" "Freeport-McMoRan Inc")
           (asset "MP" "MP Materials Corp")])])])]

    ;; SPY > 200 but QQQ < 200: Broader market leadership
    [(weight-equal
      [;; Mega-cap quality
       (weight-equal
        [(asset "MSFT" "Microsoft Corp")
         (asset "GOOGL" "Alphabet Inc")
         (asset "AAPL" "Apple Inc")
         (asset "NVDA" "NVIDIA Corp")])

       ;; Infrastructure
       (weight-equal
        [(asset "EQIX" "Equinix Inc")
         (asset "DLR" "Digital Realty Trust")
         (asset "AMT" "American Tower Corp")
         (asset "CEG" "Constellation Energy Corp")])

       ;; Materials
       (weight-equal
        [(asset "COPX" "Global X Copper Miners ETF")
         (asset "REMX" "VanEck Rare Earth/Strategic Metals ETF")
         (asset "FCX" "Freeport-McMoRan Inc")
         (asset "URNM" "Sprott Uranium Miners ETF")])

       ;; Defensive tech
       (asset "QQQ" "Invesco QQQ Trust")])])]

  ;; SPY < 200: Check QQQ
  [(if
    (> (current-price "QQQ") (moving-average-price "QQQ" {:window 200}))

    ;; QQQ strong, SPY weak: TECH LEADERSHIP - go all-in tech
    [(weight-equal
      [;; Mega-cap AI
       (weight-equal
        [(asset "NVDA" "NVIDIA Corp")
         (asset "MSFT" "Microsoft Corp")
         (asset "GOOGL" "Alphabet Inc")
         (asset "META" "Meta Platforms Inc")
         (asset "AMZN" "Amazon.com Inc")])

       ;; Mega-cap AI again (to double weight vs others)
       (weight-equal
        [(asset "NVDA" "NVIDIA Corp")
         (asset "MSFT" "Microsoft Corp")
         (asset "GOOGL" "Alphabet Inc")
         (asset "META" "Meta Platforms Inc")])

       ;; High-growth AI
       (weight-equal
        [(asset "AMD" "Advanced Micro Devices")
         (asset "AVGO" "Broadcom Inc")
         (asset "PLTR" "Palantir Technologies")
         (asset "ANET" "Arista Networks Inc")])

       ;; Infrastructure
       (weight-equal
        [(asset "EQIX" "Equinix Inc")
         (asset "DLR" "Digital Realty Trust")])

       ;; Leveraged tech
       (asset "TQQQ" "ProShares UltraPro QQQ")])]

    ;; Both < 200: Bear market
    [(if
      (> (current-price "MSFT") (moving-average-price "MSFT" {:window 200}))

      ;; Mega-caps resilient
      [(weight-equal
        [(weight-equal
          [(asset "MSFT" "Microsoft Corp")
           (asset "GOOGL" "Alphabet Inc")
           (asset "NVDA" "NVIDIA Corp")
           (asset "AAPL" "Apple Inc")
           (asset "AMZN" "Amazon.com Inc")])
         (weight-equal
          [(asset "MSFT" "Microsoft Corp")
           (asset "GOOGL" "Alphabet Inc")
           (asset "NVDA" "NVIDIA Corp")])
         (weight-equal
          [(asset "EQIX" "Equinix Inc")
           (asset "AMT" "American Tower Corp")])
         (asset "SHY" "iShares 1-3 Year Treasury Bond ETF")])]

      ;; Everything weak
      [(weight-equal
        [(asset "SHY" "iShares 1-3 Year Treasury Bond ETF")
         (asset "SHY" "iShares 1-3 Year Treasury Bond ETF")
         (weight-equal
          [(asset "MSFT" "Microsoft Corp")
           (asset "GOOGL" "Alphabet Inc")])
         (weight-equal
          [(asset "EQIX" "Equinix Inc")
           (asset "SQQQ" "ProShares UltraPro Short QQQ")])])])])])
)
