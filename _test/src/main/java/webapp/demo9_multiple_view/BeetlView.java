package webapp.demo9_multiple_view;

import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.annotation.Singleton;
import org.noear.solon.core.handle.ModelAndView;

@Singleton(false)
@Controller
public class BeetlView {
    @Mapping("/demo9/view/beetl")
    public ModelAndView dock(){
        ModelAndView model = new ModelAndView("beetl.htm");
        model.put("title","dock");
        model.put("msg","你好 world! in XController");

        return model;
    }
}
