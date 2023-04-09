package jee.annuaire;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import jee.annuaire.dao.DirectoryDao;
import jee.annuaire.dao.IDirectoryDao;


    @Profile("DirectoryManager-test")
    @Configuration
    public class DaoTestConfiguration {

        @Bean
        @Primary
        public IDirectoryDao dao() {
            return Mockito.mock(IDirectoryDao.class);
        }
    }
