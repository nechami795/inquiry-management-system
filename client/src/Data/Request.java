package Data;

import java.util.List;

public class Request extends  Inquiry{
    private static final long serialVersionUID = 1234567L;

    public  Request(){}
    @Override
    public String handling(){
        return ("Request:"+getCode());
    }

//    @Override
//    public String getFolderName() {
//        return "Request";
//    }
//
//    @Override
//    public String getFileName() {
//        return ""+code;
//    }
//
//    @Override
//    public String getData() {
//        return className+","+code+","+description+","+documentNames.toString()+","+creationDate;
//    }
//
//    @Override
//    public void parseFromFile(List<String> values) {
//        description = values.get(2);
//        String[] value = values.get(3).split(" ");
//        for (String val : value) {
//            documentNames.add(val);
//        }
//    }
}
