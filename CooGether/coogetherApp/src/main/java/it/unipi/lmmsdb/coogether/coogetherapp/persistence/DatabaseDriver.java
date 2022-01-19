package it.unipi.lmmsdb.coogether.coogetherapp.persistence;

public interface DatabaseDriver {
    public boolean openConnection();
    public  void closeConnection();
}
