package com.maximaLibri.maximaLibriV2.recommender;

import com.maximaLibri.maximaLibriV2.model.Book;
import com.maximaLibri.maximaLibriV2.model.BookRating;
import com.maximaLibri.maximaLibriV2.repository.BookRatingRepository;
import com.maximaLibri.maximaLibriV2.repository.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan(value = "com.maximaLibri.maximaLibriV2")
public class QueryTests {

    @Autowired
    private BookRatingRepository bookRatingRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    @Transactional
    public void findRatingsByUserIdTest() {
        List<BookRating> bookRatingList = bookRatingRepository.findRatingsByUserId(8L);
        assertThat(bookRatingList)
                .isNotEmpty()
                .hasSize(7);
        assertTrue(bookRatingList.get(0).getBookRatingId().getUserId()==8);
        assertTrue(bookRatingList.get(0).getBookRatingId().getIsbn().equals("0002005018"));
        assertTrue(bookRatingList.get(0).getBookRating()==5);
    }

    @Test
    @Transactional
    public void findTop10Test() {
        //List<Book> bookList =  bookRepository.findTopByTitle10OrderByTitle("Silent night");
//        assertThat(bookList)
//                .isNotEmpty()
//                .hasSize(10);
    }
}
