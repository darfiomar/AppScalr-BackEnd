package io.creator.appscalr.service.serviceimpl;

import io.creator.appscalr.dao.ChangelogRepository;
import io.creator.appscalr.entities.App;
import io.creator.appscalr.entities.Changelog;
import io.creator.appscalr.entities.User;
import io.creator.appscalr.service.Changelogservice;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.Set;

@AllArgsConstructor
@Service
@Transactional
public class Changelogserviceimpl implements Changelogservice {

    private final ChangelogRepository clogdao;

    @Override
    public Set<Changelog> getUserlogs(User user) {
        Set<Changelog> userlogs = clogdao.findByUserOrderByIdDesc(user);
        DateTimeFormatter formatter =
                DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
                        .withLocale( Locale.UK )
                        .withZone( ZoneId.systemDefault() );
        for(Changelog userlog : userlogs) {
            userlog.setCreatedat(formatter.format(userlog.getCreated_at()));
        }
        return userlogs;
    }

    @Override
    public void newUserLog(User user, String log) {
        Changelog userlog = new Changelog();
        userlog.setUser(user);
        userlog.setCreated_at(Instant.now());
        userlog.setLog("[APPSCALR]: "+log);
        clogdao.save(userlog);
    }

}
