package it.unipi.dii.inginf.lsdb.coogether.persistence;

public interface DatabaseDriver {
    public boolean openConnection();
    public  void closeConnection();
}
