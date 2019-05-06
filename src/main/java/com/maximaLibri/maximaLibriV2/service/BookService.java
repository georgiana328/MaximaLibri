package com.maximaLibri.maximaLibriV2.service;

import com.maximaLibri.maximaLibriV2.dto.IBookAndRating;
import com.maximaLibri.maximaLibriV2.model.Book;
import com.maximaLibri.maximaLibriV2.model.BookRating;
import com.maximaLibri.maximaLibriV2.model.BookRatingId;
import com.maximaLibri.maximaLibriV2.repository.BookRatingRepository;
import com.maximaLibri.maximaLibriV2.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.jayway.jsonpath.JsonPath;
import com.paralleldots.paralleldots.App;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookRatingRepository bookRatingRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(String isbn) {
        return bookRepository.findById(isbn);
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    /** get the 10 best books relative to popularity and average rating */
    public List<IBookAndRating> getTop10() {
        return bookRatingRepository.getTop10();
    }

    public IBookAndRating getBookAndRatingById(String isbn) {
        return bookRatingRepository.findBookAndRatingById(isbn);
    }

    public BookRating getBookRatingByUserAndIsbn(String isbn, Long userId) {
        BookRatingId bookRatingId = new BookRatingId();
        bookRatingId.setUserId(userId);
        bookRatingId.setIsbn(isbn);
        Optional<BookRating> optionalBookRating = bookRatingRepository.findById(bookRatingId);
        return optionalBookRating.orElse(null);
    }

    public void saveBookRating(Long userId, String isbn, Integer rating) {
        BookRatingId bookRatingId = new BookRatingId();
        bookRatingId.setUserId(userId);
        bookRatingId.setIsbn(isbn);
        BookRating bookRating = new BookRating();
        bookRating.setBookRatingId(bookRatingId);
        bookRating.setBookRating(rating);
        bookRatingRepository.save(bookRating);
    }

    public void saveBookRating(BookRating bookRating) {
        bookRatingRepository.save(bookRating);
    }

    /** return list of items that contain the parameter in the isbn, title or author*/
    public List<IBookAndRating> getSearchResults(String searchParameter) {
        return bookRepository.findSearchResults(searchParameter);
    }

    public String getBookDescription(String isbn) {
        StringBuffer description = null;
        try {
            URL oracle = new URL("https://www.goodreads.com/search?q="+isbn);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(oracle.openStream()));

            String inputLine;
            Boolean intoDescriptionDiv = false;
            Boolean passedFirstSpan = false;
            while ((inputLine = in.readLine()) != null){
                if(inputLine.contains("id=\"description\"")) intoDescriptionDiv=true;
                if(inputLine.contains("<span") && intoDescriptionDiv && passedFirstSpan) description = new StringBuffer(inputLine);
                if(inputLine.contains("<span") && intoDescriptionDiv) passedFirstSpan=true;
                if(intoDescriptionDiv && inputLine.contains("</div>")) intoDescriptionDiv=false;
            }
            in.close();
            if(description!=null) {
                Integer first = description.indexOf(">");
                description.delete(0, first + 1);
                first = description.indexOf("<br />");
                while (first != -1) {
                    description.delete(first, first + 6);
                    description.insert(first, "\n\n");
                    first = description.indexOf("<br />");
                }
                first = description.indexOf("<i>");
                while(first!=-1) {
                Integer second = description.indexOf("</i>");
                description.delete(first,second+4);
                first = description.indexOf("<i>");
                }

                first = description.indexOf("</span>");
                description.delete(first, first + 7);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if(description!=null) return description.toString();
        else return "";
    }

    public List<String> getKeywordsForBook(String description) {
        App pd = new App("UlQbgmtuLUkMygYSueumxD40Ve4znCI0DedqdJxD1uE");
        List<String> keywordList = null;
        try {
            String keywords = pd.keywords(description);
            keywordList = JsonPath.read(keywords,"$.keywords[*].keyword");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(keywordList==null) keywordList = new ArrayList<>();
        return keywordList;
    }
}
