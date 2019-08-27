import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/*
 * Create TransactionException, DigitalWallet, and DigitalWalletTransaction classes here.
 */

class TransactionException extends Exception {
    private String errorMessage;
    private String code;
    
    TransactionException(String errorMessage,String errorCode){
        this.errorMessage = errorMessage;
        this.code =  errorCode;
    }
    String getErrorCode(){
        return code;
    }

    public String getMessage(){
        return errorMessage;
    }

}

class DigitalWallet {

    private String id;
    private String user;
    private String code;
    private int balance;
    
    DigitalWallet(String id, String user){
        this.id = id;
        this.user = user;
    }

    DigitalWallet(String id, String user, String code){
        this.id = id;
        this.user = user;
        this.code = code;
    }

    String getWalletId(){
        return id;
    }

    String getUsername(){
        return user;
    }

    String getUserAccessToken(){
        return code;
    }

    void setWalletBalance(int balance){
        this.balance = balance;
    }
    int getWalletBalance(){
        return balance;
    }

}

class DigitalWalletTransaction {

    void addMoney(DigitalWallet wallet,int amount) throws TransactionException {
        if(wallet.getUserAccessToken() == null)
            throw new TransactionException("User not authorized", "USER_NOT_AUTHORIZED");
        int bal = wallet.getWalletBalance();
        if(amount < 1)
            throw new TransactionException("Amount should be greater than zero", "INVALID_AMOUNT");
        else
            wallet.setWalletBalance( bal + amount);

    }
    void payMoney(DigitalWallet wallet,int amount) throws TransactionException {
        if(wallet.getUserAccessToken() == null)
            throw new TransactionException("User not authorized", "USER_NOT_AUTHORIZED");
        int bal = wallet.getWalletBalance();
        if(amount < 1)
            throw new TransactionException("Amount should be greater than zero", "INVALID_AMOUNT");
        else if(bal < amount)
            throw new TransactionException("Insufficient balance", "INSUFFICIENT_BALANCE");
        else
            wallet.setWalletBalance(bal - amount);

    }

}
public class Solution {
    private static final Scanner INPUT_READER = new Scanner(System.in);
    private static final DigitalWalletTransaction DIGITAL_WALLET_TRANSACTION = new DigitalWalletTransaction();
    
    private static final Map<String, DigitalWallet> DIGITAL_WALLETS = new HashMap<>();
    
    public static void main(String[] args) {
        int numberOfWallets = Integer.parseInt(INPUT_READER.nextLine());
        while (numberOfWallets-- > 0) {
            String[] wallet = INPUT_READER.nextLine().split(" ");
            DigitalWallet digitalWallet;
            
            if (wallet.length == 2) {
                digitalWallet = new DigitalWallet(wallet[0], wallet[1]);
            } else {
                digitalWallet = new DigitalWallet(wallet[0], wallet[1], wallet[2]);
            }
            
            DIGITAL_WALLETS.put(wallet[0], digitalWallet);
        }
        
        int numberOfTransactions = Integer.parseInt(INPUT_READER.nextLine());
        while (numberOfTransactions-- > 0) {
            String[] transaction = INPUT_READER.nextLine().split(" ");
            DigitalWallet digitalWallet = DIGITAL_WALLETS.get(transaction[0]);
            
            if (transaction[1].equals("add")) {
                try {
                    DIGITAL_WALLET_TRANSACTION.addMoney(digitalWallet, Integer.parseInt(transaction[2]));
                    System.out.println("Wallet successfully credited.");
                } catch (TransactionException ex) {
                    System.out.println(ex.getErrorCode() + ": " + ex.getMessage() + ".");
                }
            } else {
                try {
                    DIGITAL_WALLET_TRANSACTION.payMoney(digitalWallet, Integer.parseInt(transaction[2]));
                    System.out.println("Wallet successfully debited.");
                } catch (TransactionException ex) {
                    System.out.println(ex.getErrorCode() + ": " + ex.getMessage() + ".");
                }
            }
        }
        
        System.out.println();
        
        DIGITAL_WALLETS.keySet()
                .stream()
                .sorted()
                .map((digitalWalletId) -> DIGITAL_WALLETS.get(digitalWalletId))
                .forEachOrdered((digitalWallet) -> {
                    System.out.println(digitalWallet.getWalletId()
                            + " " + digitalWallet.getUsername()
                            + " " + digitalWallet.getWalletBalance());
                });
    }
}