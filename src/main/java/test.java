import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;

public class test {

    public static void main(String[] args) {
        YaGsonBuilder yaGsonBuilder = new YaGsonBuilder();
        YaGson yaGson = yaGsonBuilder.create();
        String input = "{\"@type\":\"test$AttackResponse\",\"@val\":{\"name\":\"hello\",\"number\":2}}";
        Response response = yaGson.fromJson(input, Response.class);
        if (response instanceof AttackResponse){
            System.out.println("polymorphism superiority");
        }else {
            System.out.println("khak to saret");
        }
    }

    static class Response{
        private int number = 2;
    }

    static class AttackResponse extends Response{
        private String name = "hello";
    }
}
