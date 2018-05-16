package com.jinhui.test;




import com.jinhui.model.Fileinfos;
import com.jinhui.model.review.Attachment;
import com.jinhui.model.review.Review;
import com.jinhui.service.review.ReviewService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:test-context.xml", "classpath*:springmvc-servlet.xml" })
public class ReviewTests extends AbstractJUnit4SpringContextTests {

    @Autowired
    private ReviewService reviewService;

    private MockMvc mockMvc;

    @Test
    public void reviewHistory() throws Exception {
        List<Review> review = reviewService.reviewHistory(1);
        System.out.println(review);
    }

    @Test
    public void findLastUpdated(){
        Review review = reviewService.findLastUpdated(1);
        System.out.println(review);
    }

    @Test
    public void addAttachment(){
       /* Attachment attachment = new Attachment(1,1L,2,"");
       reviewService.addAttachments(attachment);*/
    }

    @Test
    public void review(){
        Review review = new Review(2, 1,"");
        Fileinfos fileinfos = new Fileinfos();
        fileinfos.setId(1L);
        reviewService.review(review,null);
        review = new Review(2, 1,"");
        reviewService.review(review,null);
    }

}
