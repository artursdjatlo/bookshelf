package lv.tele2ssc.bookshelf;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Controller class. Controller class is processing requests from the user.
 */
@Controller
public class BookController {

    private static final Logger logger
            = LoggerFactory.getLogger(BookController.class);

    // We will user book service 
    @Autowired
    private BookService bookService;
    
    @RequestMapping(path = "/book", method = RequestMethod.GET)
    public String page(@RequestParam long bookId, Model model) {
        logger.debug("Book id {} is requested", bookId);

        Book book = bookService.findById(bookId);

        // put book to the model to use from the template
        model.addAttribute("book", book);
        return "book";
    }

    @RequestMapping(path = "/edit-book", method = RequestMethod.GET)
    public String edit(@RequestParam long bookId, Model model) {
        logger.debug("Edit Book id {} is requested", bookId);
        Book book = bookService.findById(bookId);

        model.addAttribute("book", book);
        return "edit-book";
    }

    @RequestMapping(path = "/edit-book", method = RequestMethod.POST)
    public String edit(@Valid Book book, BindingResult bindingResult, Model model) {
        // checks whether edited book has validation errors
        if (bindingResult.hasErrors()) {
            return "edit-book";
        }
        logger.debug("Saving book {}", book);
        
        bookService.save(book);

        model.addAttribute("book", book);
        return "redirect:/book?bookId="+book.getId();
    }

}
