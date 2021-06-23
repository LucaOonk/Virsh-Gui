package com.lucaoonk.Virt_Commander.Backend.Objects;

public class RemoteConnectionComboItem {

    private String key;
    private RemoteConnection value;

    public RemoteConnectionComboItem(){

    }
    
    public RemoteConnectionComboItem(RemoteConnection connection)
    {
        this.key = connection.name + " | "+connection.address;
        this.value = connection;
    }

    @Override
    public String toString()
    {
        return key;
    }

    public String getKey()
    {
        return key;
    }

    public RemoteConnection getValue()
    {
        return value;
    }
}
