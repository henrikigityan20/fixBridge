# fixBridge
Application that connects to broker and sends incoming json requests to that broker with Quickfix/J and FIX protocol

# Notes
In application properties write quickfix file from resources under config quickfix.config-file

Make sure you have configured maven so that you can run program

"symbol": "TSLA" was not valid one in lmax broker but with other existing ones like "ETH/USD" it worked fine. 