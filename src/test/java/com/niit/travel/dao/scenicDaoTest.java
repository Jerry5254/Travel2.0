package com.niit.travel.dao;

import com.niit.travel.entity.scenic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class scenicDaoTest {
    @Autowired
    private scenicDao dao;

    @Test
    public void querytn() {
        List<scenic> tnList = dao.queryScenic();
        assertEquals(2, tnList.size());
    }

    @Test
    public void queryUsersById() {
        List<scenic> tnList = dao.queryScenicByCity("Guangzhou");
        assertEquals(2, tnList.size());
    }

    @Test
    public void insertUsers() {
        scenic sc = new scenic();
        sc.setSCity("Guangzhou");
        sc.setSName("Guangzhou Tower");
        sc.setSDes("Very high!Very Beautiful");
        sc.setSPic("123");
        int efftectedNum = dao.insertScenic(sc);
        assertEquals(1, efftectedNum);
    }

    @Test
    public void updateUsers() {
        scenic sc = new scenic();
        sc.setSId(1);
        sc.setSPic("123456");
        int efftectedNum = dao.updateScenic(sc);
        assertEquals(1, efftectedNum);
    }


}
