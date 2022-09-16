package io.creator.appscalr.service.serviceimpl;

import io.creator.appscalr.dao.TemplateRepository;
import io.creator.appscalr.entities.Template;
import io.creator.appscalr.service.Templateservice;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Service
@Transactional
public class Templateserviceimpl implements Templateservice {

    private final TemplateRepository templatedao;

    @Override
    public Template newtemplate(String title, String thumbnailurl, String templatedom) {
        Template template = new Template();
        template.setTitle(title);
        template.setThumbnail(thumbnailurl);
        template.setCreated_at(Instant.now());
        template.setDom(templatedom);
        templatedao.save(template);
        return template;
    }

    @Override
    public Template updatetemplate(long templateid, String title, String thumbnailurl, String templatedom) {
        Template template = templatedao.findById(templateid).get();
        template.setTitle(title);
        template.setThumbnail(thumbnailurl);
        template.setCreated_at(Instant.now());
        template.setDom(templatedom);
        templatedao.save(template);
        return template;
    }

    @Override
    public void deletetemplate(long templateid) {
        templatedao.deleteById(templateid);
    }

    @Override
    public Set<Template> getTemplates() {
        Set<Template> templates = new HashSet(templatedao.findAll());
        return templates;
    }
}
