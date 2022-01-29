# Mockchain 

Submission for CIT2C22 DevOps Essentials at Temasek Polytechnic.  

Planned submission for the hackathon hacksingapore (hacksg) under the open track "The Era of Blockchain & Trust" It seeks to
be an educational tool that can simply explain blockchain in an interactive fashion, improving people's perception 
towards the trust technology.

## Requirements

Java, Tomcat, SQL Server compatible with Microsoft's JDBC Driver

Clone the project (HTTPS)

```
git clone https://github.com/Jaykingamez/mockchain.git
```

Or with Github CLI

```
gh repo clone Jaykingamez/mockchain
```

## Functions:

Register and login an account

Create a wallet, and update the amount in the wallet. Money can be transfered from one account to another, or one can increase
the funds in their wallet if the transaction is approved or rejected. This sought to simulate a 51% attack.

Create transactions, and update their approval. Transactions will not be deleted as they are stored as a ledger for everyone
to see. 

Approve or Reject transactions. A user out of all of those who approve the transactions will be randomly given a dollar 
to simulate mining on the system, encouraging users to partake in the approving and rejection of transactions as much as 
possible.

## Explanation

Just like Tor, if it was not released to the public, the technology would be effectively worthless. In a closed system,
one can print infinite money into their bank accounts, but with no one else to use it with, the money is effectively worthless. 
I believe, Mockchain can help fulfill its intended educational purpose.


