package com.stackroute.muzixservice.repository;

import com.stackroute.muzixservice.domain.Music;
import com.stackroute.muzixservice.service.MusicService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@DataJpaTest
public class MusicRepositoryTest {

    @Autowired
    private MusicRepository musicRepository;
    private Music music;

    @Before
    public void setUp()
    {
        music = new Music();
        music.setTid(1);
        music.setTname("dilbar");
        music.setComments("hit song");

    }
//
//    @After
//    public void tearDown(){
//
//        MusicRepository.deleteTrack();
//    }


    @Test
    public void testSaveTrack(){
        musicRepository.save(music);
        Music fetchTrack = musicRepository.findById(music.getTid()).get();
        Assert.assertEquals(1,fetchTrack.getTid());

    }

    @Test
    public void testSaveTrackFailure(){
        Music testTrack = new Music(1,"dilbar","hit song");
        musicRepository.save(music);
        Music fetchTrack = musicRepository.findById(music.getTid()).get();
        Assert.assertNotSame(testTrack,music);
    }

    @Test
    public void testGetAllUser(){
        Music m = new Music(1,"dilbar","hit song");
        Music m1 = new Music(2,"blue eyes","hit song");
        musicRepository.save(m);
        musicRepository.save(m1);

        List<Music> musiclist = (List<Music>) musicRepository.findAll();
        Assert.assertEquals("dilbar",musiclist.get(0).getTname());




    }


}
