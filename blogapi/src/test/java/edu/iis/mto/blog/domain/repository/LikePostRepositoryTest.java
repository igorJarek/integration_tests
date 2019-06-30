package edu.iis.mto.blog.domain.repository;

import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.BlogPost;
import edu.iis.mto.blog.domain.model.LikePost;
import edu.iis.mto.blog.domain.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class LikePostRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LikePostRepository repository;

    private User user;
    private BlogPost blogPost;
    private LikePost likePost;

    @Before
    public void setup(){
        user = new User();
        user.setFirstName("Jan");
        user.setLastName("Kowalski");
        user.setEmail("jkowalski@gmail.com");
        user.setAccountStatus(AccountStatus.NEW);
        entityManager.persist(user);

        blogPost = new BlogPost();
        blogPost.setUser(user);
        blogPost.setEntry("Lorem ipsum dolor sit amet");

        entityManager.persist(blogPost);

        likePost = new LikePost();
        likePost.setUser(user);
        likePost.setPost(blogPost);
        repository.save(likePost);
    }


    @Test
    public void postShouldHaveCorrectUser() {
        List<LikePost> likePosts = repository.findAll();
        Assert.assertEquals(user, likePosts.get(0).getUser());
    }

    @Test
    public void postsShouldHaveOnePostInside() {
        List<LikePost> likePosts = repository.findAll();
        Assert.assertEquals(1, likePosts.size());
    }

    @Test
    public void postListShouldContainLikePost() {
        List<LikePost> likePosts = repository.findAll();
        Assert.assertTrue(likePosts.contains(likePost));
    }

    @Test
    public void shouldFindLikePostWhenProperUserAndBlogPostIsGiven() {
        Optional<LikePost> likePosts = repository.findByUserAndPost(user,blogPost);
        Assert.assertEquals(likePost, likePosts.get());
    }

    @Test
    public void postShouldHaveProperBlogPost() {
        List<LikePost> likePosts = repository.findAll();
        Assert.assertEquals(blogPost, likePosts.get(0).getPost());
    }

    @Test
    public void shouldNotFindLikePostWhenGivenBlogPostIsInvalid(){
        BlogPost fakePost = new BlogPost();
        fakePost.setEntry("fake block post");
        fakePost.setUser(user);
        entityManager.persist(fakePost);
        Optional<LikePost> likePosts = repository.findByUserAndPost(user,fakePost);
        Assert.assertEquals(Optional.empty(), likePosts);
    }

    @Test
    public void shouldNotFindPostWhenUserIsInvalid(){
        User fakeUser = new User();
        fakeUser.setFirstName("Piotr");
        fakeUser.setLastName("Tymbarkiewicz");
        fakeUser.setEmail("ptymbarkiewicz@gmail.pl");
        fakeUser.setAccountStatus(AccountStatus.NEW);
        entityManager.persist(fakeUser);
        Optional<LikePost> likePosts = repository.findByUserAndPost(fakeUser,blogPost);
        Assert.assertEquals(Optional.empty(),likePosts);
    }

    @Test
    public void shouldProperlyEditLikePost(){
        Optional<LikePost> listLikedPosts = repository.findByUserAndPost(user,blogPost);
        Assert.assertEquals(likePost,listLikedPosts.get());
        likePost.getUser().setEmail("test@gmail.com");
        repository.save(likePost);
        listLikedPosts = repository.findByUserAndPost(user,blogPost);
        Assert.assertEquals(likePost,listLikedPosts.get());
        Assert.assertEquals(1,repository.findAll().size());
    }

}
