package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

//------------------------4.@SpringBootTest-----------------------
@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    //------------------------12.@BeforeAll-----------------------
    @BeforeAll
    static void setup(){

    }
    //------------------------13.@BeforeEach-----------------------
    @BeforeEach
    void e(){

    }
    //------------------------14.@AfterEach-----------------------
    @AfterEach
    void a(){

    }
    //------------------------15.@AfterAll-----------------------
    @AfterAll
    static void all(){

    }

    //------------------------1.@Test-----------------------
    @Disabled
    @Test
    void TestFindByUserName() {
        //------------------------2.assertEquals()-----------------------
        assertEquals(3, 2 + 1);
        //------------------------3.assertNotNull()-----------------------
        assertNotNull(userRepository.findByUserName("ram"));//userRepository will be null we use 4. to load spring context
        //------------------------5.assertTrue()-----------------------
        assertTrue(5 > 3);
        User user = userRepository.findByUserName("ram");
        //------------------------6.assertFalse()-----------------------
        assertFalse(user.getJournalEntries().isEmpty());
    }

    //----------------------------7.@Disabled----------------------------
    @Disabled
    //------------------------8.@ParameterizedTest-----------------------
    @ParameterizedTest
    //----------------------------9.@CsvSource---------------------------
    @CsvSource({
            "1,1,2",
            "2,10,12",
            "3,3,9"
    })
    void test(int a, int b, int expected) {
        assertEquals(expected, a + b);
    }

    /*@ParameterizedTest
    @CsvSource({"ram", "shyam", "vipul"})
    void TestFindByUserName(String name) {
        assertNotNull(userRepository.findByUserName(name));
    }*/

    //----------------------------10.@ValueSource---------------------------
    @Disabled
    @ParameterizedTest
    @ValueSource(strings = {"ram", "shyam", "vipul"})
    void TestFindByUserName(String name) {
        assertNotNull(userRepository.findByUserName(name));
    }

    //----------------------------11.@ArgumentsSource----------------custom class UserArgumentsProvider
    @Disabled
    @ParameterizedTest
    @ArgumentsSource(UserArgumentsProvider.class)
    void testSaveNewUser(User user) {
        assertTrue(userService.saveNewUser(user));
    }


}
