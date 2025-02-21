package econo.app.sleeper.repository;

import econo.app.sleeper.domain.diary.Content;
import econo.app.sleeper.domain.diary.Diary;
import econo.app.sleeper.domain.user.RoleType;
import econo.app.sleeper.domain.user.User;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(value = false)
public class DiaryRepositoryTest {

    @Autowired
    DiaryRepository diaryRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void save() {
        User user = User.builder()
                .userId("sleeper")
                .userPassword("sleeper1234@@")
                .userAge(24L)
                .userNickName("관리자")
                .build();

        userRepository.save(user);

        LocalDate localDate = LocalDate.now();
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

        Diary diary = new Diary(new Content("행복한 하루 되세요"),localDate,localDateTime,user);

        diaryRepository.save(diary);
    }

    @Test
    public void delete() {

        User user = User.builder()
                .userId("sleeper")
                .userPassword("sleeper1234@@")
                .userAge(24L)
                .userNickName("관리자")
                .build();

        userRepository.save(user);

        LocalDate localDate = LocalDate.now();
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

        Diary diary = new Diary(new Content("행복한 하루 되세요"),localDate,localDateTime,user);

        diaryRepository.save(diary);

        diaryRepository.delete(diary);
    }

    @Test
    // 회원의 감사일기들만 찾기
    public void findAllByPk() {
        User user = User.builder()
                .userId("sleeper")
                .userPassword("sleeper1234@@")
                .userAge(24L)
                .userNickName("관리자")
                .build();

        User user1 = User.builder()
                .userId("sleeper2")
                .userPassword("sleeper1234@@2")
                .userAge(25L)
                .userNickName("관리자2")
                .build();

        userRepository.save(user);
        userRepository.save(user1);

        LocalDate localDate = LocalDate.now();
        LocalDateTime localDateTime1 = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

        Diary diary = new Diary(new Content("행복한 하루 되세요"),localDate,localDateTime1,user);
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

        Diary diary1 = new Diary(new Content("행복한 하루 되세요"),localDate,localDateTime,user);

        diaryRepository.save(diary);
        diaryRepository.save(diary1);

        Long userPk = user.getUserPk();

        List<Diary> diaries = diaryRepository.findAllByPk(userPk);

        for(Diary diaryy : diaries){
            System.out.println(diaryy.getContent());
        }

    }

    @Test
    public void findByDate() {
        User user = User.builder()
                .userId("sleeper")
                .userPassword("sleeper1234@@")
                .userAge(24L)
                .userNickName("관리자")
                .roleType(RoleType.ADMIN)
                .build();

        userRepository.save(user);

        System.out.println(" ======================================================= ");
//        LocalDateTime localDateTime1 = LocalDateTime.now();
        LocalDate localDate = LocalDate.of(2022,12,22);
        LocalDateTime localDateTime = LocalDateTime.of(2022,12,23,01,33);
//        System.out.println("localDateTime = " + localDateTime1);
        System.out.println("localDateTime2 = " + localDate);
        System.out.println(" ======================================================= ");

  //      Diary diary1 = new Diary("행복한 하루 되세요",localDateTime1,user);
        Diary diary2 = new Diary(new Content("행복한 하루 되세요"),localDate,localDateTime,user);

//        diaryRepository.save(diary1);
        diaryRepository.save(diary2);

        Long userPk = user.getUserPk();

    //    List<Diary> diaries1 = diaryRepository.findByDate(userPk,localDateTime1);
        List<Diary> diaries2 = diaryRepository.findByDate(userPk,localDate);

        Assertions.assertThat(diaries2.size()).isEqualTo(1);
    }

    @Test
    public void findRecentDiaryByUser() {
        User user = userRepository.findById("sleeper").get();
        LocalDateTime localDateTime = LocalDateTime.of(2023,01,8,1,38);
        LocalDateTime localDateTime1 = LocalDateTime.of(2022,12,23,01,33);
        LocalDateTime localDateTime2 = LocalDateTime.of(2022,12,30,01,01);
        Diary diary = new Diary(new Content("오늘도 파이팅"),LocalDate.of(2023,01,8),localDateTime,user);
        Diary diary1 = new Diary(new Content("내일도 파이팅"),LocalDate.of(2022,12,30),localDateTime2,user);
        Diary diary2 = new Diary(new Content("이번주도 파이팅"),LocalDate.of(2022,12,22),localDateTime1,user);
        diaryRepository.save(diary);
        diaryRepository.save(diary1);
        diaryRepository.save(diary2);
        Diary recentDiaryByUser = diaryRepository.findRecentDiaryByUser(1L);
        Assertions.assertThat(recentDiaryByUser.getDiaryPk()).isEqualTo(diary.getDiaryPk());
    }


    @Test
    public void findBetweenDate(){
        User user = userRepository.findById("sleeper").get();
        LocalDateTime localDateTime = LocalDateTime.of(2022,11,20,10,38);
        LocalDateTime localDateTime1 = LocalDateTime.of(2022,12,23,01,33);
        LocalDateTime localDateTime2 = LocalDateTime.of(2022,12,30,01,01);
        LocalDateTime localDateTime3 = LocalDateTime.of(2023,01,22,01,01);
        LocalDateTime localDateTime4 = LocalDateTime.of(2022,12,10,03,01);
        Diary diary = new Diary(new Content("오늘도 파이팅"),LocalDate.of(2022,11,20),localDateTime,user);
        Diary diary1 = new Diary(new Content("내일도 파이팅"),LocalDate.of(2022,12,23),localDateTime2,user);
        Diary diary2 = new Diary(new Content("이번주도 파이팅"),LocalDate.of(2022,12,30),localDateTime1,user);
        Diary diary3 = new Diary(new Content("파이팅"),LocalDate.of(2023,01,22),localDateTime3,user);
        Diary diary4 = new Diary(new Content("오늘 하루도 파이팅"),LocalDate.of(2022,12,10),localDateTime4,user);
        diaryRepository.save(diary);
        diaryRepository.save(diary1);
        diaryRepository.save(diary2);
        diaryRepository.save(diary3);
        diaryRepository.save(diary4);

        LocalDate startDate = LocalDate.of(2022, 12, 1);
        LocalDate endDate = LocalDate.of(2022, 12, 31);
        List<Diary> diaries = diaryRepository.findBetweenDate(user.getUserPk(), startDate, endDate);
        Assertions.assertThat(diaries.size()).isEqualTo(3);
    }



}

