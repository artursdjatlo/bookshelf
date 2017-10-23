package lv.tele2ssc.bookshelf;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller class. Controller class is processing requests from the user.
 */
@Controller
public class SearchBookController {
    private static final Logger logger 
            = LoggerFactory.getLogger(SearchBookController.class);
    
    @Autowired
    private BookService bookService;
    
    @RequestMapping(method = RequestMethod.GET, path = "/")
    public String page(Model model) {
        List<Book> list = bookService.findAllBooks();
        model.addAttribute("books", list);
        return "index";
    }
    
    @RequestMapping(method = RequestMethod.POST, path = "/")
    public String search(@RequestParam("term") String term, Model model) {
        logger.debug("User serches for {}", term);

        List<Book> list = bookService.findByTerm(term);
        
        model.addAttribute("books", list);
        model.addAttribute("term", term);

        return "index";
    }
    
}
