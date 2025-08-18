# fixBridge
Application that connects to broker and sends incoming json requests to that broker with Quickfix/J and FIX protocol

# Must do
Put right password in quickfix config (Didn't want to push password in github)

# Notes
In application properties write quickfix file from resources under config quickfix.config-file
Put right password in quickfix config

Make sure you have configured maven so that you can run program

"symbol": "TSLA" was not valid one in lmax broker but with other existing ones like "ETH/USD" it worked fine. 