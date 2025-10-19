package Data;

import HandleStoreFiles.IForSaving;

import java.util.List;

public class Representative implements IForSaving {
    private int ID;
    private String name;
    private  String className;
    public Representative(int ID, String name){
        setID(ID);
        setName(name);
        className=this.getClass().getName();
    }
    public Representative(){}
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getFolderName() {
        return "Representative";
    }

    @Override
    public String getFileName() {
        return ""+ID;
    }

    @Override
    public String getData() {
        return "Representative"+","+ID+","+name+",";
    }

    @Override
    public void parseFromFile(List<String> values) {
        setID(Integer.parseInt(values.get(1)));
        setName(values.get(0));
    }
}
