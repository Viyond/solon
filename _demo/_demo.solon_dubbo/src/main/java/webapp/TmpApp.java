package webapp;

import org.noear.solon.Solon;
import org.noear.solon.SolonApp;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;

@Controller
public class TmpApp {
    public static void main(String[] args) {
        Solon.start(TmpApp.class, args);
    }

    @Mapping("/")
    public Object helloworld(){
        return "Hello world";
    }
}
