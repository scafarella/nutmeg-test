Feature: Nutmeg transaction holding
    You are required to write a calculator that determines what stocks and cash our customers hold on a given
    date given a list of transactions. The transactions are supplied in a CSV file in the format specified below, you may
    assume that all transactions for an account are grouped together

    Scenario Outline: Stock purchase
        Given a stock purchase transaction "<account>" "<date>" "<txnType>" "<units>" "<price>" "<asset>"
        And A hold date "<hold date>"
        And a "<current balance amount>"
        When I process the transaction
        Then I should deduct the cash balance of the customer by the value of the transaction ("<units>" times "<price>")
    Examples:
        |   hold date   |   current balance amount  |   account   |   date        |   txnType |   units   |   price   |   asset   |
        |   20180101    |   200                     |   NEAA0000  |   20170102    |   BOT     |       20  |   2.123   |   VUKE    |