Feature: Nutmeg transaction holding
    You are required to write a calculator that determines what stocks and cash our customers hold on a given
    date given a list of transactions. The transactions are supplied in a CSV file in the format specified below, you may
    assume that all transactions for an account are grouped together

    Scenario Outline: Stock purchase
        Given a stock purchase transaction "<account>" "<date>" "<txnType>" "<units>" "<price>" "<asset>"
        And A hold date "<hold date>"
        And the user has these holdings
        |amount | asset|
        |100    |  CASH|
        |100    |  TEST|
        When I process the transaction
        Then I should deduct/increase the cash balance of the customer by the value of "<units>" times "<price>"

        Examples:
        |   hold date   |   account   |   date        |   txnType |   units   |   price   |   asset   |
        |   20180101    |   NEAA0000  |   20170102    |   BOT     |       20  |   2.123   |   VUKE    |
        |   20180101    |   NEAA0000  |   20170102    |   SLD     |       20  |   2.123   |   TEST    |
        |   20180101    |   NEAA0000  |   20170102    |   DIV     |   0.2024  |       1   |   TEST    |
        |   20180101    |   NEAA0000  |   20170102    |   DEP     |      200  |       1   |   CASH    |